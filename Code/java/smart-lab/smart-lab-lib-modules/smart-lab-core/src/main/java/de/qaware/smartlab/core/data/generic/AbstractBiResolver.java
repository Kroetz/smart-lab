package de.qaware.smartlab.core.data.generic;

import com.google.common.collect.BiMap;
import de.qaware.smartlab.core.exception.ResolverException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBiResolver<KeyT, ValueT> implements IBiResolver<KeyT, ValueT> {

    private final BiMap<KeyT, ValueT> valuesByKey;

    protected AbstractBiResolver(BiMap<KeyT, ValueT> valuesByKey) {
        this.valuesByKey = valuesByKey;
    }

    @Override
    public ValueT resolve(KeyT key) throws ResolverException {
        if(this.valuesByKey.containsKey(key)) return this.valuesByKey.get(key);
        String errorMessage = getErrorMessage(key);
        log.error(errorMessage);
        throw new ResolverException(errorMessage);
    }

    @Override
    public KeyT inverseResolve(ValueT key) throws ResolverException {
        if(this.valuesByKey.inverse().containsKey(key)) return this.valuesByKey.inverse().get(key);
        String errorMessage = getInverseErrorMessage(key);
        log.error(errorMessage);
        throw new ResolverException(errorMessage);
    }

    protected abstract String getErrorMessage(KeyT key);
    protected abstract String getInverseErrorMessage(ValueT key);
}
