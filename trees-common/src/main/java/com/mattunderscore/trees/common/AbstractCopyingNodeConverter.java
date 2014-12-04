package com.mattunderscore.trees.common;

import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Abstract copying node converter that captures the source and target types from the supertype and delegates to a
 * copying node converter.
 * @author Matt Champion
 */
public abstract class AbstractCopyingNodeConverter<E, N extends Node<E>, T extends Tree<E, N>, S extends Node<E>> implements NodeToTreeConverter<E, N, T, S>, SPISupportAwareComponent {
    private final DelegateCopyingNodeToTreeConverter<E, N, T, S> delegateConverter;

    public AbstractCopyingNodeConverter() {
        final Class<S> sourceType;
        final Class<T> targetType;
        final ParameterizedType concreteParameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type nodeType = concreteParameterizedType.getActualTypeArguments()[3];
        if (nodeType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) nodeType;
            sourceType = (Class<S>) parameterizedType.getRawType();
        }
        else if (nodeType instanceof Class) {
            sourceType = (Class<S>) nodeType;
        }
        else {
            throw new IllegalStateException("Unable to identify tree class");
        }

        final Type treeType = concreteParameterizedType.getActualTypeArguments()[2];
        if (treeType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) treeType;
            targetType = (Class<T>) parameterizedType.getRawType();
        }
        else if (treeType instanceof Class) {
            targetType = (Class<T>) treeType;
        }
        else {
            throw new IllegalStateException("Unable to identify tree class");
        }

        delegateConverter = new DelegateCopyingNodeToTreeConverter<>(sourceType, targetType);
    }

    @Override
    public final T treeFromRootNode(S node) {
        return delegateConverter.treeFromRootNode(node);
    }

    @Override
    public final Class<S> forClass() {
        return delegateConverter.forClass();
    }

    @Override
    public final void setSupport(SPISupport support) {
        delegateConverter.setSupport(support);
    }
}
