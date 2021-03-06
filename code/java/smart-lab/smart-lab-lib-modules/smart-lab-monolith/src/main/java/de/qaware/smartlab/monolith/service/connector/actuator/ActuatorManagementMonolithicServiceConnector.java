package de.qaware.smartlab.monolith.service.connector.actuator;

import de.qaware.smartlab.actuator.management.service.controller.ActuatorManagementController;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.monolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MONOLITH)
public class ActuatorManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IActuator, ActuatorId, ActuatorDto> implements IActuatorManagementService {

    private final ActuatorManagementController actuatorManagementController;

    public ActuatorManagementMonolithicServiceConnector(
            ActuatorManagementController actuatorManagementController,
            IDtoConverter<IActuator, ActuatorDto> actuatorConverter) {
        super(actuatorManagementController, actuatorConverter);
        this.actuatorManagementController = actuatorManagementController;
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_ACTUATOR_MANAGEMENT_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(ActuatorManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
