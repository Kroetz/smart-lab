package de.qaware.smartlabcore.workgroup

import de.qaware.smartlabcommons.api.client.IWorkgroupManagementApiClient
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup
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
class WorkgroupManagementApiIntegrationTest extends CrudApiIntegrationTest<IWorkgroup> {

    @Autowired
    private IWorkgroupManagementApiClient workgroupManagementApiClient

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
        crudApiClient = workgroupManagementApiClient
        entitiesForFindAll_withExisting = Arrays.asList(
                coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD),
                forestRangersDataFactory.createWorkgroupMap().get(forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS),
                fireFightersDataFactory.createWorkgroupMap().get(fireFightersDataFactory.WORKGROUP_ID_FIRE_FIGHTERS))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudApiClient = workgroupManagementApiClient
        entityForFindOne_withExisting = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudApiClient = workgroupManagementApiClient
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudApiClient = workgroupManagementApiClient
        entityForCreate_withoutConflict = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudApiClient = workgroupManagementApiClient
        entityForCreate_withConflict = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudApiClient = workgroupManagementApiClient
        entityForDelete_withExisting = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudApiClient = workgroupManagementApiClient
        entityIdForDelete_withoutExisting = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
    }
}
