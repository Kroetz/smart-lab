package de.qaware.smartlabperson.service.controller;

import de.qaware.smartlabapi.service.constant.person.PersonManagementApiConstants;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.url.IBaseUrlDetector;
import de.qaware.smartlabperson.service.business.IPersonManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PersonManagementApiConstants.MAPPING_BASE)
@Slf4j
public class PersonManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IPerson> {

    private final IPersonManagementBusinessLogic personManagementBusinessLogic;

    public PersonManagementController(IPersonManagementBusinessLogic personManagementBusinessLogic) {
        this.personManagementBusinessLogic = personManagementBusinessLogic;
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<IPerson> findAll() {
        return this.personManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<IPerson> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId) {
        return responseFromOptional(this.personManagementBusinessLogic.findOne(PersonId.of(personId)));
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<IPerson>> findMultiple(@RequestParam(PersonManagementApiConstants.PARAMETER_NAME_PERSON_IDS) String[] personIds) {
        return responseFromOptionals(this.personManagementBusinessLogic.findMultiple(
                Arrays.stream(personIds)
                        .map(PersonId::of)
                        .collect(Collectors.toSet())));
    }

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<IPerson> create(@RequestBody IPerson person) {
        return ResponseEntity.ok(this.personManagementBusinessLogic.create(person));
    }

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<IPerson>> create(@RequestBody Set<IPerson> persons) {
        return ResponseEntity.ok(this.personManagementBusinessLogic.create(persons));
    }

    @Override
    @DeleteMapping(PersonManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId) {
        return this.personManagementBusinessLogic.delete(PersonId.of(personId)).toResponseEntity();
    }

    @RestController
    @RequestMapping(PersonManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(PersonManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
