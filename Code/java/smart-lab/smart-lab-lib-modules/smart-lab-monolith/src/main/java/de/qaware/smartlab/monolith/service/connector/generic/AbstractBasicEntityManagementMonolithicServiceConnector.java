package de.qaware.smartlab.monolith.service.connector.generic;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import de.qaware.smartlab.core.service.controller.IBasicEntityManagementController;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public abstract class AbstractBasicEntityManagementMonolithicServiceConnector<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier, DtoT extends IDto> implements IBasicEntityManagementService<EntityT, IdentifierT, DtoT> {

    protected final IBasicEntityManagementController<DtoT> entityManagementController;
    protected final IDtoConverter<EntityT, DtoT> converter;

    protected AbstractBasicEntityManagementMonolithicServiceConnector(
            IBasicEntityManagementController<DtoT> entityManagementController,
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
        return this.converter.toEntity(requireNonNull(response.getBody()));
    }

    @Override
    public Set<EntityT> findMultiple(IdentifierT[] entityIds) {
        ResponseEntity<Set<DtoT>> response = this.entityManagementController
                .findMultiple(stream(entityIds)
                .map(IIdentifier::getIdValue)
                .toArray(String[]::new));
        return requireNonNull(response.getBody()).stream().map(this.converter::toEntity).collect(toSet());
    }

    @Override
    public EntityT create(EntityT entity) {
        ResponseEntity<DtoT> response = this.entityManagementController.create(this.converter.toDto(entity));
        return this.converter.toEntity(requireNonNull(response.getBody()));
    }

    @Override
    public Set<EntityT> create(Set<EntityT> entities) {
        ResponseEntity<Set<DtoT>> response = this.entityManagementController.create(entities.stream()
                .map(this.converter::toDto)
                .collect(toSet()));
        return requireNonNull(response.getBody()).stream().map(this.converter::toEntity).collect(toSet());
    }

    @Override
    public void delete(IdentifierT entityId) {
        this.entityManagementController.delete(entityId.getIdValue());
    }
}
