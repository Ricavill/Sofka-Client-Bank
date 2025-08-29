package com.sofka.sofkaClient.shared.config.data.history;

import com.sofka.sofkaClient.shared.config.data.BeanDecorator;
import org.springframework.expression.ExpressionParser;

import java.util.Objects;

public class Memento<T> {

    private final BeanDecorator<T> snapshot;
    private final BeanDecorator<T> originator;

    public Memento(T snapshot, T originator) {
        this.snapshot = new BeanDecorator<>(snapshot);
        this.originator = new BeanDecorator<>(originator);
    }

    public BeanDecorator<T> getSnapshot() {
        return snapshot;
    }

    public BeanDecorator<T> getOriginator() {
        return originator;
    }

    public void setExpressionParser(ExpressionParser expressionParser) {
        originator.setExpressionParser(expressionParser);
        snapshot.setExpressionParser(expressionParser);
    }

    public boolean isDirty(String property) {
        Object snapshotValue = snapshot.getValue(property);
        Object currentValue = originator.getValue(property);

        if (snapshotValue == null && currentValue == null) {
            return false;
        }

        return (snapshotValue == null && currentValue != null)
                || (snapshotValue != null && currentValue == null)
                || (!Objects.equals(snapshotValue, currentValue));
    }
}