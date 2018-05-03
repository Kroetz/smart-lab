package de.qaware.smartlabcore.generic.miscellaneous;

import de.qaware.smartlabcommons.data.IEntity;
import de.qaware.smartlabcommons.result.CreationResult;
import de.qaware.smartlabcommons.result.DeletionResult;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ICrudMethods<T extends IEntity> {

    Set<T> findAll();
    Optional<T> findOne(String entityId);
    Map<String, Optional<T>> findMultiple(Set<String> entityIds);
    CreationResult create(T entity);
    DeletionResult delete(String entityId);
}
