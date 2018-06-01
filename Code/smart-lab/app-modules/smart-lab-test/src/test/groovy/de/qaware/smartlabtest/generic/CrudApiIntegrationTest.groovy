package de.qaware.smartlabtest.generic

import de.qaware.smartlabapi.service.generic.IEntityManagementService
import de.qaware.smartlabcommons.data.generic.IEntity
import de.qaware.smartlabcommons.exception.EntityNotFoundException
import de.qaware.smartlabcommons.exception.MeetingConflictException
import spock.lang.Specification

import java.util.stream.Collectors

abstract class CrudApiIntegrationTest<T extends IEntity> extends Specification {

    protected IEntityManagementService<T> crudService
    protected Set<T> entitiesForFindAll_withExisting
    protected T entityForFindOne_withExisting
    protected String entityIdForFindOne_withoutExisting
    protected Set<T> allEntitiesForFindMultiple_withExisting
    protected Set<T> requestedEntitiesForFindMultiple_withExisting
    protected Set<T> allEntitiesForFindMultiple_withoutExisting
    protected Set<T> requestedEntitiesForFindMultiple_withoutExisting
    protected T entityForCreate_withoutConflict
    protected T entityForCreate_withConflict
    protected T entityForDelete_withExisting
    protected String entityIdForDelete_withoutExisting

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
        for (def entity : entitiesForFindAll_withExisting) {
            crudService.create(entity)
        }

        when: "The set of entities is requested"
        Set<T> foundEntities = crudService.findAll()

        then: "The returned set equals the one that was used to populate the repository"
        foundEntities == entitiesForFindAll_withExisting

        cleanup:
        for (def entity : entitiesForFindAll_withExisting) {
            crudService.delete(entity.getId())
        }
    }

    def "Get a set of all existing entities when there are no entities (aka findAll_withoutExisting)"() {

        given: "There are no entities available in the repository"
        Set<T> entities = new HashSet<T>()

        when: "The set of entities is requested"
        Set<T> foundEntities = crudService.findAll()

        then: "The returned set is empty"
        foundEntities == entities
    }

    def "Get a specific entity when the entity exists (aka findOne_withExisting)"() {

        given: "An entity with the requested entity ID does exist"
        setupDataForFindOne_withExisting()
        crudService.create(entityForFindOne_withExisting)

        when: "The entity is requested"
        def foundEntity = crudService.findOne(entityForFindOne_withExisting.getId())

        then: "The received entity equals the one that was initially put into the repository"
        foundEntity == entityForFindOne_withExisting

        cleanup:
        crudService.delete(entityForFindOne_withExisting.getId())
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
        for (def entity : allEntitiesForFindMultiple_withExisting) {
            crudService.create(entity)
        }
        String[] requestedEntityIds = requestedEntitiesForFindMultiple_withExisting.stream()
                .map(mapEntityId)
                .collect(Collectors.toList())
                .toArray()

        when: "The entities are requested"
        def foundEntities = crudService.findMultiple(requestedEntityIds)

        then: "The received set of entities equals the one that was initially put into the repository"
        foundEntities == requestedEntitiesForFindMultiple_withExisting

        cleanup:
        for (def entity : allEntitiesForFindMultiple_withExisting) {
            crudService.delete(entity.getId())
        }
    }

    def "Get a set of specific entities when the entities do not exist (aka findMultiple_withoutExisting)"() {

        given: "At least one of the entities with the requested entity IDs does not exist"
        setupDataForFindMultiple_withoutExisting()
        for (def entity : allEntitiesForFindMultiple_withoutExisting) {
            crudService.create(entity)
        }
        String[] requestedEntityIds = requestedEntitiesForFindMultiple_withoutExisting.stream()
                .map(mapEntityId)
                .collect(Collectors.toList())
                .toArray()

        when: "The entities are requested"
        crudService.findMultiple(requestedEntityIds)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for (def entity : allEntitiesForFindMultiple_withoutExisting) {
            crudService.delete(entity.getId())
        }
    }

    def "Create an entity with an ID that does not yet exist (aka create_withoutConflict)"() {

        given: "The ID of the entity to create is not yet taken"
        setupDataForCreate_withoutConflict()

        when: "The entity is created"
        crudService.create(entityForCreate_withoutConflict)

        then: "The creation is successful and no exception is thrown"
        noExceptionThrown()

        when: "The created entity is requested"
        def foundEntity = crudService.findOne(entityForCreate_withoutConflict.getId())

        then: "The found entity equals the one that was initially passed during the creation"
        entityForCreate_withoutConflict == foundEntity

        cleanup:
        crudService.delete(entityForCreate_withoutConflict.getId())
    }

    def "Create an entity with an ID that does already exist (aka create_withConflict)"() {

        given: "The ID of the entity to create is already taken"
        setupDataForCreate_withConflict()
        crudService.create(entityForCreate_withConflict)

        when: "The entity is created"
        crudService.create(entityForCreate_withConflict)

        then: "An exception is thrown"
        thrown(MeetingConflictException)

        cleanup:
        crudService.delete(entityForCreate_withConflict.getId())
    }

    def "Delete an entity with an ID that does exist (aka delete_withExisting)"() {

        given: "The ID of the entity to delete is taken"
        setupDataForDelete_withExisting()
        crudService.create(entityForDelete_withExisting)

        when: "The entity is deleted"
        crudService.delete(entityForDelete_withExisting.getId())

        then: "The deletion is successful and no exception is thrown"
        noExceptionThrown()

        when: "The deleted entity is requested"
        crudService.findOne(entityForDelete_withExisting.getId())

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
