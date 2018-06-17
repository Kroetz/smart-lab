package de.qaware.smartlabcore.generic.miscellaneous;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.result.CreationResult;
import de.qaware.smartlabcore.result.DeletionResult;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ICrudMethods<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> {

    Set<EntityT> findAll();
    Optional<EntityT> findOne(IdentifierT entityId);
    Map<IdentifierT, Optional<EntityT>> findMultiple(Set<IdentifierT> entityIds);
    CreationResult create(EntityT entity);
    DeletionResult delete(IdentifierT entityId);
}
