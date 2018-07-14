package de.qaware.smartlabmonolith.service.connector.generic;

import de.qaware.smartlabapi.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Set;

public abstract class AbstractBasicEntityManagementMonolithicServiceConnector<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementService<EntityT, IdentifierT> {

    protected final IBasicEntityManagementController<EntityT> entityManagementController;

    public AbstractBasicEntityManagementMonolithicServiceConnector(IBasicEntityManagementController<EntityT> entityManagementController) {
        this.entityManagementController = entityManagementController;
    }

    @Override
    public Set<EntityT> findAll() {
        return this.entityManagementController.findAll();
    }

    @Override
    public EntityT findOne(IdentifierT entityId) {
        ResponseEntity<EntityT> response = this.entityManagementController.findOne(entityId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public Set<EntityT> findMultiple(IdentifierT[] entityIds) {
        ResponseEntity<Set<EntityT>> response = this.entityManagementController
                .findMultiple(Arrays.stream(entityIds)
                .map(IIdentifier::getIdValue)
                .toArray(String[]::new));
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public EntityT create(EntityT entity) {
        ResponseEntity<EntityT> response = this.entityManagementController.create(entity);
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        throw new UnknownErrorException();
    }

    @Override
    public Set<EntityT> create(Set<EntityT> entities) {
        ResponseEntity<Set<EntityT>> response = this.entityManagementController.create(entities);
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        throw new UnknownErrorException();
    }

    @Override
    public void delete(IdentifierT entityId) {
        ResponseEntity<Void> response = this.entityManagementController.delete(entityId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }
}