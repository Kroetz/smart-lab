package de.qaware.smartlabcore.generic.business;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.generic.miscellaneous.ICrudMethods;

public interface IBasicEntityManagementBusinessLogic<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> extends ICrudMethods<EntityT, IdentifierT> { }
