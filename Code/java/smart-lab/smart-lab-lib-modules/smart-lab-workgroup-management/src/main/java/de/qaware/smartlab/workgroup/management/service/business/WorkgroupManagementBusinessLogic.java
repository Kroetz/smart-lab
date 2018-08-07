package de.qaware.smartlab.workgroup.management.service.business;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.workgroup.management.service.repository.IWorkgroupManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class WorkgroupManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IWorkgroup, WorkgroupId> implements IWorkgroupManagementBusinessLogic {

    private final IWorkgroupManagementRepository workgroupManagementRepository;
    private final IEventManagementService eventManagementService;

    public WorkgroupManagementBusinessLogic(
            IWorkgroupManagementRepository workgroupManagementRepository,
            IEventManagementService eventManagementService) {
        super(workgroupManagementRepository);
        this.workgroupManagementRepository = workgroupManagementRepository;
        this.eventManagementService = eventManagementService;
    }

    @Override
    public Optional<Set<IEvent>> getEventsOfWorkgroup(WorkgroupId workgroupId) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> Optional.of(this.eventManagementService.findAll(workgroup.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IEvent> getCurrentEvent(WorkgroupId workgroupId) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> Optional.of(this.eventManagementService.findCurrent(workgroup.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentEvent(WorkgroupId workgroupId, Duration extension) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> {
                    try {
                        return getCurrentEvent(workgroup.getId())
                                .map(event -> {
                                    this.eventManagementService.extendEvent(event.getId(), extension);
                                    return ExtensionResult.SUCCESS;})
                                .orElse(ExtensionResult.NOT_FOUND);
                    }
                    catch(Exception e) {
                        return ExtensionResult.fromException(e);
                    }
                })
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
