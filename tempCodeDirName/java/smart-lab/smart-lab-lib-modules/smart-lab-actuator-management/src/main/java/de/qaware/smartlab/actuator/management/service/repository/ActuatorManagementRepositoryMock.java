package de.qaware.smartlab.actuator.management.service.repository;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Slf4j
public class ActuatorManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IActuator, ActuatorId> implements IActuatorManagementRepository {

    public ActuatorManagementRepositoryMock(Set<IActuator> initialActuators) {
        super(initialActuators);
    }
}
