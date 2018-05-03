package de.qaware.smartlabcore.device

import de.qaware.smartlabcommons.api.service.device.IDeviceManagementService
import de.qaware.smartlabcommons.data.device.IDevice
import de.qaware.smartlabcore.data.sample.factory.AstronautsDataFactory
import de.qaware.smartlabcore.data.sample.factory.CoastGuardDataFactory
import de.qaware.smartlabcore.data.sample.factory.FireFightersDataFactory
import de.qaware.smartlabcore.data.sample.factory.ForestRangersDataFactory
import de.qaware.smartlabcore.data.sample.provider.EmptySampleDataProvider
import de.qaware.smartlabcore.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(EmptySampleDataProvider.PROFILE_NAME)
class DeviceManagementApiIntegrationTest extends CrudApiIntegrationTest<IDevice> {

    @Autowired
    private IDeviceManagementService deviceManagementService

    @Autowired
    private CoastGuardDataFactory coastGuardDataFactory

    @Autowired
    private ForestRangersDataFactory forestRangersDataFactory

    @Autowired
    private FireFightersDataFactory fireFightersDataFactory

    @Autowired
    private AstronautsDataFactory astronautsDataFactory

    @Override
    def setupDataForFindAll_withExisting() {
        crudService = deviceManagementService
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
                coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY),
                forestRangersDataFactory.createDeviceMap().get(forestRangersDataFactory.DEVICE_ID_GREEN_MICROPHONE),
                fireFightersDataFactory.createDeviceMap().get(fireFightersDataFactory.DEVICE_ID_RED_DISPLAY)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = deviceManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = deviceManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = deviceManagementService
        def deviceId1 = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY
        def deviceId2 = forestRangersDataFactory.DEVICE_ID_GREEN_MICROPHONE
        def deviceId3 = fireFightersDataFactory.DEVICE_ID_RED_DISPLAY
        def device1 = coastGuardDataFactory.createDeviceMap().get(deviceId1)
        def device2 = forestRangersDataFactory.createDeviceMap().get(deviceId2)
        def device3 = fireFightersDataFactory.createDeviceMap().get(deviceId3)
        allEntitiesForFindMultiple_withExisting = [device1, device2, device3]
        requestedEntitiesForFindMultiple_withExisting = [device1, device2]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = deviceManagementService
        def deviceId1 = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY
        def deviceId2 = forestRangersDataFactory.DEVICE_ID_GREEN_MICROPHONE
        def deviceId3 = fireFightersDataFactory.DEVICE_ID_RED_DISPLAY
        def device1 = coastGuardDataFactory.createDeviceMap().get(deviceId1)
        def device2 = forestRangersDataFactory.createDeviceMap().get(deviceId2)
        def device3 = fireFightersDataFactory.createDeviceMap().get(deviceId3)
        allEntitiesForFindMultiple_withoutExisting = [device1, device2]
        requestedEntitiesForFindMultiple_withoutExisting = [device2, device3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = deviceManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = deviceManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = deviceManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = deviceManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY
    }
}
