package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.generic.IEntityManagementService;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.generic.controller.IEntityManagementController;

import java.util.Set;

public class AbstractEntityManagementServiceMonolith<T extends IEntity> implements IEntityManagementService<T> {

    protected final IEntityManagementController<T> entityManagementController;

    public AbstractEntityManagementServiceMonolith(IEntityManagementController<T> entityManagementController) {
        this.entityManagementController = entityManagementController;
    }

    @Override
    public Set<T> findAll() {
        return this.entityManagementController.findAll();
    }

    @Override
    public T findOne(String entityId) {
        return this.entityManagementController.findOne(entityId).getBody();
    }

    @Override
    public Set<T> findMultiple(String[] entityIds) {
        return this.entityManagementController.findMultiple(entityIds).getBody();
    }

    @Override
    public void create(T entity) {
        this.entityManagementController.create(entity);
    }

    @Override
    public void delete(String entityId) {
        this.entityManagementController.delete(entityId);
    }
}
