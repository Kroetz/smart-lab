package de.qaware.smartlabtest.generic

import de.qaware.smartlabapi.service.connector.generic.IBasicEntityManagementService
import de.qaware.smartlabcore.data.generic.IDto
import de.qaware.smartlabcore.data.generic.IEntity
import de.qaware.smartlabcore.data.generic.IIdentifier
import de.qaware.smartlabcore.exception.EntityConflictException
import de.qaware.smartlabcore.exception.EntityNotFoundException
import spock.lang.Specification

import static java.util.stream.Collectors.toList

abstract class CrudApiIntegrationTest<IdentifierT extends IIdentifier, DtoT extends IDto, EntityT extends IEntity<IdentifierT, DtoT>> extends Specification {

    protected IBasicEntityManagementService<EntityT, IdentifierT, DtoT> crudService
    protected Set<EntityT> entitiesForFindAll_withExisting
    protected EntityT entityForFindOne_withExisting
    protected IdentifierT entityIdForFindOne_withoutExisting
    protected Set<EntityT> allEntitiesForFindMultiple_withExisting
    protected Set<EntityT> allEntitiesForFindMultiple_withoutExisting
    protected Set<EntityT> requestedEntitiesForFindMultiple_withoutExisting
    protected EntityT entityForCreate_withoutConflict
    protected EntityT entityForCreate_withConflict
    protected EntityT entityForDelete_withExisting
    protected IdentifierT entityIdForDelete_withoutExisting

    def abstract setupDataForFindAll_withExisting()
    def abstract setupDataForFindOne_withExisting()
    def abstract setupDataForFindOne_withoutExisting()
    def abstract setupDataForFindMultiple_withExisting()
    def abstract setupDataForFindMultiple_withoutExisting()
    def abstract setupDataForCreate_withoutConflict()
    def abstract setupDataForCreate_withConflict()
    def abstract setupDataForDelete_withExisting()
    def abstract setupDataForDelete_withoutExisting()

    def mapEntityId = { entity -> entity.getId() }

    def "Get a set of all existing entities when there are entities (aka findAll_withExisting)"() {

        given: "There are entities available in the repository"
        setupDataForFindAll_withExisting()
        Set<EntityT> createdEntities = new HashSet<>()
        for (EntityT entity : entitiesForFindAll_withExisting) {
            createdEntities.add(crudService.create(entity))
        }

        when: "The set of entities is requested"
        Set<EntityT> foundEntities = crudService.findAll()

        then: "The returned set equals the one that was used to populate the repository"
        foundEntities == createdEntities

        cleanup:
        for (EntityT entity : createdEntities) {
            crudService.delete(entity.getId())
        }
    }

    def "Get a set of all existing entities when there are no entities (aka findAll_withoutExisting)"() {

        given: "There are no entities available in the repository"
        Set<EntityT> entities = new HashSet<EntityT>()

        when: "The set of entities is requested"
        Set<EntityT> foundEntities = crudService.findAll()

        then: "The returned set is empty"
        foundEntities == entities
    }

    def "Get a specific entity when the entity exists (aka findOne_withExisting)"() {

        given: "An entity with the requested entity ID does exist"
        setupDataForFindOne_withExisting()
        EntityT createdEntity = crudService.create(entityForFindOne_withExisting)

        when: "The entity is requested"
        EntityT foundEntity = crudService.findOne(createdEntity.getId())

        then: "The received entity equals the one that was initially put into the repository"
        foundEntity == createdEntity

        cleanup:
        crudService.delete(createdEntity.getId())
    }

    def "Get a specific entity when the entity does not exist (aka findOne_withoutExisting)"() {

        given: "An entity with the requested entity ID does not exist"
        setupDataForFindOne_withoutExisting()

        when: "The entity is requested"
        crudService.findOne(entityIdForFindOne_withoutExisting)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }

    def "Get a set of specific entities when the entities exist (aka findMultiple_withExisting)"() {

        given: "The entities with the requested entity IDs do exist"
        setupDataForFindMultiple_withExisting()
        List<EntityT> createdEntities = new ArrayList<>()
        for (EntityT entity : allEntitiesForFindMultiple_withExisting) {
            createdEntities.add(crudService.create(entity))
        }
        List<EntityT> requestedEntities = new ArrayList<>(createdEntities)
        requestedEntities.remove(0)
        IdentifierT[] requestedEntityIds = requestedEntities.stream()
                .map(mapEntityId)
                .collect(toList())
                .toArray()

        when: "The entities are requested"
        Set<EntityT> foundEntities = crudService.findMultiple(requestedEntityIds)

        then: "The received set of entities equals the one that was initially put into the repository"
        foundEntities == requestedEntities.toSet()

        cleanup:
        for (EntityT entity : createdEntities) {
            crudService.delete(entity.getId())
        }
    }

    def "Get a set of specific entities when the entities do not exist (aka findMultiple_withoutExisting)"() {

        given: "At least one of the entities with the requested entity IDs does not exist"
        setupDataForFindMultiple_withoutExisting()
        Set<EntityT> createdEntities = new HashSet<>()
        for (EntityT entity : allEntitiesForFindMultiple_withoutExisting) {
            createdEntities.add(crudService.create(entity))
        }
        IdentifierT[] requestedEntityIds = requestedEntitiesForFindMultiple_withoutExisting.stream()
                .map(mapEntityId)
                .collect(toList())
                .toArray()

        when: "The entities are requested"
        crudService.findMultiple(requestedEntityIds)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for (EntityT entity : createdEntities) {
            crudService.delete(entity.getId())
        }
    }

    def "Create an entity with an ID that does not yet exist (aka create_withoutConflict)"() {

        given: "The ID of the entity to create is not yet taken"
        setupDataForCreate_withoutConflict()

        when: "The entity is created"
        EntityT createdEntity = crudService.create(entityForCreate_withoutConflict)

        then: "The creation is successful and no exception is thrown"
        noExceptionThrown()

        when: "The created entity is requested"
        EntityT foundEntity = crudService.findOne(createdEntity.getId())

        then: "The found entity equals the one that was initially passed during the creation"
        createdEntity == foundEntity

        cleanup:
        crudService.delete(createdEntity.getId())
    }

    def "Create an entity with an ID that does already exist (aka create_withConflict)"() {

        given: "The ID of the entity to create is already taken"
        setupDataForCreate_withConflict()
        EntityT createdEntity = crudService.create(entityForCreate_withConflict)

        when: "The entity is created"
        crudService.create(entityForCreate_withConflict)

        then: "An exception is thrown"
        thrown(EntityConflictException)

        cleanup:
        crudService.delete(createdEntity.getId())
    }

    def "Delete an entity with an ID that does exist (aka delete_withExisting)"() {

        given: "The ID of the entity to delete is taken"
        setupDataForDelete_withExisting()
        EntityT createdEntity = crudService.create(entityForDelete_withExisting)

        when: "The entity is deleted"
        crudService.delete(createdEntity.getId())

        then: "The deletion is successful and no exception is thrown"
        noExceptionThrown()

        when: "The deleted entity is requested"
        crudService.findOne(createdEntity.getId())

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }

    def "Delete an entity with an ID that does not exist (aka delete_withoutExisting)"() {

        given: "The ID of the entity to delete is not taken"
        setupDataForDelete_withoutExisting()

        when: "The entity is deleted"
        crudService.delete(entityIdForDelete_withoutExisting)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }
}
