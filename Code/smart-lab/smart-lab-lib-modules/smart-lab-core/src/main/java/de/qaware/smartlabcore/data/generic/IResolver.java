package de.qaware.smartlabcore.data.generic;

import java.util.Optional;

public interface IResolver<KeyT, ValueT> {

    Optional<ValueT> resolve(KeyT key);
}
