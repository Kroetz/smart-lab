package de.qaware.smartlab.core.service.repository;

import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import de.qaware.smartlab.core.exception.data.DataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Set;

@Repository
@Slf4j
public abstract class AbstractBasicEntityManagementRepository<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementRepository<EntityT, IdentifierT> {

    protected final Set<EntityT> initialData;

    protected AbstractBasicEntityManagementRepository(Set<EntityT> initialData) {
        this.initialData = initialData;
    }

    @PostConstruct
    private void populateWithInitialData() {
        try {
            create(this.initialData);
        }
        catch(DataException e) {
            log.error("Could not populate repository with initial data", e);
        }
    }

    protected boolean exists(IdentifierT entityId) {
        return findOne(entityId).isPresent();
    }
}
