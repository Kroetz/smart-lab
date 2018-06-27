package de.qaware.smartlabcore.data.generic;

import java.util.*;

public abstract class AbstractResolver<KeyT, ValueT> implements IResolver<KeyT, ValueT> {

    private final Map<KeyT, ValueT> valuesByKey;

    public AbstractResolver(Map<KeyT, ValueT> valuesByKey) {
        this.valuesByKey = valuesByKey;
    }

    public Optional<ValueT> resolve(KeyT key) {
        return Optional.ofNullable(this.valuesByKey.get(key));
    }
}
