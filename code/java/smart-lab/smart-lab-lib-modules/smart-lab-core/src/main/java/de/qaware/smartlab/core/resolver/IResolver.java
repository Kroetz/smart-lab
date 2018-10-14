package de.qaware.smartlab.core.resolver;

import de.qaware.smartlab.core.exception.resolver.ResolverException;

public interface IResolver<KeyT, ValueT> {

    ValueT resolve(KeyT key) throws ResolverException;
}
