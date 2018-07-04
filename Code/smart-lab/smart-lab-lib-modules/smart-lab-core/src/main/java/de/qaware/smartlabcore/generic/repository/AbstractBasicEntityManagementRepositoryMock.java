package de.qaware.smartlabcore.generic.repository;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityCreationException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.result.DeletionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public abstract class AbstractBasicEntityManagementRepositoryMock<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementRepository<EntityT, IdentifierT> {

    protected Set<EntityT> entities;
    protected final Set<EntityT> initialData;

    public AbstractBasicEntityManagementRepositoryMock(Set<EntityT> initialData) {
        this.initialData = initialData;
    }

    @PostConstruct
    private void populateWithInitialData() {
        try {
            create(this.initialData);
        }
        catch(EntityCreationException e) {
            log.error("Could not populate repository with initial data", e);
        }
    }

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
    public synchronized EntityT create(EntityT entity) {
        if (exists(entity.getId())) {
            log.error("Cannot create entity {} because an entity with that ID already exists", entity);
            // TODO: Meaningful exception messages
            throw new EntityConflictException();
        }
        if(this.entities.add(entity)) {
            return entity;
        }
        throw new UnknownErrorException();
    }

    @Override
    public synchronized Set<EntityT> create(Set<EntityT> entities) {
        Set<EntityT> createdEntities = new HashSet<>();
        for(EntityT entity : entities) {
            createdEntities.add(create(entity));
        }
        return createdEntities;
    }

    @Override
    public synchronized DeletionResult delete(IdentifierT entityId) {
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
