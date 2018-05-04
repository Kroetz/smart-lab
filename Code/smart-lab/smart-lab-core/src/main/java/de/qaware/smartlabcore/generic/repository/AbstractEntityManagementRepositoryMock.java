package de.qaware.smartlabcore.generic.repository;

import de.qaware.smartlabcommons.data.generic.IEntity;
import de.qaware.smartlabcommons.result.CreationResult;
import de.qaware.smartlabcommons.result.DeletionResult;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public abstract class AbstractEntityManagementRepositoryMock<T extends IEntity> implements IEntityManagementRepository<T> {

    protected Set<T> entities;

    protected boolean exists(String entityId) {
        return entities.stream()
                .anyMatch(entity -> entity.getId().equals(entityId));
    }

    @Override
    public Set<T> findAll() {
        return this.entities;
    }

    @Override
    public Optional<T> findOne(String entityId) {
        return this.entities.stream()
                .filter(entity -> entity.getId().equals(entityId))
                .findFirst();
    }

    @Override
    public Map<String, Optional<T>> findMultiple(Set<String> entityIds) {
        Map<String, Optional<T>> entitiesById = new HashMap<>();
        entityIds.forEach(entityId -> entitiesById.put(entityId, findOne(entityId)));
        return entitiesById;
    }

    @Override
    public CreationResult create(T entity) {
        if (exists(entity.getId())) {
            return CreationResult.CONFLICT;
        }
        if(this.entities.add(entity)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult delete(String entityId) {
        List<IEntity> entitiesToDelete = this.entities.stream()
                .filter(entity -> entity.getId().equals(entityId))
                .collect(Collectors.toList());
        if(entitiesToDelete.isEmpty()) {
            return DeletionResult.NOT_FOUND;
        }
        boolean deleted =  this.entities.removeAll(entitiesToDelete);
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    protected void sortEntitiesById(List<T> entities) {
        entities.sort(Comparator.comparing(IEntity::getId));
    }
}
