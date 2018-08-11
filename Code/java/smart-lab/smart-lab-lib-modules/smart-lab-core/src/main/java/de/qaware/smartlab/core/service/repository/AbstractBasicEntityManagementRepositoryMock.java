package de.qaware.smartlab.core.service.repository;

import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import de.qaware.smartlab.core.exception.entity.EntityConflictException;
import de.qaware.smartlab.core.exception.entity.EntityException;
import de.qaware.smartlab.core.exception.entity.EntityNotFoundException;
import de.qaware.smartlab.core.exception.SmartLabException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Repository
@Slf4j
public abstract class AbstractBasicEntityManagementRepositoryMock<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> extends AbstractBasicEntityManagementRepository<EntityT, IdentifierT> {

    protected Set<EntityT> entities;

    protected AbstractBasicEntityManagementRepositoryMock(Set<EntityT> initialData) {
        super(initialData);
        this.entities = new HashSet<>();
    }

    @Override
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
        throw new SmartLabException();
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
    public synchronized void delete(IdentifierT entityId) {
        List<EntityT> entitiesToDelete = this.entities.stream()
                .filter(entity -> entity.getId().equals(entityId))
                .collect(toList());
        if(entitiesToDelete.isEmpty()) throw new EntityNotFoundException(entityId.getIdValue());
        boolean deleted =  this.entities.removeAll(entitiesToDelete);
        if(!deleted) throw new EntityException(format("Could not delete entity \"%s\"", entityId.getIdValue()));
    }
}
