package de.qaware.smartlabapi.client.generic;

import de.qaware.smartlabcore.data.generic.IEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IEntityManagementApiClient<T extends IEntity> {

    Set<T> findAll();
    ResponseEntity<T> findOne(String entityId);
    ResponseEntity<Set<T>> findMultiple(String[] entityIds);
    ResponseEntity<Void> create(T entity);
    ResponseEntity<Void> delete(String entityId);
}
