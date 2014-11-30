package com.mattunderscore.trees.construction;

import com.mattunderscore.trees.tree.Tree;

import java.lang.reflect.ParameterizedType;

/**
 * Type key to support generics when constructing trees.
 * @author Matt Champion
 */
public abstract class TypeKey<T extends Tree<?, ?>> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public TypeKey() {
        final ParameterizedType concreteParameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final ParameterizedType parameterizedType = (ParameterizedType) concreteParameterizedType.getActualTypeArguments()[0];
        type = (Class<T>) parameterizedType.getRawType();
    }

    public final Class<T> getType() {
        return type;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TypeKey typeKey = (TypeKey) o;

        return type.equals(typeKey.type);
    }

    @Override
    public final int hashCode() {
        return type.hashCode();
    }

    @Override
    public final String toString() {
        return "TypeKey:" + type.getName();
    }
}
