package de.qaware.smartlabtrigger.service.business;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.api.service.connector.job.IJobManagementService;
import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.assistance.assistances.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.meeting.IMeeting;
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
            IMeeting meeting,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            @Nullable URL callbackUrl) {
        IJobInfo jobInfo = this.jobManagementService.recordNewJob(callbackUrl);
        this.asyncTriggerHandler.triggerAssistances(
                meeting,
                triggerReaction,
                jobInfo.getId());
        return jobInfo;
    }

    @Override
    public IJobInfo setUpMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to set up meeting {}", meeting);
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerSetUpMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to set up meeting {}", meeting);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return setUpMeeting(this.locationManagementService.getCurrentMeeting(locationId), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentMeeting(ILocation location, @Nullable URL callbackUrl) {
        return setUpCurrentMeetingByLocationId(location.getId(), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return setUpMeeting(this.workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return setUpCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to clean up meeting {}", meeting);
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerCleanUpMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to clean up meeting {}", meeting);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return cleanUpMeeting(this.locationManagementService.getCurrentMeeting(locationId), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpCurrentMeeting(ILocation location, @Nullable URL callbackUrl) {
        return cleanUpCurrentMeetingByLocationId(location.getId(), callbackUrl);
    }

    // TODO: Introduce "Clean up LAST meeting" since there is no "current meeting" after a meeting has ended

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return cleanUpMeeting(this.workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return cleanUpCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo startMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to start meeting {}", meeting);
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerStartMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to start meeting {}", meeting);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo startCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return startMeeting(this.locationManagementService.getCurrentMeeting(locationId), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentMeeting(ILocation location, @Nullable URL callbackUrl) {
        return startCurrentMeetingByLocationId(location.getId(), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return startMeeting(this.workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return startCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo stopMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to stop meeting {}", meeting);
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerStopMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to stop meeting {}", meeting);
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo stopCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl) {
        return stopMeeting(this.locationManagementService.getCurrentMeeting(locationId), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentMeeting(ILocation location, @Nullable URL callbackUrl) {
        return stopCurrentMeetingByLocationId(location.getId(), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl) {
        return stopMeeting(this.workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return stopCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }
}
