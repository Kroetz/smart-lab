package de.qaware.smartlabapi.client.generic;

import de.qaware.smartlabcore.data.generic.IEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IBasicEntityManagementApiClient<EntityT extends IEntity> {

    Set<EntityT> findAll();
    ResponseEntity<EntityT> findOne(String entityId);
    ResponseEntity<Set<EntityT>> findMultiple(String[] entityIds);
    ResponseEntity<EntityT> create(EntityT entity);
    ResponseEntity<Set<EntityT>> create(Set<EntityT> entities);
    ResponseEntity<Void> delete(String entityId);
}
