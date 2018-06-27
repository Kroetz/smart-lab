package de.qaware.smartlabcore.data.generic;

import java.util.Optional;

public interface IBiResolver<KeyT, ValueT> extends IResolver<KeyT, ValueT> {

    Optional<KeyT> inverseResolve(ValueT key);
}
