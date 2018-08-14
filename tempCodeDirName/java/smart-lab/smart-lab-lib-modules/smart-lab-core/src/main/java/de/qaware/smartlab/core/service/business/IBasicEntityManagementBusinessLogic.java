package de.qaware.smartlab.core.service.business;

import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import de.qaware.smartlab.core.miscellaneous.ICrudMethods;

public interface IBasicEntityManagementBusinessLogic<
        EntityT extends IEntity<IdentifierT>,
        IdentifierT extends IIdentifier>
        extends ICrudMethods<EntityT, IdentifierT> { }
