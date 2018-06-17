package de.qaware.smartlabworkgroup.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.generic.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabworkgroup.repository.IWorkgroupManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class WorkgroupManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IWorkgroup, WorkgroupId> implements IWorkgroupManagementBusinessLogic {

    private final IWorkgroupManagementRepository workgroupManagementRepository;

    public WorkgroupManagementBusinessLogic(
            IWorkgroupManagementRepository workgroupManagementRepository) {
        super(workgroupManagementRepository);
        this.workgroupManagementRepository = workgroupManagementRepository;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsOfWorkgroup(WorkgroupId workgroupId) {

        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> Optional.of(this.workgroupManagementRepository.getMeetingsOfWorkgroup(workgroup)))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(WorkgroupId workgroupId) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(this.workgroupManagementRepository::getCurrentMeeting)
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(WorkgroupId workgroupId, Duration extension) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> this.workgroupManagementRepository.extendCurrentMeeting(workgroup, extension))
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
