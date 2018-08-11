package de.qaware.smartlab.api.service.client.generic;

import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.miscellaneous.IHttpCrudMethods;

public interface IBasicEntityManagementApiClient<DtoT extends IDto> extends IHttpCrudMethods<DtoT> { }
