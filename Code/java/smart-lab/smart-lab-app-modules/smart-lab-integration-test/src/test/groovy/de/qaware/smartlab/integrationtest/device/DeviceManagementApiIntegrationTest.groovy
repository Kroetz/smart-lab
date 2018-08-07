package de.qaware.smartlab.integrationtest.device

import de.qaware.smartlab.api.service.connector.device.IDeviceManagementService
import de.qaware.smartlab.core.data.device.DeviceDto
import de.qaware.smartlab.core.data.device.DeviceId
import de.qaware.smartlab.core.data.device.IDevice
import de.qaware.smartlab.data.set.factory.AstronautsSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.CoastGuardSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.FireFightersSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.ForestRangersSampleDataSetFactory
import de.qaware.smartlab.integrationtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import static java.util.Arrays.asList

@SpringBootTest
class DeviceManagementApiIntegrationTest extends CrudApiIntegrationTest<DeviceId, DeviceDto, IDevice> {

    @Autowired
    private IDeviceManagementService deviceManagementService

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
        crudService = deviceManagementService
        entitiesForFindAll_withExisting = new HashSet<>(asList(
                coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG),
                forestRangersDataFactory.createDeviceMap().get(forestRangersDataFactory.DEVICE_ID_GREEN_DISPLAY_BIG),
                fireFightersDataFactory.createDeviceMap().get(fireFightersDataFactory.DEVICE_ID_RED_MICROPHONE)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = deviceManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = deviceManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = deviceManagementService
        def deviceId1 = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG
        def deviceId2 = forestRangersDataFactory.DEVICE_ID_GREEN_DISPLAY_BIG
        def deviceId3 = fireFightersDataFactory.DEVICE_ID_RED_MICROPHONE
        def device1 = coastGuardDataFactory.createDeviceMap().get(deviceId1)
        def device2 = forestRangersDataFactory.createDeviceMap().get(deviceId2)
        def device3 = fireFightersDataFactory.createDeviceMap().get(deviceId3)
        allEntitiesForFindMultiple_withExisting = [device1, device2, device3]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = deviceManagementService
        def deviceId1 = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG
        def deviceId2 = forestRangersDataFactory.DEVICE_ID_GREEN_DISPLAY_BIG
        def deviceId3 = fireFightersDataFactory.DEVICE_ID_RED_MICROPHONE
        def device1 = coastGuardDataFactory.createDeviceMap().get(deviceId1)
        def device2 = forestRangersDataFactory.createDeviceMap().get(deviceId2)
        def device3 = fireFightersDataFactory.createDeviceMap().get(deviceId3)
        allEntitiesForFindMultiple_withoutExisting = [device1, device2]
        requestedEntitiesForFindMultiple_withoutExisting = [device2, device3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = deviceManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = deviceManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = deviceManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createDeviceMap().get(coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = deviceManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.DEVICE_ID_BLUE_DISPLAY_BIG
    }
}
