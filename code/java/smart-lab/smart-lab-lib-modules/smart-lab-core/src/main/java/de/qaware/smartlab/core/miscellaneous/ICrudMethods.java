package de.qaware.smartlab.core.miscellaneous;

import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ICrudMethods<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> {

    Set<EntityT> findAll();
    Optional<EntityT> findOne(IdentifierT entityId);
    Map<IdentifierT, Optional<EntityT>> findMultiple(Set<IdentifierT> entityIds);
    EntityT create(EntityT entity);
    Set<EntityT> create(Set<EntityT> entities);
    void delete(IdentifierT entityId);
}
