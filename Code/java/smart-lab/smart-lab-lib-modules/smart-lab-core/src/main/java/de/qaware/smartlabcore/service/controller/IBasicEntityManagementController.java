package de.qaware.smartlabcore.service.controller;

import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.generic.IEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

//TODO: Same content as IEntityManagementApiClient --> Eliminate code duplicates via parent interface
public interface IBasicEntityManagementController<EntityT extends IEntity, DtoT extends IDto> {

    Set<DtoT> findAll();
    ResponseEntity<DtoT> findOne(String entityId);
    ResponseEntity<Set<DtoT>> findMultiple(String[] entityIds);
    ResponseEntity<DtoT> create(DtoT entity);
    ResponseEntity<Set<DtoT>> create(Set<DtoT> entities);
    ResponseEntity<Void> delete(String entityId);
}
