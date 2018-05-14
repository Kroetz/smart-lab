package de.qaware.smartlabcore.generic.controller;

import de.qaware.smartlabcommons.data.generic.IEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

//TODO: Same content as IEntityManagementApiClient --> Eliminate code duplicates via parent interface
public interface IEntityManagementController<T extends IEntity> {

    Set<T> findAll();
    ResponseEntity<T> findOne(String entityId);
    ResponseEntity<Set<T>> findMultiple(String[] entityIds);
    ResponseEntity<Void> create(T entity);
    ResponseEntity<Void> delete(String entityId);
}