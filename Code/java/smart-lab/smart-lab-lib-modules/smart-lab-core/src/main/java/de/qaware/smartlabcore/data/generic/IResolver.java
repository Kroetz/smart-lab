package de.qaware.smartlabcore.data.generic;

import de.qaware.smartlabcore.exception.ResolverException;

public interface IResolver<KeyT, ValueT> {

    ValueT resolve(KeyT key) throws ResolverException;
}
