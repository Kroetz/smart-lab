package de.qaware.smartlabperson.service.controller;

import de.qaware.smartlabapi.service.constant.person.PersonManagementApiConstants;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.person.dto.PersonDto;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import de.qaware.smartlabperson.service.business.IPersonManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping(PersonManagementApiConstants.MAPPING_BASE)
@Slf4j
public class PersonManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IPerson, PersonDto> {

    private final IPersonManagementBusinessLogic personManagementBusinessLogic;
    private final IDtoConverter<IPerson, PersonDto> personConverter;

    public PersonManagementController(
            IPersonManagementBusinessLogic personManagementBusinessLogic,
            IDtoConverter<IPerson, PersonDto> personConverter) {
        this.personManagementBusinessLogic = personManagementBusinessLogic;
        this.personConverter = personConverter;
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<PersonDto> findAll() {
        return this.personManagementBusinessLogic.findAll().stream()
                .map(this.personConverter::toDto)
                .collect(toSet());
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<PersonDto> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId) {
        return responseFromEntityOptional(
                this.personManagementBusinessLogic.findOne(PersonId.of(personId)),
                this.personConverter);
    }

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<PersonDto>> findMultiple(@RequestParam(PersonManagementApiConstants.PARAMETER_NAME_PERSON_IDS) String[] personIds) {
        return responseFromEntityOptionals(
                this.personManagementBusinessLogic.findMultiple(stream(personIds)
                        .map(PersonId::of)
                        .collect(toSet())),
                this.personConverter);
    }

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto person) {
        IPerson personToCreate = this.personConverter.toEntity(person);
        PersonDto createdPerson = this.personConverter.toDto(
                this.personManagementBusinessLogic.create(personToCreate));
        return ResponseEntity.ok(createdPerson);
    }

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<PersonDto>> create(@RequestBody Set<PersonDto> persons) {
        return ResponseEntity.ok(this.personManagementBusinessLogic.create(persons
                .stream()
                .map(this.personConverter::toEntity)
                .collect(toSet()))
                .stream()
                .map(this.personConverter::toDto)
                .collect(toSet()));
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
