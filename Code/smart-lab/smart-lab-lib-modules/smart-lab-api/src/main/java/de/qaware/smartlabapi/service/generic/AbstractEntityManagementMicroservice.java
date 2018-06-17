package de.qaware.smartlabapi.service.generic;

import de.qaware.smartlabapi.client.generic.IEntityManagementApiClient;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MeetingConflictException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import feign.FeignException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Set;

public abstract class AbstractEntityManagementMicroservice<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IEntityManagementService<EntityT, IdentifierT> {

    protected final IEntityManagementApiClient<EntityT> entityManagementApiClient;

    public AbstractEntityManagementMicroservice(IEntityManagementApiClient<EntityT> entityManagementApiClient) {
        this.entityManagementApiClient = entityManagementApiClient;
    }

    @Override
    public Set<EntityT> findAll() {
        try {
            return entityManagementApiClient.findAll();
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public EntityT findOne(IdentifierT entityId) {
        try {
            return entityManagementApiClient.findOne(entityId.getIdValue()).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<EntityT> findMultiple(IdentifierT[] entityIds) {
        try {
            return entityManagementApiClient.findMultiple(Arrays.stream(entityIds)
                    .map(IIdentifier::getIdValue)
                    .toArray(String[]::new))
                    .getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                // TODO: Incorporate the IDs of the entities that were not found
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void create(EntityT entity) {
        try {
            entityManagementApiClient.create(entity);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new MeetingConflictException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void delete(IdentifierT entityId) {
        try {
            entityManagementApiClient.delete(entityId.getIdValue());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }
}