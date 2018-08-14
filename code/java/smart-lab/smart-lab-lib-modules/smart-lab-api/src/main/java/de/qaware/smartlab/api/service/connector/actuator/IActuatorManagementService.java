package de.qaware.smartlab.api.service.connector.actuator;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;

public interface IActuatorManagementService extends IBasicEntityManagementService<IActuator, ActuatorId, ActuatorDto> { }
