package de.qaware.smartlabcore.data.generic;

import com.google.common.collect.BiMap;

import java.util.Optional;

public class AbstractBiResolver<KeyT, ValueT> implements IBiResolver<KeyT, ValueT> {

    private final BiMap<KeyT, ValueT> valuesByKey;

    public AbstractBiResolver(BiMap<KeyT, ValueT> valuesByKey) {
        this.valuesByKey = valuesByKey;
    }

    public Optional<ValueT> resolve(KeyT key) {
        return Optional.ofNullable(this.valuesByKey.get(key));
    }

    @Override
    public Optional<KeyT> inverseResolve(ValueT key) {
        return Optional.ofNullable(this.valuesByKey.inverse().get(key));
    }
}
