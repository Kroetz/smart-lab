package de.qaware.smartlabcore.data.generic;

import java.util.*;

public abstract class AbstractResolver<K, V> implements IResolver<K, V> {

    private final Map<K, V> valuesByKey;

    public AbstractResolver(Map<K, V> valuesByKey) {
        this.valuesByKey = valuesByKey;
    }

    public Optional<V> resolve(K key) {
        return Optional.ofNullable(valuesByKey.get(key));
    }
}
