package de.qaware.smartlab.core.service.controller;

import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.miscellaneous.IHttpCrudMethods;

public interface IBasicEntityManagementController<DtoT extends IDto> extends IHttpCrudMethods<DtoT> { }
