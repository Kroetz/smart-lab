package de.qaware.smartlabapi.service.connector.generic;

import de.qaware.smartlabapi.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import feign.FeignException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Set;

public abstract class AbstractBasicEntityManagementMicroserviceConnector<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementService<EntityT, IdentifierT> {

    protected final IBasicEntityManagementApiClient<EntityT> entityManagementApiClient;

    public AbstractBasicEntityManagementMicroserviceConnector(IBasicEntityManagementApiClient<EntityT> entityManagementApiClient) {
        this.entityManagementApiClient = entityManagementApiClient;
    }

    @Override
    public Set<EntityT> findAll() {
        try {
            return this.entityManagementApiClient.findAll();
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public EntityT findOne(IdentifierT entityId) {
        try {
            return this.entityManagementApiClient.findOne(entityId.getIdValue()).getBody();
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
            return this.entityManagementApiClient.findMultiple(Arrays.stream(entityIds)
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
    public EntityT create(EntityT entity) {
        try {
            return this.entityManagementApiClient.create(entity).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new EntityConflictException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<EntityT> create(Set<EntityT> entities) {
        try {
            return this.entityManagementApiClient.create(entities).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new EntityConflictException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void delete(IdentifierT entityId) {
        try {
            this.entityManagementApiClient.delete(entityId.getIdValue());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }
}
