package de.qaware.smartlabcore.generic

import de.qaware.smartlabcommons.api.client.generic.ICrudApiClient
import de.qaware.smartlabcommons.data.IEntity
import feign.FeignException
import org.springframework.http.HttpStatus
import spock.lang.Specification

abstract class CrudApiIntegrationTest<T extends IEntity> extends Specification {

    protected ICrudApiClient<T> crudApiClient
    protected Set<T> entitiesForFindAll_withExisting
    protected T entityForFindOne_withExisting
    protected String entityIdForFindOne_withoutExisting
    protected T entityForCreate_withoutConflict
    protected T entityForCreate_withConflict
    protected T entityForDelete_withExisting
    protected String entityIdForDelete_withoutExisting

    def abstract setupDataForFindAll_withExisting()
    def abstract setupDataForFindOne_withExisting()
    def abstract setupDataForFindOne_withoutExisting()
    def abstract setupDataForCreate_withoutConflict()
    def abstract setupDataForCreate_withConflict()
    def abstract setupDataForDelete_withExisting()
    def abstract setupDataForDelete_withoutExisting()

    def "Get a set of all existing entities when there are entities (aka findAll_withExisting)"() {

        given: "There are entities available in the repository"
        setupDataForFindAll_withExisting()
        for (def entity : entitiesForFindAll_withExisting) {
            crudApiClient.create(entity)
        }

        when: "The set of entities is requested"
        Set<T> response = crudApiClient.findAll()

        then: "The returned set equals the one that was used to populate the repository"
        response == entitiesForFindAll_withExisting

        cleanup:
        for (def entity : entitiesForFindAll_withExisting) {
            crudApiClient.delete(entity.getId())
        }
    }

    def "Get a set of all existing entities when there are no entities (aka findAll_withoutExisting)"() {

        given: "There are no entities available in the repository"
        def entities = new HashSet<IEntity>()

        when: "The set of entities is requested"
        Set<T> response = crudApiClient.findAll()

        then: "The returned set is empty"
        response == entities
    }

    def "Get a specific entity when the entity exists (aka findOne_withExisting)"() {

        given: "An entity with the requested entity ID does exist"
        setupDataForFindOne_withExisting()
        crudApiClient.create(entityForFindOne_withExisting)

        when: "The entity is requested"
        def response = crudApiClient.findOne(entityForFindOne_withExisting.getId())

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The received entity equals the one that was initially put into the repository"
        response.getBody() == entityForFindOne_withExisting

        cleanup:
        crudApiClient.delete(entityForFindOne_withExisting.getId())
    }

    def "Get a specific entity when the entity does not exist (aka findOne_withoutExisting)"() {

        given: "An entity with the requested entity ID does not exist"
        setupDataForFindOne_withoutExisting()

        when: "The entity is requested"
        crudApiClient.findOne(entityIdForFindOne_withoutExisting)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }

    def "Create an entity with an ID that does not yet exist (aka create_withoutConflict)"() {

        given: "The ID of the entity to create is not yet taken"
        setupDataForCreate_withoutConflict()

        when: "The entity is created"
        def creationResponse = crudApiClient.create(entityForCreate_withoutConflict)

        then: "The returned HTTP status code is 200 (OK)"
        creationResponse.statusCode == HttpStatus.OK

        when: "The created entity is requested"
        def findResponse = crudApiClient.findOne(entityForCreate_withoutConflict.getId())

        then: "The entity from the response equals the one that was initially passed during the creation"
        findResponse.statusCodeValue == HttpStatus.OK.value()
        entityForCreate_withoutConflict == findResponse.getBody()

        cleanup:
        crudApiClient.delete(entityForCreate_withoutConflict.getId())
    }

    def "Create an entity with an ID that does already exist (aka create_withConflict)"() {

        given: "The ID of the entity to create is already taken"
        setupDataForCreate_withConflict()
        crudApiClient.create(entityForCreate_withConflict)

        when: "The entity is created"
        crudApiClient.create(entityForCreate_withConflict)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        crudApiClient.delete(entityForCreate_withConflict.getId())
    }

    def "Delete an entity with an ID that does exist (aka delete_withExisting)"() {

        given: "The ID of the entity to delete is taken"
        setupDataForDelete_withExisting()
        crudApiClient.create(entityForDelete_withExisting)

        when: "The entity is deleted"
        def deletionResponse = crudApiClient.delete(entityForDelete_withExisting.getId())

        then: "The returned HTTP status code is 200 (OK)"
        deletionResponse.statusCode == HttpStatus.OK

        when: "The deleted entity is requested"
        crudApiClient.findOne(entityForDelete_withExisting.getId())

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }

    def "Delete an entity with an ID that does not exist (aka delete_withoutExisting)"() {

        given: "The ID of the entity to delete is not taken"
        setupDataForDelete_withoutExisting()

        when: "The entity is deleted"
        crudApiClient.delete(entityIdForDelete_withoutExisting)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }
}
