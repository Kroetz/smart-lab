package de.qaware.smartlab.core.data.generic;

import de.qaware.smartlab.core.exception.resolver.ResolverException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
public abstract class AbstractResolver<KeyT, ValueT> implements IResolver<KeyT, ValueT> {

    private final Map<KeyT, ValueT> valuesByKey;

    protected AbstractResolver(Set<Map.Entry<KeyT, ValueT>> entries) {
        this.valuesByKey = new HashMap<>();
        for(Map.Entry<KeyT, ValueT> entry : entries) {
            if(this.valuesByKey.containsKey(entry.getKey())) throw new IllegalArgumentException(format(
                    "The keys of all entries must be unique but the key %s is already present",
                    entry.getKey().toString()));
            this.valuesByKey.put(entry.getKey(), entry.getValue());
        }
    }

    public ValueT resolve(KeyT key) throws ResolverException {
        if(this.valuesByKey.containsKey(key)) return this.valuesByKey.get(key);
        String errorMessage = getErrorMessage(key);
        log.error(errorMessage);
        throw new ResolverException(errorMessage);
    }

    protected abstract String getErrorMessage(KeyT key);
}
