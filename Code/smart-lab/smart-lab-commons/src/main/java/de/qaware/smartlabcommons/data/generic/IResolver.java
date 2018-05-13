package de.qaware.smartlabcommons.data.generic;

import java.util.Optional;

public interface IResolver<K, V> {

    Optional<V> resolve(K key);
}
