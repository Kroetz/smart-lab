package de.qaware.smartlabcommons.api.service.generic;

import de.qaware.smartlabcommons.api.client.generic.IEntityManagementApiClient;
import de.qaware.smartlabcommons.data.generic.IEntity;
import de.qaware.smartlabcommons.exception.EntityNotFoundException;
import de.qaware.smartlabcommons.exception.MeetingConflictException;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import feign.FeignException;
import org.springframework.http.HttpStatus;

import java.util.Set;

public class AbstractEntityManagementService<T extends IEntity> implements IEntityManagementService<T> {

    protected final IEntityManagementApiClient<T> entityManagementApiClient;

    public AbstractEntityManagementService(IEntityManagementApiClient<T> entityManagementApiClient) {
        this.entityManagementApiClient = entityManagementApiClient;
    }

    @Override
    public Set<T> findAll() {
        try {
            return entityManagementApiClient.findAll();
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public T findOne(String entityId) {
        try {
            return entityManagementApiClient.findOne(entityId).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<T> findMultiple(String[] entityIds) {
        try {
            return entityManagementApiClient.findMultiple(entityIds).getBody();
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
    public void create(T entity) {
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
    public void delete(String entityId) {
        try {
            entityManagementApiClient.delete(entityId);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }
}
