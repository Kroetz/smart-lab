package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.generic.service.AbstractEntityManagementService;
import de.qaware.smartlabcore.workgroup.repository.IWorkgroupManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class WorkgroupManagementService extends AbstractEntityManagementService<IWorkgroup> implements IWorkgroupManagementService {

    private final IWorkgroupManagementRepository workgroupManagementRepository;

    public WorkgroupManagementService(
            IWorkgroupManagementRepository workgroupManagementRepository) {
        super(workgroupManagementRepository);
        this.workgroupManagementRepository = workgroupManagementRepository;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsOfWorkgroup(String workgroupId) {

        return workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> Optional.of(workgroupManagementRepository.getMeetingsOfWorkgroup(workgroup)))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String workgroupId) {
        return workgroupManagementRepository.findOne(workgroupId)
                .map(workgroupManagementRepository::getCurrentMeeting)
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension) {
        return workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> workgroupManagementRepository.extendCurrentMeeting(workgroup, extension))
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
