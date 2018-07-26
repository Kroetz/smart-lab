package de.qaware.smartlabcore.service.business;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.result.DeletionResult;
import de.qaware.smartlabcore.service.repository.IBasicEntityManagementRepository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractBasicEntityManagementBusinessLogic<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementBusinessLogic<EntityT, IdentifierT> {

    protected final IBasicEntityManagementRepository<EntityT, IdentifierT> entityManagementRepository;

    public AbstractBasicEntityManagementBusinessLogic(IBasicEntityManagementRepository<EntityT, IdentifierT> entityManagementRepository) {
        this.entityManagementRepository = entityManagementRepository;
    }

    @Override
    public Set<EntityT> findAll() {
        return this.entityManagementRepository.findAll();
    }

    @Override
    public Optional<EntityT> findOne(IdentifierT entityId) {
        return this.entityManagementRepository.findOne(entityId);
    }

    @Override
    public Map<IdentifierT, Optional<EntityT>> findMultiple(Set<IdentifierT> entityIds) {
        return this.entityManagementRepository.findMultiple(entityIds);
    }

    @Override
    public EntityT create(EntityT entity) {
        return this.entityManagementRepository.create(entity);
    }

    @Override
    public Set<EntityT> create(Set<EntityT> entities) {
        return this.entityManagementRepository.create(entities);
    }

    @Override
    public DeletionResult delete(IdentifierT entityId) {
        return this.entityManagementRepository.delete(entityId);
    }
}
