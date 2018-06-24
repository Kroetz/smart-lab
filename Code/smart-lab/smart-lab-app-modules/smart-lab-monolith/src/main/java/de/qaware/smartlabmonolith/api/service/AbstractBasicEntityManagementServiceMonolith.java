package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.generic.controller.IBasicEntityManagementController;

import java.util.Arrays;
import java.util.Set;

public abstract class AbstractBasicEntityManagementServiceMonolith<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementService<EntityT, IdentifierT> {

    protected final IBasicEntityManagementController<EntityT> entityManagementController;

    public AbstractBasicEntityManagementServiceMonolith(IBasicEntityManagementController<EntityT> entityManagementController) {
        this.entityManagementController = entityManagementController;
    }

    @Override
    public Set<EntityT> findAll() {
        return this.entityManagementController.findAll();
    }

    @Override
    public EntityT findOne(IdentifierT entityId) {
        return this.entityManagementController.findOne(entityId.getIdValue()).getBody();
    }

    @Override
    public Set<EntityT> findMultiple(IdentifierT[] entityIds) {
        return this.entityManagementController.findMultiple(Arrays.stream(entityIds)
                .map(IIdentifier::getIdValue)
                .toArray(String[]::new))
                .getBody();
    }

    @Override
    public void create(EntityT entity) {
        this.entityManagementController.create(entity);
    }

    @Override
    public void delete(IdentifierT entityId) {
        this.entityManagementController.delete(entityId.getIdValue());
    }
}