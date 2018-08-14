package de.qaware.smartlab.api.service.connector.person;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonDto;
import de.qaware.smartlab.core.data.person.PersonId;

public interface IPersonManagementService extends IBasicEntityManagementService<IPerson, PersonId, PersonDto> { }
