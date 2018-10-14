package de.qaware.smartlab.job.service.repository;

import de.qaware.smartlab.job.data.JobInfo;
import org.springframework.data.repository.CrudRepository;

// TODO: Currently jobs not persisted but saved in an in-memory database that is addressed by this interface. A more resilient way would be to persist the jobs.
public interface IJobManagementRepository extends CrudRepository<JobInfo, Long> { }
