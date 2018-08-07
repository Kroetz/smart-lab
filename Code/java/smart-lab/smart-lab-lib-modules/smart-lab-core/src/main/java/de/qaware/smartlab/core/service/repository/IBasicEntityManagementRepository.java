package de.qaware.smartlab.core.service.repository;

import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import de.qaware.smartlab.core.miscellaneous.ICrudMethods;

public interface IBasicEntityManagementRepository<
        EntityT extends IEntity<IdentifierT>,
        IdentifierT extends IIdentifier>
        extends ICrudMethods<EntityT, IdentifierT> { }
