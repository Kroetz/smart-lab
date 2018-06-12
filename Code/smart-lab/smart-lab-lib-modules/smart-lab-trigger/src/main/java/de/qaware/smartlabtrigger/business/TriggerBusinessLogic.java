package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabapi.service.job.IJobManagementService;
import de.qaware.smartlabapi.service.room.IRoomManagementService;
import de.qaware.smartlabapi.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabassistance.assistance.triggerable.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class TriggerBusinessLogic implements ITriggerBusinessLogic {

    private final IAssistanceService assistanceService;
    private final IRoomManagementService roomManagementService;
    private final IWorkgroupManagementService workgroupManagementService;
    private final ITriggerHandler asyncTriggerHandler;
    private final IJobManagementService jobManagementService;

    public TriggerBusinessLogic(
            IAssistanceService assistanceService,
            IRoomManagementService roomManagementService,
            IWorkgroupManagementService workgroupManagementService,
            ITriggerHandler asyncTriggerHandler,
            IJobManagementService jobManagementService) {
        this.assistanceService = assistanceService;
        this.roomManagementService = roomManagementService;
        this.workgroupManagementService = workgroupManagementService;
        this.asyncTriggerHandler = asyncTriggerHandler;
        this.jobManagementService = jobManagementService;
    }

    private IJobInfo triggerAssistances(
            IMeeting meeting,
            BiConsumer<IContext, IAssistanceTriggerable> triggerReactionGetter,
            @Nullable URL callbackUrl) {
        IJobInfo jobInfo = this.jobManagementService.recordNewJob(callbackUrl);
        this.asyncTriggerHandler.triggerAssistances(meeting, triggerReactionGetter, jobInfo.getId());
        return jobInfo;
    }

    @Override
    public IJobInfo setUpMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to set up meeting {}", meeting.toString());
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerSetUpMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to set up meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo setUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return setUpMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return setUpCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return setUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo setUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return setUpCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to clean up meeting {}", meeting.toString());
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerCleanUpMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to clean up meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return cleanUpMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return cleanUpCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    // TODO: Introduce "Clean up LAST meeting" since there is no "current meeting" after a meeting has ended

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return cleanUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo cleanUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return cleanUpCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo startMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to start meeting {}", meeting.toString());
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerStartMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to start meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return startMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return startCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return startMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo startCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return startCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public IJobInfo stopMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to stop meeting {}", meeting.toString());
        IJobInfo jobInfo = triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerStopMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to stop meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return jobInfo;
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return stopMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return stopCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return stopMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public IJobInfo stopCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return stopCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }
}
