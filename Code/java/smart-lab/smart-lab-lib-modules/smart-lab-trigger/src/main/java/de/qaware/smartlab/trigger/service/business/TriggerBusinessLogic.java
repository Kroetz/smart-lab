package de.qaware.smartlab.trigger.service.business;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.api.service.connector.job.IJobManagementService;
import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.assistance.assistances.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class TriggerBusinessLogic implements ITriggerBusinessLogic {

    private final IAssistanceService assistanceService;
    private final ILocationManagementService locationManagementService;
    private final IWorkgroupManagementService workgroupManagementService;
    private final ITriggerHandler asyncTriggerHandler;
    private final IJobManagementService jobManagementService;

    public TriggerBusinessLogic(
            IAssistanceService assistanceService,
            ILocationManagementService locationManagementService,
            IWorkgroupManagementService workgroupManagementService,
            ITriggerHandler asyncTriggerHandler,
            IJobManagementService jobManagementService) {
        this.assistanceService = assistanceService;
        this.locationManagementService = locationManagementService;
        this.workgroupManagementService = workgroupManagementService;
        this.asyncTriggerHandler = asyncTriggerHandler;
        this.jobManagementService = jobManagementService;
    }

    private IJobInfo triggerAssistances(
            IEvent event,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            @Nullable URL callbackUrl) {
        IJobInfo jobInfo = this.jobManagementService.recordNewJob(callbackUrl);
        this.asyncTriggerHandler.triggerAssistances(
                event,
                triggerReaction,
                jobInfo.getId());
        return jobInfo;
    }

    @Override
    public IJobInfo setUpEvent(IEvent event, @Nullable URL callbackUrl) {
        log.info("Processing trigger to set up event {}", event);
        IJobInfo jobInfo = triggerAssistances(
                event,
                (context, assistance) -> assistance.reactOnTriggerSetUpEvent(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to set up event {}", event);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo setUpCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return setUpEvent(this.locationManagementService.getCurrentEvent(locationId), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentEvent(ILocation location, @Nullable URL callbackUrl) {
        return setUpCurrentEventByLocationId(location.getId(), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return setUpEvent(this.workgroupManagementService.getCurrentEvent(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return setUpCurrentEventByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpEvent(IEvent event, @Nullable URL callbackUrl) {
        log.info("Processing trigger to clean up event {}", event);
        IJobInfo jobInfo = triggerAssistances(
                event,
                (context, assistance) -> assistance.reactOnTriggerCleanUpEvent(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to clean up event {}", event);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return cleanUpEvent(this.locationManagementService.getCurrentEvent(locationId), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpCurrentEvent(ILocation location, @Nullable URL callbackUrl) {
        return cleanUpCurrentEventByLocationId(location.getId(), callbackUrl);
    }

    // TODO: Introduce "Clean up LAST event" since there is no "current event" after a event has ended

    @Override
    public IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return cleanUpEvent(this.workgroupManagementService.getCurrentEvent(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return cleanUpCurrentEventByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo startEvent(IEvent event, @Nullable URL callbackUrl) {
        log.info("Processing trigger to start event {}", event);
        IJobInfo jobInfo = triggerAssistances(
                event,
                (context, assistance) -> assistance.reactOnTriggerStartEvent(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to start event {}", event);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo startCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return startEvent(this.locationManagementService.getCurrentEvent(locationId), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentEvent(ILocation location, @Nullable URL callbackUrl) {
        return startCurrentEventByLocationId(location.getId(), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return startEvent(this.workgroupManagementService.getCurrentEvent(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return startCurrentEventByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo stopEvent(IEvent event, @Nullable URL callbackUrl) {
        log.info("Processing trigger to stop event {}", event);
        IJobInfo jobInfo = triggerAssistances(
                event,
                (context, assistance) -> assistance.reactOnTriggerStopEvent(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to stop event {}", event);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo stopCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return stopEvent(this.locationManagementService.getCurrentEvent(locationId), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentEvent(ILocation location, @Nullable URL callbackUrl) {
        return stopCurrentEventByLocationId(location.getId(), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return stopEvent(this.workgroupManagementService.getCurrentEvent(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return stopCurrentEventByWorkgroupId(workgroup.getId(), callbackUrl);
    }
}
