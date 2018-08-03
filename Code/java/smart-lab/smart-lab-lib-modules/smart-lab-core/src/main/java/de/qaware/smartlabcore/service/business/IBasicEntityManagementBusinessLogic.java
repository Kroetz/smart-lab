package de.qaware.smartlabcore.service.business;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.miscellaneous.ICrudMethods;

public interface IBasicEntityManagementBusinessLogic<
        EntityT extends IEntity<IdentifierT>,
        IdentifierT extends IIdentifier>
        extends ICrudMethods<EntityT, IdentifierT> { }
