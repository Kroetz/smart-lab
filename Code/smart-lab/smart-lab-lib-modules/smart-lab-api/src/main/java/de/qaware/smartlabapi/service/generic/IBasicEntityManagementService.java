package de.qaware.smartlabapi.service.generic;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;

import java.util.Set;

public interface IBasicEntityManagementService<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> {

    Set<EntityT> findAll();
    EntityT findOne(IdentifierT entityId);
    Set<EntityT> findMultiple(IdentifierT[] entityIds);
    void create(EntityT entity);
    void delete(IdentifierT entityId);
}
