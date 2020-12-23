package com.sebastian_daschner.coffee.actions.boundary;

import com.sebastian_daschner.coffee.actions.control.SchemaIngestionProcessor;
import com.sebastian_daschner.coffee.actions.control.TransactionEventDeserializer;
import com.sebastian_daschner.coffee.actions.control.TransactionReverser;
import com.sebastian_daschner.coffee.actions.entity.Action;
import com.sebastian_daschner.coffee.actions.entity.RelationshipPayload;
import com.sebastian_daschner.coffee.actions.entity.TransactionEvent;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

import static com.sebastian_daschner.coffee.actions.entity.EntityType.relationship;
import static com.sebastian_daschner.coffee.actions.entity.OperationType.created;
import static com.sebastian_daschner.coffee.actions.entity.OperationType.updated;

@ApplicationScoped
public class Actions {

    @Inject
    SessionFactory sessionFactory;

    @Inject
    TransactionEventDeserializer deserializer;

    @Inject
    TransactionReverser transactionReverser;

    @Inject
    SchemaIngestionProcessor ingestionProcessor;

    public void undo(String actionId) {
        Session session = sessionFactory.openSession();

        Action action = session.load(Action.class, actionId);

        if (action == null || action.getJson() == null)
            throw new IllegalArgumentException("Action with id " + actionId + " is not available.");

        processCUDJson(action.getJson(), session);
    }

    private void processCUDJson(String json, Session session) {
        Collection<TransactionEvent> events = deserializer.deserializeEvents(json);

        Collection<TransactionEvent> reversed = transactionReverser.reverse(events);

        reversed.forEach(event -> {
            if (event.payload.type == relationship && (event.meta.operation == updated || event.meta.operation == created)) {
                RelationshipPayload payload = (RelationshipPayload) event.payload;
                if (payload.label.equals("TASTES")) {
                    payload.after.properties.put("percentage", ((Number) payload.after.properties.get("percentage")).doubleValue());
                }
            }
        });

        ingestionProcessor.processEvents(reversed).forEach(it -> {
            Transaction transaction = session.beginTransaction();
            try (transaction) {
                session.query(it.query, Map.of("events", it.events));
                transaction.commit();
            }
        });
    }

}
