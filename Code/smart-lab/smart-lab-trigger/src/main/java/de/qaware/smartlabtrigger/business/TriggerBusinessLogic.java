package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.api.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcommons.data.assistance.IAssistance;
import de.qaware.smartlabcommons.data.assistance.ITriggerEffect;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.context.IContextFactory;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import de.qaware.smartlabcommons.exception.UnknownAssistanceException;
import de.qaware.smartlabcommons.result.CleanUpMeetingResult;
import de.qaware.smartlabcommons.result.SetUpMeetingResult;
import de.qaware.smartlabcommons.result.StartMeetingResult;
import de.qaware.smartlabcommons.result.StopMeetingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.BiFunction;

@Service
@Slf4j
public class TriggerBusinessLogic implements ITriggerBusinessLogic {

    private final IAssistanceService assistanceService;
    private final IRoomManagementService roomManagementService;
    private final IWorkgroupManagementService workgroupManagementService;
    private final IContextFactory contextFactory;
    private final IResolver<String, IAssistance> assistanceResolver;

    public TriggerBusinessLogic(
            IAssistanceService assistanceService,
            IRoomManagementService roomManagementService,
            IWorkgroupManagementService workgroupManagementService,
            IContextFactory contextFactory,
            IResolver<String, IAssistance> assistanceResolver) {
        this.assistanceService = assistanceService;
        this.roomManagementService = roomManagementService;
        this.workgroupManagementService = workgroupManagementService;
        this.contextFactory = contextFactory;
        this.assistanceResolver = assistanceResolver;
    }

    private void triggerAssistance(
            IMeeting meeting,
            BiFunction<IContext, IAssistance, ITriggerEffect> triggerEffectGetter) {
        Set<String> assistanceIds = meeting.getAssistanceIds();
        for(String assistanceId : assistanceIds) {
            log.info("Processing assistance with ID \"{}\"", assistanceId);
            IAssistance assistance = this.assistanceResolver.resolve(assistanceId).orElseThrow(UnknownAssistanceException::new);
            IContext context = this.contextFactory.ofMeetingAssistance(meeting, assistance);
            ITriggerEffect triggerEffect = triggerEffectGetter.apply(context, assistance);
            log.info("Calling assistance service for the effect of the trigger on assistance \"{}\" in room with ID \"{}\"",
                    assistance.getAssistanceId(),
                    context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new));
            triggerEffect.accept(this.assistanceService);
            log.info("Called assistance service for the effect of the trigger on assistance \"{}\" in room with ID \"{}\"",
                    assistance.getAssistanceId(),
                    context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new));
        }
        // TODO: Return type necessary?
    }

    @Override
    public SetUpMeetingResult setUpMeeting(IMeeting meeting) {
        log.info("Processing trigger to set up meeting {}", meeting.toString());
        triggerAssistance(meeting, (context, assistance) -> assistance.effectOfTriggerSetUpMeeting(context));
        log.info("Processed trigger to set up meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return SetUpMeetingResult.SUCCESS;
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeetingByRoomId(String roomId) {
        return setUpMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(IRoom room) {
        return setUpCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return setUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(IWorkgroup workgroup) {
        return setUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public CleanUpMeetingResult cleanUpMeeting(IMeeting meeting) {
        log.info("Processing trigger to clean up meeting {}", meeting.toString());
        triggerAssistance(meeting, (context, assistance) -> assistance.effectOfTriggerCleanUpMeeting(context));
        log.info("Processed trigger to clean up meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return CleanUpMeetingResult.SUCCESS;
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeetingByRoomId(String roomId) {
        return cleanUpMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(IRoom room) {
        return cleanUpCurrentMeetingByRoomId(room.getId());
    }

    // TODO: Introduce "Clean up LAST meeting" since there is no "current meeting" after a meeting has ended

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return cleanUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(IWorkgroup workgroup) {
        return cleanUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public StartMeetingResult startMeeting(IMeeting meeting) {
        log.info("Processing trigger to start meeting {}", meeting.toString());
        triggerAssistance(meeting, (context, assistance) -> assistance.effectOfTriggerStartMeeting(context));
        log.info("Processed trigger to start meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return StartMeetingResult.SUCCESS;
    }

    @Override
    public StartMeetingResult startCurrentMeetingByRoomId(String roomId){
        return startMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public StartMeetingResult startCurrentMeeting(IRoom room) {
        return startCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public StartMeetingResult startCurrentMeetingByWorkgroupId(String workgroupId) {
        return startMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public StartMeetingResult startCurrentMeeting(IWorkgroup workgroup) {
        return startCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public StopMeetingResult stopMeeting(IMeeting meeting) {
        log.info("Processing trigger to stop meeting {}", meeting.toString());
        triggerAssistance(meeting, (context, assistance) -> assistance.effectOfTriggerStopMeeting(context));
        log.info("Processed trigger to stop meeting {}", meeting.toString());
        // TODO: Return type necessary?
        return StopMeetingResult.SUCCESS;
    }

    @Override
    public StopMeetingResult stopCurrentMeetingByRoomId(String roomId) {
        return stopMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(IRoom room) {
        return stopCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public StopMeetingResult stopCurrentMeetingByWorkgroupId(String workgroupId) {
        return stopMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(IWorkgroup workgroup) {
        return stopCurrentMeetingByWorkgroupId(workgroup.getId());
    }
}
