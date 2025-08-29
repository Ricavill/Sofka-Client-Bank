package com.sofka.sofkaClient.shared.config.data;

import org.springframework.expression.ExpressionParser;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanDecorator<T> {
    private final T bean;
    private ExpressionParser expressionParser;

    public BeanDecorator(T bean) {
        this.bean = bean;
    }

    public T getBean() {
        return bean;
    }

    public void setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    public Object getValue(String property) {
        if (expressionParser != null) {
            return expressionParser.parseExpression(property).getValue(bean);
        }

        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, bean.getClass());
            Method method = descriptor.getReadMethod();
            return method.invoke(bean);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException ignored) {
            return null;
        }
    }

    public void setValue(String property, Object value) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, bean.getClass());
            Method method = descriptor.getWriteMethod();
            method.invoke(bean, value);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }
}
