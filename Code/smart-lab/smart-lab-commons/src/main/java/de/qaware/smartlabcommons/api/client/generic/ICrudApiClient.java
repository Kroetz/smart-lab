package de.qaware.smartlabcommons.api.client.generic;

import de.qaware.smartlabcommons.data.IEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICrudApiClient<T extends IEntity> {

    List<T> findAll();

    ResponseEntity<T> findOne(String entityId);

    ResponseEntity<Void> create(T entity);

    ResponseEntity<Void> delete(String entityId);
}
