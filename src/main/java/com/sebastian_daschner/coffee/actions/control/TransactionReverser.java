package com.sebastian_daschner.coffee.actions.control;

import com.sebastian_daschner.coffee.actions.entity.TransactionEvent;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sebastian_daschner.coffee.actions.entity.EntityType.node;
import static com.sebastian_daschner.coffee.actions.entity.EntityType.relationship;
import static com.sebastian_daschner.coffee.actions.entity.OperationType.*;

@ApplicationScoped
public class TransactionReverser {

    public Collection<TransactionEvent> reverse(Collection<TransactionEvent> events) {

        return events.stream()
                .map(event -> {
                    if (event.meta.operation == updated)
                        return updatedNodeOrRel(event);

                    else if (event.payload.type == node && event.meta.operation == created)
                        return createdNode(event);
                    else if (event.payload.type == node && event.meta.operation == deleted)
                        return deletedNode(event);

                    else if (event.payload.type == relationship && event.meta.operation == created)
                        return createdRel(event);
                    else if (event.payload.type == relationship && event.meta.operation == deleted)
                        return deletedRel(event);

                    System.err.println("Don't know what to do with this transaction, ignoring: " + event.payload.type + ", " + event.meta.operation);
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private TransactionEvent updatedNodeOrRel(TransactionEvent event) {
        event.swapBeforeAfterProperties();
        return event;
    }

    private TransactionEvent createdNode(TransactionEvent event) {
        event.meta.operation = deleted;
        event.swapBeforeAfterProperties();
        return event;
    }

    private TransactionEvent deletedNode(TransactionEvent event) {
        event.meta.operation = created;
        event.swapBeforeAfterProperties();
        return event;
    }

    private TransactionEvent createdRel(TransactionEvent event) {
        event.meta.operation = deleted;
        event.swapBeforeAfterProperties();
        return event;
    }

    private TransactionEvent deletedRel(TransactionEvent event) {
        event.meta.operation = created;
        event.swapBeforeAfterProperties();
        return event;
    }

}
