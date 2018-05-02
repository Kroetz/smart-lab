package de.qaware.smartlabcore.person

import de.qaware.smartlabcommons.api.client.IPersonManagementApiClient
import de.qaware.smartlabcommons.data.person.IPerson
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
class PersonManagementApiIntegrationTest extends CrudApiIntegrationTest<IPerson> {

    @Autowired
    private IPersonManagementApiClient personManagementApiClient

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
        crudApiClient = personManagementApiClient
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
                coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE),
                forestRangersDataFactory.createWorkgroupMemberMap().get(forestRangersDataFactory.MEMBER_ID_ANNA),
                fireFightersDataFactory.createWorkgroupMemberMap().get(fireFightersDataFactory.MEMBER_ID_ANTHONY)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudApiClient = personManagementApiClient
        entityForFindOne_withExisting = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudApiClient = personManagementApiClient
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.MEMBER_ID_ALICE
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudApiClient = personManagementApiClient
        def personId1 = coastGuardDataFactory.MEMBER_ID_ALICE
        def personId2 = forestRangersDataFactory.MEMBER_ID_ANNA
        def personId3 = fireFightersDataFactory.MEMBER_ID_ANTHONY
        def person1 = coastGuardDataFactory.createWorkgroupMemberMap().get(personId1)
        def person2 = forestRangersDataFactory.createWorkgroupMemberMap().get(personId2)
        def person3 = fireFightersDataFactory.createWorkgroupMemberMap().get(personId3)
        allEntitiesForFindMultiple_withExisting = [person1, person2, person3]
        requestedEntitiesForFindMultiple_withExisting = [person1, person2]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudApiClient = personManagementApiClient
        def personId1 = coastGuardDataFactory.MEMBER_ID_ALICE
        def personId2 = forestRangersDataFactory.MEMBER_ID_ANNA
        def personId3 = fireFightersDataFactory.MEMBER_ID_ANTHONY
        def person1 = coastGuardDataFactory.createWorkgroupMemberMap().get(personId1)
        def person2 = forestRangersDataFactory.createWorkgroupMemberMap().get(personId2)
        def person3 = fireFightersDataFactory.createWorkgroupMemberMap().get(personId3)
        allEntitiesForFindMultiple_withoutExisting = [person1, person2]
        requestedEntitiesForFindMultiple_withoutExisting = [person2, person3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudApiClient = personManagementApiClient
        entityForCreate_withoutConflict = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudApiClient = personManagementApiClient
        entityForCreate_withConflict = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudApiClient = personManagementApiClient
        entityForDelete_withExisting = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudApiClient = personManagementApiClient
        entityIdForDelete_withoutExisting = coastGuardDataFactory.MEMBER_ID_ALICE
    }
}