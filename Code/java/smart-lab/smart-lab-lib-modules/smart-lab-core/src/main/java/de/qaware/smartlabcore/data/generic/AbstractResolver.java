package de.qaware.smartlabcore.data.generic;

import java.util.*;

import static java.lang.String.format;

public abstract class AbstractResolver<KeyT, ValueT> implements IResolver<KeyT, ValueT> {

    private final Map<KeyT, ValueT> valuesByKey;

    public AbstractResolver(Set<Map.Entry<KeyT, ValueT>> entries) {
        this.valuesByKey = new HashMap<>();
        for(Map.Entry<KeyT, ValueT> entry : entries) {
            if(this.valuesByKey.containsKey(entry.getKey())) throw new IllegalArgumentException(format(
                    "The keys of all entries must be unique but the key %s is already present",
                    entry.getKey().toString()));
            this.valuesByKey.put(entry.getKey(), entry.getValue());
        }
    }

    public Optional<ValueT> resolve(KeyT key) {
        return Optional.ofNullable(this.valuesByKey.get(key));
    }
}
