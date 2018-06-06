package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabapi.service.room.IRoomManagementService;
import de.qaware.smartlabapi.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabassistance.assistance.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.result.CleanUpMeetingResult;
import de.qaware.smartlabcore.result.SetUpMeetingResult;
import de.qaware.smartlabcore.result.StartMeetingResult;
import de.qaware.smartlabcore.result.StopMeetingResult;
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
    private final IJobInfoBookKeeper jobInfoBookKeeper;

    public TriggerBusinessLogic(
            IAssistanceService assistanceService,
            IRoomManagementService roomManagementService,
            IWorkgroupManagementService workgroupManagementService,
            ITriggerHandler asyncTriggerHandler,
            IJobInfoBookKeeper jobInfoBookKeeper) {
        this.assistanceService = assistanceService;
        this.roomManagementService = roomManagementService;
        this.workgroupManagementService = workgroupManagementService;
        this.asyncTriggerHandler = asyncTriggerHandler;
        this.jobInfoBookKeeper = jobInfoBookKeeper;
    }

    private void triggerAssistances(
            IMeeting meeting,
            BiConsumer<IContext, IAssistanceTriggerable> triggerReactionGetter,
            @Nullable URL callbackUrl) {
        Long jobId = this.jobInfoBookKeeper.recordNewJob(JobInfo.of(callbackUrl)).getId();
        this.asyncTriggerHandler.triggerAssistances(meeting, triggerReactionGetter, jobId);
    }

    @Override
    public SetUpMeetingResult setUpMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to set up meeting {}", meeting.toString());
        triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerSetUpMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to set up meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return SetUpMeetingResult.SUCCESS;
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return setUpMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return setUpCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return setUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return setUpCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public CleanUpMeetingResult cleanUpMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to clean up meeting {}", meeting.toString());
        triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerCleanUpMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to clean up meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return CleanUpMeetingResult.SUCCESS;
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return cleanUpMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return cleanUpCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    // TODO: Introduce "Clean up LAST meeting" since there is no "current meeting" after a meeting has ended

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return cleanUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return cleanUpCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public StartMeetingResult startMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to start meeting {}", meeting.toString());
        triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerStartMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to start meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return StartMeetingResult.SUCCESS;
    }

    @Override
    public StartMeetingResult startCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return startMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public StartMeetingResult startCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return startCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    @Override
    public StartMeetingResult startCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return startMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public StartMeetingResult startCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return startCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }

    @Override
    public StopMeetingResult stopMeeting(IMeeting meeting, @Nullable URL callbackUrl) {
        log.info("Processing trigger to stop meeting {}", meeting.toString());
        triggerAssistances(
                meeting,
                (context, assistance) -> assistance.reactOnTriggerStopMeeting(this.assistanceService, context),
                callbackUrl);
        log.info("Processed trigger to stop meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return StopMeetingResult.SUCCESS;
    }

    @Override
    public StopMeetingResult stopCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl) {
        return stopMeeting(roomManagementService.getCurrentMeeting(roomId), callbackUrl);
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(IRoom room, @Nullable URL callbackUrl) {
        return stopCurrentMeetingByRoomId(room.getId(), callbackUrl);
    }

    @Override
    public StopMeetingResult stopCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl) {
        return stopMeeting(workgroupManagementService.getCurrentMeeting(workgroupId), callbackUrl);
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl) {
        return stopCurrentMeetingByWorkgroupId(workgroup.getId(), callbackUrl);
    }
}
