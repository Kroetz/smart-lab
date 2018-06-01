package de.qaware.smartlabcore.generic.repository;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.generic.miscellaneous.ICrudMethods;

public interface IEntityManagementRepository<T extends IEntity> extends ICrudMethods<T> {

}
