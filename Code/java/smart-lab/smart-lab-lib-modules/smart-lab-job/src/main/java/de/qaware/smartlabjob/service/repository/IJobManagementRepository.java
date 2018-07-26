package de.qaware.smartlabjob.service.repository;

import de.qaware.smartlabcore.data.job.JobInfo;
import org.springframework.data.repository.CrudRepository;

public interface IJobManagementRepository extends CrudRepository<JobInfo, Long> { }