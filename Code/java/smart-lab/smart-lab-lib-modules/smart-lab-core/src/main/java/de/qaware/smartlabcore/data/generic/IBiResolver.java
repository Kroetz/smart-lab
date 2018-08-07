package de.qaware.smartlabcore.data.generic;

import de.qaware.smartlabcore.exception.ResolverException;

public interface IBiResolver<KeyT, ValueT> extends IResolver<KeyT, ValueT> {

    KeyT inverseResolve(ValueT key) throws ResolverException;
}
