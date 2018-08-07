package de.qaware.smartlabmonolith.service.connector.generic;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public abstract class AbstractBasicEntityManagementMonolithicServiceConnector<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier, DtoT extends IDto> implements IBasicEntityManagementService<EntityT, IdentifierT, DtoT> {

    protected final IBasicEntityManagementController<EntityT, DtoT> entityManagementController;
    protected final IDtoConverter<EntityT, DtoT> converter;

    public AbstractBasicEntityManagementMonolithicServiceConnector(
            IBasicEntityManagementController<EntityT, DtoT> entityManagementController,
            IDtoConverter<EntityT, DtoT> converter) {
        this.entityManagementController = entityManagementController;
        this.converter = converter;
    }

    @Override
    public Set<EntityT> findAll() {
        return this.entityManagementController.findAll().stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public EntityT findOne(IdentifierT entityId) {
        ResponseEntity<DtoT> response = this.entityManagementController.findOne(entityId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.converter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public Set<EntityT> findMultiple(IdentifierT[] entityIds) {
        ResponseEntity<Set<DtoT>> response = this.entityManagementController
                .findMultiple(stream(entityIds)
                .map(IIdentifier::getIdValue)
                .toArray(String[]::new));
        if(response.getStatusCode() == HttpStatus.OK) return requireNonNull(response.getBody()).stream().map(this.converter::toEntity).collect(toSet());
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public EntityT create(EntityT entity) {
        ResponseEntity<DtoT> response = this.entityManagementController.create(this.converter.toDto(entity));
        if(response.getStatusCode() == HttpStatus.OK) return this.converter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        throw new UnknownErrorException();
    }

    @Override
    public Set<EntityT> create(Set<EntityT> entities) {
        ResponseEntity<Set<DtoT>> response = this.entityManagementController.create(entities.stream()
                .map(this.converter::toDto)
                .collect(toSet()));
        if(response.getStatusCode() == HttpStatus.OK) return requireNonNull(response.getBody()).stream().map(this.converter::toEntity).collect(toSet());;
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
