package de.qaware.smartlabcore.service.repository;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.generic.IIdentifier;
import de.qaware.smartlabcore.exception.EntityCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public abstract class AbstractBasicEntityManagementRepository<EntityT extends IEntity<IdentifierT>, IdentifierT extends IIdentifier> implements IBasicEntityManagementRepository<EntityT, IdentifierT> {

    protected final Set<EntityT> initialData;

    public AbstractBasicEntityManagementRepository(Set<? extends EntityT> initialData) {
        this.initialData = new HashSet<>();
        this.initialData.addAll(initialData);
    }

    @PostConstruct
    private void populateWithInitialData() {
        try {
            create(this.initialData);
        }
        catch(EntityCreationException e) {
            log.error("Could not populate repository with initial data", e);
        }
    }

    protected boolean exists(IdentifierT entityId) {
        return findOne(entityId).isPresent();
    }
}
