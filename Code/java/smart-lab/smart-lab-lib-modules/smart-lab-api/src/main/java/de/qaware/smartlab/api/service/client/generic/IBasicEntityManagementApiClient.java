package de.qaware.smartlab.api.service.client.generic;

import de.qaware.smartlab.core.data.generic.IDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IBasicEntityManagementApiClient<DtoT extends IDto> {

    Set<DtoT> findAll();
    ResponseEntity<DtoT> findOne(String entityId);
    ResponseEntity<Set<DtoT>> findMultiple(String[] entityIds);
    ResponseEntity<DtoT> create(DtoT entity);
    ResponseEntity<Set<DtoT>> create(Set<DtoT> entities);
    ResponseEntity<Void> delete(String entityId);
}
