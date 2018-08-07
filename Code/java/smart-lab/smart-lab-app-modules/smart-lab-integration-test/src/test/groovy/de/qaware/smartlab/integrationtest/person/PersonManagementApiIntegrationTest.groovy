package de.qaware.smartlab.integrationtest.person

import de.qaware.smartlab.api.service.connector.person.IPersonManagementService
import de.qaware.smartlab.core.data.person.IPerson
import de.qaware.smartlab.core.data.person.PersonId
import de.qaware.smartlab.core.data.person.PersonDto
import de.qaware.smartlab.data.set.factory.AstronautsSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.CoastGuardSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.FireFightersSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.ForestRangersSampleDataSetFactory
import de.qaware.smartlab.integrationtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import static java.util.Arrays.asList

@SpringBootTest
class PersonManagementApiIntegrationTest extends CrudApiIntegrationTest<PersonId, PersonDto, IPerson> {

    @Autowired
    private IPersonManagementService personManagementService

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
        crudService = personManagementService
        entitiesForFindAll_withExisting = new HashSet<>(asList(
                coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE),
                forestRangersDataFactory.createWorkgroupMemberMap().get(forestRangersDataFactory.MEMBER_ID_ANNA),
                fireFightersDataFactory.createWorkgroupMemberMap().get(fireFightersDataFactory.MEMBER_ID_ANTHONY)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = personManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = personManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.MEMBER_ID_ALICE
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = personManagementService
        def personId1 = coastGuardDataFactory.MEMBER_ID_ALICE
        def personId2 = forestRangersDataFactory.MEMBER_ID_ANNA
        def personId3 = fireFightersDataFactory.MEMBER_ID_ANTHONY
        def person1 = coastGuardDataFactory.createWorkgroupMemberMap().get(personId1)
        def person2 = forestRangersDataFactory.createWorkgroupMemberMap().get(personId2)
        def person3 = fireFightersDataFactory.createWorkgroupMemberMap().get(personId3)
        allEntitiesForFindMultiple_withExisting = [person1, person2, person3]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = personManagementService
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
        crudService = personManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = personManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = personManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createWorkgroupMemberMap().get(coastGuardDataFactory.MEMBER_ID_ALICE)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = personManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.MEMBER_ID_ALICE
    }
}
