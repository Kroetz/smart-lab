package de.qaware.smartlabperson.controller;

import de.qaware.smartlabcommons.api.PersonManagementApiConstants;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.generic.controller.IEntityManagementController;
import de.qaware.smartlabperson.business.IPersonManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(PersonManagementApiConstants.MAPPING_BASE)
@Slf4j
public class PersonManagementController extends AbstractSmartLabController implements IEntityManagementController<IPerson> {

    private final IPersonManagementBusinessLogic personManagementBusinessLogic;

    public PersonManagementController(IPersonManagementBusinessLogic personManagementBusinessLogic) {
        this.personManagementBusinessLogic = personManagementBusinessLogic;
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IPerson> findAll() {
        return personManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IPerson> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId) {
        return responseFromOptional(personManagementBusinessLogic.findOne(personId));
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_MULTIPLE)
    public ResponseEntity<Set<IPerson>> findMultiple(@RequestParam(PersonManagementApiConstants.PARAMETER_NAME_PERSON_IDS) String[] personIds) {
        return responseFromOptionals(personManagementBusinessLogic.findMultiple(new HashSet<>(Arrays.asList(personIds))));
    }

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IPerson person) {
        return personManagementBusinessLogic.create(person).toResponseEntity();
    }

    @Override
    @DeleteMapping(PersonManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId) {
        return personManagementBusinessLogic.delete(personId).toResponseEntity();
    }
}
