package de.qaware.smartlab.api.service.client.generic;

import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.generic.IEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IBasicEntityManagementApiClient<EntityT extends IEntity, DtoT extends IDto> {

    Set<DtoT> findAll();
    ResponseEntity<DtoT> findOne(String entityId);
    ResponseEntity<Set<DtoT>> findMultiple(String[] entityIds);
    ResponseEntity<DtoT> create(DtoT entity);
    ResponseEntity<Set<DtoT>> create(Set<DtoT> entities);
    ResponseEntity<Void> delete(String entityId);
}
