package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.workgroup.repository.IWorkgroupManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

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
    public Set<IWorkgroup> getWorkgroups() {
        return workgroupManagementRepository.findAll();
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup(String workgroupId) {
        return workgroupManagementRepository.findOne(workgroupId);
    }

    @Override
    public CreationResult createWorkgroup(IWorkgroup workgroup) {
        return workgroupManagementRepository.create(workgroup);
    }

    @Override
    public DeletionResult deleteWorkgroup(String workgroupId) {
        return workgroupManagementRepository.delete(workgroupId);
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId) {
        return workgroupManagementRepository.getMeetingsOfWorkgroup(workgroupId);
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String workgroupId) {
        return workgroupManagementRepository.getCurrentMeeting(workgroupId);
    }

    @Override
    public ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension) {
        return workgroupManagementRepository.extendCurrentMeeting(workgroupId, extension);
    }
}
