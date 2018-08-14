package de.qaware.smartlab.job.service.repository;

import de.qaware.smartlab.job.data.JobInfo;
import org.springframework.data.repository.CrudRepository;

public interface IJobManagementRepository extends CrudRepository<JobInfo, Long> { }
