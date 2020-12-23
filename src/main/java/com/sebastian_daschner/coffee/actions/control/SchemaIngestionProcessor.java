package com.sebastian_daschner.coffee.actions.control;

import com.sebastian_daschner.coffee.actions.entity.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class SchemaIngestionProcessor {

    public List<QueryEvents> processEvents(Collection<TransactionEvent> events) {
        List<QueryEvents> queries = new ArrayList<>();
        queries.addAll(deleteRelationshipEvents(events));
        queries.addAll(deleteNodeEvents(events));
        queries.addAll(mergeNodeEvents(events));
        queries.addAll(mergeRelationshipEvents(events));

        return queries;
    }

    private List<QueryEvents> mergeNodeEvents(Collection<TransactionEvent> events) {

        BiFunction<List<String>, List<String>, List<String>> filterLabels = (labels, existing) ->
                labels.stream()
                        .filter(label -> existing.stream().noneMatch(constraint -> Objects.equals(constraint, label)))
                        .map(SchemaIngestionProcessor::quote)
                        .collect(Collectors.toList());

        return events.stream()
                .filter(event -> event.payload.type == EntityType.node && event.meta.operation != OperationType.deleted)
                .map(event -> {
                    NodeChange changeEvtAfter = (NodeChange) event.payload.after;
                    NodeChange changeEvtBefore = (NodeChange) event.payload.before;
                    List<String> labelsAfter = changeEvtAfter.labels;
                    List<String> labelsBefore = changeEvtBefore != null ? changeEvtBefore.labels : List.of();

                    List<Constraint> constraints = getNodeConstraints(event, it -> it.type == Constraint.StreamsConstraintType.UNIQUE);

                    if (constraints.isEmpty()) {
                        return null;
                    } else {
                        List<String> allLabels = new ArrayList<>(labelsAfter);
                        allLabels.addAll(labelsBefore);
                        List<String> labelsToAdd = filterLabels.apply(minus(labelsAfter, labelsBefore), allLabels);
                        List<String> labelsToDelete = filterLabels.apply(minus(labelsBefore, labelsAfter), allLabels);

                        Set<String> propertyKeys = changeEvtAfter.properties != null ? changeEvtAfter.properties.keySet() : Set.of();
                        Set<String> keys = getNodeKeys(labelsAfter, propertyKeys, constraints);

                        if (keys.isEmpty()) {
                            return null;
                        } else {
                            NodeSchemaMetadata key = new NodeSchemaMetadata(constraints, labelsAfter, labelsToAdd, labelsToDelete, keys);
                            Map<String, Map<String, Object>> value = Map.of("properties", changeEvtAfter.properties);
                            return Pair.of(key, value);
                        }
                    }
                })
                .filter(Objects::nonNull)
                .map(pair -> {
                    String nodeLabels = getLabelsAsString(pair.getKey().identifierLabels);
                    String query = "UNWIND $events as event\n" +
                            "MERGE (n" + nodeLabels + "{" + getNodeKeysAsString(pair.getKey().keys) + "})\n" +
                            "SET n = event.properties";
                    if (!pair.getKey().labelsToAdd.isEmpty()) {
                        query += "\nSET n" + getLabelsAsString(pair.getKey().labelsToAdd) + ' ';
                    }
                    if (!pair.getKey().labelsToDelete.isEmpty()) {
                        query += "\nREMOVE n" + getLabelsAsString(pair.getKey().labelsToDelete) + ' ';
                    }
                    return new QueryEvents(query, List.of(pair.getValue()));
                })
                .collect(Collectors.toList());
    }

    private List<QueryEvents> deleteNodeEvents(Collection<TransactionEvent> events) {
        return events.stream()
                .filter(event -> event.payload.type == EntityType.node && event.meta.operation == OperationType.deleted)
                .map(event -> {
                    NodeChange changeEvtBefore = (NodeChange) event.payload.before;
                    List<Constraint> constraints = getNodeConstraints(event, it -> it.type == Constraint.StreamsConstraintType.UNIQUE);
                    if (constraints.isEmpty()) {
                        return null;
                    } else {
                        return Triple.of(constraints, ((NodeChange) event.payload.before).labels, Map.of("properties", changeEvtBefore.properties));
                    }
                })
                .filter(Objects::nonNull)
                .map(pair -> {
                    Set<String> nodeKeys = pair.getLeft().stream().flatMap(c -> c.properties.stream()).collect(Collectors.toSet());
                    String query = "UNWIND $events as event\n" +
                            "MATCH (n" + getLabelsAsString(pair.getMiddle()) + "{" + getNodeKeysAsString(nodeKeys) + "})\n" +
                            "DETACH DELETE n";
                    return new QueryEvents(query, List.of(pair.getRight()));
                })
                .collect(Collectors.toList());
    }

    private List<QueryEvents> mergeRelationshipEvents(Collection<TransactionEvent> events) {
        return prepareRelationshipEvents(events.stream().filter(it -> it.payload.type == EntityType.relationship && it.meta.operation != OperationType.deleted).collect(Collectors.toList()), true)
                .entrySet().stream()
                .map(it -> {
                    String label = quote(it.getKey().label);
                    String query = "UNWIND $events as event\n" +
                            "MERGE (start" + getLabelsAsString(it.getKey().startLabels) + "{" + getNodeKeysAsString("start", it.getKey().startKeys) + "})\n" +
                            "MERGE (end" + getLabelsAsString(it.getKey().endLabels) + "{" + getNodeKeysAsString("end", it.getKey().endKeys) + "})\n" +
                            "MERGE (start)-[r:" + label + "]->(end)\n" +
                            "SET r = event.properties";
                    return new QueryEvents(query, it.getValue());
                }).collect(Collectors.toList());
    }

    private Map<RelationshipSchemaMetadata, List<Map<String, ?>>> prepareRelationshipEvents(List<TransactionEvent> events, boolean withProperties) {
        return events.stream()
                .map(event -> {
                    RelationshipPayload payload = (RelationshipPayload) event.payload;

                    List<Constraint> startNodeConstraints = getNodeConstraints(event, it -> it.type == Constraint.StreamsConstraintType.UNIQUE && payload.start.labels != null && payload.start.labels.contains(it.label));
                    List<Constraint> endNodeConstraints = getNodeConstraints(event, it -> it.type == Constraint.StreamsConstraintType.UNIQUE && payload.end.labels != null && payload.end.labels.contains(it.label));

                    if (constraintsAreEmpty(startNodeConstraints, endNodeConstraints)) {
                        return null;
                    } else {
                        return createRelationshipMetadata(payload, startNodeConstraints, endNodeConstraints, withProperties);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Pair::getKey, pair -> List.of(pair.getValue())));
    }

    private boolean constraintsAreEmpty(List<Constraint> startNodeConstraints, List<Constraint> endNodeConstraints) {
        return startNodeConstraints.isEmpty() || endNodeConstraints.isEmpty();
    }

    private Pair<RelationshipSchemaMetadata, Map<String, Map<String, ?>>> createRelationshipMetadata(RelationshipPayload payload, List<Constraint> startNodeConstraints, List<Constraint> endNodeConstraints, boolean withProperties) {
        List<String> startNodeLabels = payload.start.labels != null ? payload.start.labels : List.of();
        List<String> endNodeLabels = payload.end.labels != null ? payload.end.labels : List.of();

        Set<String> startNodeKeys = getNodeKeys(startNodeLabels, payload.start.ids.keySet(), startNodeConstraints);
        Set<String> endNodeKeys = getNodeKeys(endNodeLabels, payload.end.ids.keySet(), endNodeConstraints);
        Map<String, ?> start = payload.start.ids.entrySet().stream().filter(e -> startNodeKeys.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<String, ?> end = payload.end.ids.entrySet().stream().filter(e -> endNodeKeys.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (idsAreEmpty(start, end)) {
            return null;
        } else {
            Map<String, Map<String, ?>> value;
            if (withProperties) {
                Map<String, ?> properties = payload.after != null ? payload.after.properties : payload.before != null ? payload.before.properties : Map.of();
                value = Map.of("start", start, "end", end, "properties", properties);
            } else {
                value = Map.of("start", start, "end", end);
            }
            RelationshipSchemaMetadata key = new RelationshipSchemaMetadata(payload.label, startNodeLabels, endNodeLabels, start.keySet(), end.keySet());
            return Pair.of(key, value);
        }
    }

    private boolean idsAreEmpty(Map<String, ?> start, Map<String, ?> end) {
        return start.isEmpty() || end.isEmpty();
    }

    private List<QueryEvents> deleteRelationshipEvents(Collection<TransactionEvent> events) {
        return prepareRelationshipEvents(events.stream().filter(it -> it.payload.type == EntityType.relationship && it.meta.operation == OperationType.deleted).collect(Collectors.toList()), false)
                .entrySet().stream()
                .map(it -> {
                    String label = quote(it.getKey().label);
                    String query = "UNWIND $events as event\n" +
                            "MATCH (start" + getLabelsAsString(it.getKey().startLabels) + "{" + getNodeKeysAsString("start", it.getKey().startKeys) + "})\n" +
                            "MATCH (end" + getLabelsAsString(it.getKey().endLabels) + "{" + getNodeKeysAsString("end", it.getKey().endKeys) + "})\n" +
                            "MATCH (start)-[r:" + label + "]->(end)\n" +
                            "DELETE r";
                    return new QueryEvents(query, it.getValue());
                }).collect(Collectors.toList());
    }

    private List<String> minus(List<String> first, List<String> second) {
        ArrayList<String> list = new ArrayList<>(first);
        list.removeAll(second);
        return list;
    }

    private String getLabelsAsString(Collection<String> labels) {
        String labelString = labels.stream()
                .map(SchemaIngestionProcessor::quote)
                .collect(Collectors.joining(":"));
        if (labelString.isEmpty())
            return "";
        return ":" + labelString;
    }

    private String getNodeKeysAsString(Set<String> keys) {
        return getNodeKeysAsString("properties", keys);
    }

    private String getNodeKeysAsString(String prefix, Set<String> keys) {
        return keys.stream()
                .map(k -> {
                    String quoted = quote(k);
                    return quoted + ": event." + prefix + "." + quoted;
                })
                .collect(Collectors.joining(", "));
    }

    private static Set<String> getNodeKeys(List<String> labels, Set<String> propertyKeys, List<Constraint> constraints) {
        Optional<Constraint> constraint = constraints.stream().filter(c -> c.type == Constraint.StreamsConstraintType.UNIQUE
                && propertyKeys.containsAll(c.properties)
                && labels.contains(c.label))
                .min(Comparator.comparing(t -> t.properties.size()));

        return constraint.map(c -> c.properties).orElse(Set.of());
    }

    private static List<Constraint> getNodeConstraints(TransactionEvent event, Predicate<Constraint> filter) {
        return event.schema.constraints.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    private static String quote(String string) {
        return '`' + string + '`';
    }

}
