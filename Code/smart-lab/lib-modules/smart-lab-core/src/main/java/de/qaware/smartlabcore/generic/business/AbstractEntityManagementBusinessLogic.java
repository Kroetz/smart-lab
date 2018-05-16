package de.qaware.smartlabcore.generic.business;

import de.qaware.smartlabcommons.data.generic.IEntity;
import de.qaware.smartlabcommons.result.CreationResult;
import de.qaware.smartlabcommons.result.DeletionResult;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractEntityManagementBusinessLogic<T extends IEntity> implements IEntityManagementBusinessLogic<T> {

    protected final IEntityManagementRepository<T> entityManagementRepository;

    public AbstractEntityManagementBusinessLogic(IEntityManagementRepository<T> entityManagementRepository) {
        this.entityManagementRepository = entityManagementRepository;
    }

    @Override
    public Set<T> findAll() {
        return entityManagementRepository.findAll();
    }

    @Override
    public Optional<T> findOne(String entityId) {
        return entityManagementRepository.findOne(entityId);
    }

    @Override
    public Map<String, Optional<T>> findMultiple(Set<String> entityIds) {
        return entityManagementRepository.findMultiple(entityIds);
    }

    @Override
    public CreationResult create(T entity) {
        return entityManagementRepository.create(entity);
    }

    @Override
    public DeletionResult delete(String entityId) {
        return entityManagementRepository.delete(entityId);
    }
}
