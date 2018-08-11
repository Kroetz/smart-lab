package de.qaware.smartlab.core.service.repository;

import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import de.qaware.smartlab.core.exception.data.ConflictException;
import de.qaware.smartlab.core.exception.data.DataException;
import de.qaware.smartlab.core.exception.data.NotFoundException;
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
            String errorMessage = format("Cannot create entity %s because an entity with that ID already exists", entity);
            log.error(errorMessage);
            throw new ConflictException(errorMessage);
        }
        if(this.entities.add(entity)) {
            return entity;
        }
        String errorMessage = format("Cannot create entity %s because of unknown reason", entity);
        log.error(errorMessage);
        throw new DataException(errorMessage);
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
        if(entitiesToDelete.isEmpty()) throw new NotFoundException(entityId.getIdValue());
        boolean deleted =  this.entities.removeAll(entitiesToDelete);
        if(!deleted) throw new DataException(format("Could not delete entity \"%s\"", entityId.getIdValue()));
    }
}
