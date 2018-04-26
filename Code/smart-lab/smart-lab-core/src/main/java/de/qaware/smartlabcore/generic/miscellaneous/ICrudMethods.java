package de.qaware.smartlabcore.generic.miscellaneous;

import de.qaware.smartlabcommons.data.IEntity;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;

import java.util.Optional;
import java.util.Set;

public interface ICrudMethods<T extends IEntity> {

    Set<T> findAll();
    Optional<T> findOne(String entityId);
    CreationResult create(T entity);
    DeletionResult delete(String entityId);
}
