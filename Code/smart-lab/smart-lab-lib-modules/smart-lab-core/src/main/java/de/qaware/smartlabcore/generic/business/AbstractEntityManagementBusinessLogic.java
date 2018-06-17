package de.qaware.smartlabcore.generic.business;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.result.CreationResult;
import de.qaware.smartlabcore.result.DeletionResult;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractEntityManagementBusinessLogic<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IEntityManagementBusinessLogic<EntityT, IdentifierT> {

    protected final IEntityManagementRepository<EntityT, IdentifierT> entityManagementRepository;

    public AbstractEntityManagementBusinessLogic(IEntityManagementRepository<EntityT, IdentifierT> entityManagementRepository) {
        this.entityManagementRepository = entityManagementRepository;
    }

    @Override
    public Set<EntityT> findAll() {
        return entityManagementRepository.findAll();
    }

    @Override
    public Optional<EntityT> findOne(IdentifierT entityId) {
        return entityManagementRepository.findOne(entityId);
    }

    @Override
    public Map<IdentifierT, Optional<EntityT>> findMultiple(Set<IdentifierT> entityIds) {
        return entityManagementRepository.findMultiple(entityIds);
    }

    @Override
    public CreationResult create(EntityT entity) {
        return entityManagementRepository.create(entity);
    }

    @Override
    public DeletionResult delete(IdentifierT entityId) {
        return entityManagementRepository.delete(entityId);
    }
}
