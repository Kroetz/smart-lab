package de.qaware.smartlab.core.data.generic;

import de.qaware.smartlab.core.exception.ResolverException;

public interface IBiResolver<KeyT, ValueT> extends IResolver<KeyT, ValueT> {

    KeyT inverseResolve(ValueT key) throws ResolverException;
}
