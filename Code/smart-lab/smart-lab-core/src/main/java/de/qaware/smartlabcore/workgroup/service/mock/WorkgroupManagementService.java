package de.qaware.smartlabcore.workgroup.service.mock;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.meeting.service.IMeetingManagementService;
import de.qaware.smartlabcore.workgroup.service.IWorkgroupManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mock")
@Slf4j
public class WorkgroupManagementService implements IWorkgroupManagementService {

    @Autowired
    @Qualifier("mock")
    private IMeetingManagementService meetingService;

    private final IWorkgroupConfigProviderMock workgroupConfigProvider;

    @Autowired
    public WorkgroupManagementService(
            @Qualifier("mock") IWorkgroupConfigProviderMock workgroupConfigProvider) {
        this.workgroupConfigProvider = workgroupConfigProvider;
    }

    @Override
    public List<Workgroup> getWorkgroups() {
        return workgroupConfigProvider.getWorkgroups();
    }

    @Override
    public Optional<Workgroup> getWorkgroup(long workgroupId) {
        return workgroupConfigProvider.getWorkgroup(workgroupId);
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(long workgroupId) {
        return getWorkgroup(workgroupId)
                .map(meetingService::getCurrentMeeting)
                .orElse(Optional.empty());
    }

    @Override
    public boolean createWorkgroup(Workgroup workgroup) {
        return workgroupConfigProvider.createWorkgroup(workgroup);
    }

    @Override
    public void deleteWorkgroup(long workgroupId) {
        workgroupConfigProvider.deleteWorkgroup(workgroupId);
    }

    @Override
    public boolean extendCurrentMeeting(long workgroupId, long extensionInMinutes) {
        return getWorkgroup(workgroupId)
                .map(workgroup -> {
                    return meetingService.extendCurrentMeeting(workgroup, Duration.ofMinutes(extensionInMinutes));
                })
                .orElse(false);
    }
}
