package de.qaware.smartlabcommons.api.service.generic;

import de.qaware.smartlabcommons.data.IEntity;

import java.util.Set;

public interface IEntityManagementService<T extends IEntity> {

    Set<T> findAll();
    T findOne(String entityId);
    Set<T> findMultiple(String[] entityIds);
    void create(T entity);
    void delete(String entityId);
}
