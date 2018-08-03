package de.qaware.smartlabcore.service.repository;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.miscellaneous.ICrudMethods;

public interface IBasicEntityManagementRepository<
        EntityT extends IEntity<IdentifierT>,
        IdentifierT extends IIdentifier>
        extends ICrudMethods<EntityT, IdentifierT> { }
