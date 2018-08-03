package de.qaware.smartlabapi.service.connector.person;

import de.qaware.smartlabapi.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.person.dto.PersonDto;

public interface IPersonManagementService extends IBasicEntityManagementService<IPerson, PersonId, PersonDto> { }
