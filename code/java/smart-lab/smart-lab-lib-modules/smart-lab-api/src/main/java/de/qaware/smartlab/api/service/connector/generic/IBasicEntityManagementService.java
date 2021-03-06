package de.qaware.smartlab.api.service.connector.generic;

import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;

import java.util.Set;

public interface IBasicEntityManagementService<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier, DtoT extends IDto> {

    Set<EntityT> findAll();
    EntityT findOne(IdentifierT entityId);
    Set<EntityT> findMultiple(IdentifierT[] entityIds);
    EntityT create(EntityT entity);
    Set<EntityT> create(Set<EntityT> entities);
    void delete(IdentifierT entityId);
}
