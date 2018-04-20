package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.workgroup.repository.IWorkgroupManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WorkgroupManagementService implements IWorkgroupManagementService {

    private final IWorkgroupManagementRepository workgroupManagementRepository;
    private final IMeetingManagementApiClient meetingManagementApiClient;

    public WorkgroupManagementService(
            IWorkgroupManagementRepository workgroupManagementRepository,
            IMeetingManagementApiClient meetingManagementApiClient) {
        this.workgroupManagementRepository = workgroupManagementRepository;
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public List<IWorkgroup> getWorkgroups() {
        return workgroupManagementRepository.getWorkgroups();
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup(String workgroupId) {
        return workgroupManagementRepository.getWorkgroup(workgroupId);
    }

    @Override
    public boolean createWorkgroup(IWorkgroup workgroup) {
        return workgroupManagementRepository.createWorkgroup(workgroup);
    }

    @Override
    public boolean deleteWorkgroup(String workgroupId) {
        return workgroupManagementRepository.deleteWorkgroup(workgroupId);
    }

    @Override
    public List<IMeeting> getMeetingsOfWorkgroup(String workgroupId) {
        return workgroupManagementRepository.getMeetingsOfWorkgroup(workgroupId);
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String workgroupId) {
        return workgroupManagementRepository.getCurrentMeeting(workgroupId);
    }

    @Override
    public boolean extendCurrentMeeting(String workgroupId, Duration extension) {
        return workgroupManagementRepository.extendCurrentMeeting(workgroupId, extension);
    }
}
