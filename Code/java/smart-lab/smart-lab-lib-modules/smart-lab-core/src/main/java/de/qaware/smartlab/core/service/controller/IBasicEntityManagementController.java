package de.qaware.smartlab.core.service.controller;

import de.qaware.smartlab.core.data.generic.IDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IBasicEntityManagementController<DtoT extends IDto> {

    Set<DtoT> findAll();
    ResponseEntity<DtoT> findOne(String entityId);
    ResponseEntity<Set<DtoT>> findMultiple(String[] entityIds);
    ResponseEntity<DtoT> create(DtoT entity);
    ResponseEntity<Set<DtoT>> create(Set<DtoT> entities);
    ResponseEntity<Void> delete(String entityId);
}
