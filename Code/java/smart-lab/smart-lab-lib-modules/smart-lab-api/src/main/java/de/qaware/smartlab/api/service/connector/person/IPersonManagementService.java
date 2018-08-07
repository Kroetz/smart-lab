package de.qaware.smartlab.api.service.connector.person;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonDto;
import de.qaware.smartlabcore.data.person.PersonId;

public interface IPersonManagementService extends IBasicEntityManagementService<IPerson, PersonId, PersonDto> { }
