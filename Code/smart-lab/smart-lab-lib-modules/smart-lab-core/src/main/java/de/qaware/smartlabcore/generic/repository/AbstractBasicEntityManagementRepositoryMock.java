package de.qaware.smartlabcore.generic.repository;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.result.CreationResult;
import de.qaware.smartlabcore.result.DeletionResult;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public abstract class AbstractBasicEntityManagementRepositoryMock<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementRepository<EntityT, IdentifierT> {

    protected Set<EntityT> entities;

    protected boolean exists(IdentifierT entityId) {
        return this.entities.stream()
                .anyMatch(entity -> entity.getId().equals(entityId));
    }

    @Override
    public Set<EntityT> findAll() {
        return this.entities;
    }

    @Override
    public Optional<EntityT> findOne(IdentifierT entityId) {
        return this.entities.stream()
                .filter(entity -> entity.getId().equals(entityId))
                .findFirst();
    }

    @Override
    public Map<IdentifierT, Optional<EntityT>> findMultiple(Set<IdentifierT> entityIds) {
        Map<IdentifierT, Optional<EntityT>> entitiesById = new HashMap<>();
        entityIds.forEach(entityId -> entitiesById.put(entityId, findOne(entityId)));
        return entitiesById;
    }

    @Override
    public CreationResult create(EntityT entity) {
        if (exists(entity.getId())) {
            return CreationResult.CONFLICT;
        }
        if(this.entities.add(entity)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult delete(IdentifierT entityId) {
        List<EntityT> entitiesToDelete = this.entities.stream()
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
}