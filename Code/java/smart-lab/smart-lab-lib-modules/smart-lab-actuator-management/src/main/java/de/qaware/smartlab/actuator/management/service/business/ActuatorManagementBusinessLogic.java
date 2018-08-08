package de.qaware.smartlab.actuator.management.service.business;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.actuator.management.service.repository.IActuatorManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActuatorManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IActuator, ActuatorId> implements IActuatorManagementBusinessLogic {

    private final IActuatorManagementRepository actuatorManagementRepository;

    public ActuatorManagementBusinessLogic(IActuatorManagementRepository actuatorManagementRepository) {
        super(actuatorManagementRepository);
        this.actuatorManagementRepository = actuatorManagementRepository;
    }
}
