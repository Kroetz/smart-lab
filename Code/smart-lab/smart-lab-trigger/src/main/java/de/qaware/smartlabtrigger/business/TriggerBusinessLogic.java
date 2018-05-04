package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.api.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcommons.data.assistance.IAssistance;
import de.qaware.smartlabcommons.data.assistance.IAssistanceResolver;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.context.IContextFactory;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.result.CleanUpMeetingResult;
import de.qaware.smartlabcommons.result.SetUpMeetingResult;
import de.qaware.smartlabcommons.result.StartMeetingResult;
import de.qaware.smartlabcommons.result.StopMeetingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Consumer;

@Service
@Slf4j
public class TriggerBusinessLogic implements ITriggerBusinessLogic {

    private final IRoomManagementService roomManagementService;
    private final IWorkgroupManagementService workgroupManagementService;
    private final IContextFactory contextFactory;
    private final IAssistanceResolver assistanceResolver;

    public TriggerBusinessLogic(
            IRoomManagementService roomManagementService,
            IWorkgroupManagementService workgroupManagementService,
            IContextFactory contextFactory,
            IAssistanceResolver assistanceResolver) {
        this.roomManagementService = roomManagementService;
        this.workgroupManagementService = workgroupManagementService;
        this.contextFactory = contextFactory;
        this.assistanceResolver = assistanceResolver;
    }

    private void triggerAssistance(IContext context, Consumer<IAssistance> triggerFunction) {
        // TODO: Exception?.
        Set<String> assistanceIds = context.getMeeting().map(IMeeting::getAssistanceIds).orElseThrow(IllegalStateException::new);
        for(String assistanceId : assistanceIds) {
            log.info("Processing assistance with ID \"{}\"", assistanceId);
            // TODO: Simpler with Java 9 "ifPresentOrElse" (see https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
            this.assistanceResolver.resolveAssistanceId(assistanceId).ifPresent(triggerFunction); // Inhalt der Methode muss spezifisch sein für Assistance (Mapping auf Assistance-API Feign-Call und übergeben des Assistance Namens als Param)
        }
        // TODO: Return type
    }

    @Override
    public SetUpMeetingResult setUpMeeting(IMeeting meeting) {
        log.info("Processing call to set up the meeting with ID \"{}\"", meeting.getId());
        IContext context = this.contextFactory.ofMeeting(meeting);
        triggerAssistance(context, assistance -> assistance.triggerSetUpMeeting(context));
        log.info("Room (ID: \"{}\") was set up for meeting with ID \"{}\"",
                context.getRoom().map(IRoom::getId).orElse("Default room ID"),
                meeting.getId());
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
        IContext context = this.contextFactory.ofMeeting(meeting);
        triggerAssistance(context, assistance -> assistance.triggerCleanUpMeeting(context));
        log.info("Clean up room \"{}\" (id: {}) for meeting (id: {}) of workgroup \"{}\" (id: {})",
                context.getRoom().map(IRoom::getName).orElse("Default room name"),
                context.getRoom().map(IRoom::getId).orElse("Default room ID"),
                meeting.getId(),
                context.getWorkgroup().map(IWorkgroup::getName).orElse("Default workgroup name"),
                context.getWorkgroup().map(IWorkgroup::getId).orElse("Default workgroup ID"));
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
        IContext context = this.contextFactory.ofMeeting(meeting);
        triggerAssistance(context, assistance -> assistance.triggerStartMeeting(context));
        log.info("Started meeting (id: {}) of workgroup \"{}\" (id: {}) in room \"{}\" (id: {})",
                context.getRoom().map(IRoom::getName).orElse("Default room name"),
                context.getRoom().map(IRoom::getId).orElse("Default room ID"),
                meeting.getId(),
                context.getWorkgroup().map(IWorkgroup::getName).orElse("Default workgroup name"),
                context.getWorkgroup().map(IWorkgroup::getId).orElse("Default workgroup ID"));
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
        IContext context = this.contextFactory.ofMeeting(meeting);
        triggerAssistance(context, assistance -> assistance.triggerStopMeeting(context));
        log.info("Stopped meeting (id: {}) of workgroup \"{}\" (id: {}) in room \"{}\" (id: {})",
                context.getRoom().map(IRoom::getName).orElse("Default room name"),
                context.getRoom().map(IRoom::getId).orElse("Default room ID"),
                meeting.getId(),
                context.getWorkgroup().map(IWorkgroup::getName).orElse("Default workgroup name"),
                context.getWorkgroup().map(IWorkgroup::getId).orElse("Default workgroup ID"));
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
