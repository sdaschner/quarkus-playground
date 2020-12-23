package com.sebastian_daschner.coffee.actions.control;

import com.sebastian_daschner.coffee.actions.entity.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionEventDeserializer {

    @Inject
    Jsonb jsonb;

    public Collection<TransactionEvent> deserializeEvents(String json) {
        return ((List<?>) jsonb.fromJson(json, List.class)).stream()
                .map(this::asStreamsTransactionEvent)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private TransactionEvent asStreamsTransactionEvent(Object obj) {
        if (obj instanceof TransactionEvent) {
            return (TransactionEvent) obj;
        }
        Map<String, Map<String, Object>> value;
        if (obj instanceof Map) {
            value = (Map<String, Map<String, Object>>) obj;
        } else if (obj instanceof String) {
            value = jsonb.fromJson((String) obj, Map.class);
        } else if (obj instanceof byte[]) {
            value = jsonb.fromJson(new String((byte[]) obj), Map.class);
        } else {
            value = convertValue(obj, Map.class);
        }

        Meta meta = convertValue(value.get("meta"), Meta.class);
        Schema schema = convertValue(value.get("schema"), Schema.class);

        Map<String, Object> payloadMap = value.get("payload");
        String type = payloadMap.get("type").toString();
        String id = payloadMap.get("id").toString();

        Payload payload;
        if (type.equals("node")) {
            NodeChange before = (payloadMap.get("before") != null) ? convertValue(payloadMap.get("before"), NodeChange.class) : null;
            NodeChange after = (payloadMap.get("after") != null) ? convertValue(payloadMap.get("after"), NodeChange.class) : null;
            payload = new NodePayload(id, before, after, EntityType.node);
        } else {
            String label = payloadMap.get("label").toString();
            RelationshipNodeChange start = convertValue(payloadMap.get("start"), RelationshipNodeChange.class);
            RelationshipNodeChange end = convertValue(payloadMap.get("end"), RelationshipNodeChange.class);
            RelationshipChange before = (payloadMap.get("before") != null) ? convertValue(payloadMap.get("before"), RelationshipChange.class) : null;
            RelationshipChange after = (payloadMap.get("after") != null) ? convertValue(payloadMap.get("after"), RelationshipChange.class) : null;
            payload = new RelationshipPayload(id, start, end, before, after, label);
        }
        return new TransactionEvent(meta, payload, schema);
    }

    @SuppressWarnings("unchecked")
    private <T> T convertValue(Object object, Class<T> toClass) {
        if (toClass.isAssignableFrom(object.getClass()))
            return (T) object;
        return jsonb.fromJson(jsonb.toJson(object), toClass);
    }

}
