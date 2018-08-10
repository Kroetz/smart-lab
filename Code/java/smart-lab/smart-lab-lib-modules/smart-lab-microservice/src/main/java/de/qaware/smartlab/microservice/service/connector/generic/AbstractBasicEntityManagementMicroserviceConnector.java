package de.qaware.smartlab.microservice.service.connector.generic;

import de.qaware.smartlab.api.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import de.qaware.smartlab.core.exception.SmartLabException;

import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public abstract class AbstractBasicEntityManagementMicroserviceConnector<
        EntityT extends IEntity<IdentifierT>,
        IdentifierT extends IIdentifier,
        DtoT extends IDto> implements IBasicEntityManagementService<EntityT, IdentifierT, DtoT> {

    protected final IBasicEntityManagementApiClient<EntityT, DtoT> entityManagementApiClient;
    protected final IDtoConverter<EntityT, DtoT> converter;

    protected AbstractBasicEntityManagementMicroserviceConnector(
            IBasicEntityManagementApiClient<EntityT, DtoT> entityManagementApiClient,
            IDtoConverter<EntityT, DtoT> converter) {
        this.entityManagementApiClient = entityManagementApiClient;
        this.converter = converter;
    }

    @Override
    public Set<EntityT> findAll() {
        try {
            return this.entityManagementApiClient.findAll().stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public EntityT findOne(IdentifierT entityId) {
        try {
            DtoT foundEntity = this.entityManagementApiClient.findOne(entityId.getIdValue()).getBody();
            requireNonNull(foundEntity);
            return this.converter.toEntity(foundEntity);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public Set<EntityT> findMultiple(IdentifierT[] entityIds) {
        try {
            return requireNonNull(this.entityManagementApiClient.findMultiple(stream(entityIds)
                    .map(IIdentifier::getIdValue)
                    .toArray(String[]::new))
                    .getBody())
                    .stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public EntityT create(EntityT entity) {
        try {
            DtoT createdEntity = this.entityManagementApiClient.create(this.converter.toDto(entity)).getBody();
            requireNonNull(createdEntity);
            return this.converter.toEntity(createdEntity);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public Set<EntityT> create(Set<EntityT> entities) {
        try {
            return requireNonNull(this.entityManagementApiClient.create(entities.stream()
                    .map(this.converter::toDto)
                    .collect(toSet())).getBody())
                    .stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void delete(IdentifierT entityId) {
        try {
            this.entityManagementApiClient.delete(entityId.getIdValue());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }
}
