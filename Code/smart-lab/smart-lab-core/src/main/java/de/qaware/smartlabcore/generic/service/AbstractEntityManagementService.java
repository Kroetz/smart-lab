package de.qaware.smartlabcore.generic.service;

import de.qaware.smartlabcommons.data.IEntity;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractEntityManagementService<T extends IEntity> implements IEntityManagementService<T> {

    protected final IEntityManagementRepository<T> entityManagementRepository;

    public AbstractEntityManagementService(IEntityManagementRepository<T> entityManagementRepository) {
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
