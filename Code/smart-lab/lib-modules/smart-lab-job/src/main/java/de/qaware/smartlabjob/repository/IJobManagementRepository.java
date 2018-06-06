package de.qaware.smartlabjob.repository;

import de.qaware.smartlabjob.entity.JobInfo;
import org.springframework.data.repository.CrudRepository;

public interface IJobManagementRepository extends CrudRepository<JobInfo, Long> { }
