package de.qaware.smartlabcore.person.controller;

import de.qaware.smartlabcommons.api.PersonManagementApiConstants;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.person.service.IPersonManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(PersonManagementApiConstants.MAPPING_BASE)
@Slf4j
public class PersonManagementController extends AbstractSmartLabController {

    private final IPersonManagementService personManagementService;

    public PersonManagementController(IPersonManagementService personManagementService) {
        this.personManagementService = personManagementService;
    }

    @GetMapping(PersonManagementApiConstants.MAPPING_GET_PERSONS)
    public Set<IPerson> getPersons() {
        return personManagementService.getPersons();
    }

    @GetMapping(PersonManagementApiConstants.MAPPING_GET_PERSON)
    public ResponseEntity<IPerson> getPerson(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId) {
        return responseFromOptional(personManagementService.getPerson(personId));
    }

    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_PERSON, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(@RequestBody IPerson person) {
        return personManagementService.createPerson(person).toResponseEntity();
    }

    @DeleteMapping(PersonManagementApiConstants.MAPPING_DELETE_PERSON)
    public ResponseEntity<Void> deletePerson(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId) {
        return personManagementService.deletePerson(personId).toResponseEntity();
    }
}
