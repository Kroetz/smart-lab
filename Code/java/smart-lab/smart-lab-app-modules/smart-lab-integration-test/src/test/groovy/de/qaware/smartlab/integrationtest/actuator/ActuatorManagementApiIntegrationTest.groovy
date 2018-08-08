package de.qaware.smartlab.integrationtest.actuator

import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService
import de.qaware.smartlab.core.data.actuator.ActuatorDto
import de.qaware.smartlab.core.data.actuator.ActuatorId
import de.qaware.smartlab.core.data.actuator.IActuator
import de.qaware.smartlab.data.set.factory.AstronautsSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.CoastGuardSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.FireFightersSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.ForestRangersSampleDataSetFactory
import de.qaware.smartlab.integrationtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import static java.util.Arrays.asList

@SpringBootTest
class ActuatorManagementApiIntegrationTest extends CrudApiIntegrationTest<ActuatorId, ActuatorDto, IActuator> {

    @Autowired
    private IActuatorManagementService actuatorManagementService

    @Autowired
    private CoastGuardSampleDataSetFactory coastGuardDataFactory

    @Autowired
    private ForestRangersSampleDataSetFactory forestRangersDataFactory

    @Autowired
    private FireFightersSampleDataSetFactory fireFightersDataFactory

    @Autowired
    private AstronautsSampleDataSetFactory astronautsDataFactory

    @Override
    def setupDataForFindAll_withExisting() {
        crudService = actuatorManagementService
        entitiesForFindAll_withExisting = new HashSet<>(asList(
                coastGuardDataFactory.createActuatorMap().get(coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG),
                forestRangersDataFactory.createActuatorMap().get(forestRangersDataFactory.ACTUATOR_ID_GREEN_DISPLAY_BIG),
                fireFightersDataFactory.createActuatorMap().get(fireFightersDataFactory.ACTUATOR_ID_RED_MICROPHONE)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = actuatorManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createActuatorMap().get(coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = actuatorManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = actuatorManagementService
        def actuatorId1 = coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG
        def actuatorId2 = forestRangersDataFactory.ACTUATOR_ID_GREEN_DISPLAY_BIG
        def actuatorId3 = fireFightersDataFactory.ACTUATOR_ID_RED_MICROPHONE
        def actuator1 = coastGuardDataFactory.createActuatorMap().get(actuatorId1)
        def actuator2 = forestRangersDataFactory.createActuatorMap().get(actuatorId2)
        def actuator3 = fireFightersDataFactory.createActuatorMap().get(actuatorId3)
        allEntitiesForFindMultiple_withExisting = [actuator1, actuator2, actuator3]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = actuatorManagementService
        def actuatorId1 = coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG
        def actuatorId2 = forestRangersDataFactory.ACTUATOR_ID_GREEN_DISPLAY_BIG
        def actuatorId3 = fireFightersDataFactory.ACTUATOR_ID_RED_MICROPHONE
        def actuator1 = coastGuardDataFactory.createActuatorMap().get(actuatorId1)
        def actuator2 = forestRangersDataFactory.createActuatorMap().get(actuatorId2)
        def actuator3 = fireFightersDataFactory.createActuatorMap().get(actuatorId3)
        allEntitiesForFindMultiple_withoutExisting = [actuator1, actuator2]
        requestedEntitiesForFindMultiple_withoutExisting = [actuator2, actuator3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = actuatorManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createActuatorMap().get(coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = actuatorManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createActuatorMap().get(coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = actuatorManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createActuatorMap().get(coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = actuatorManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.ACTUATOR_ID_BLUE_DISPLAY_BIG
    }
}
