package de.qaware.smartlab.core.data.job;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;

@Data
@Entity
public class JobInfo implements IJobInfo {

    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_STATUS = "status";
    private static final String FIELD_NAME_CALLBACK_URL = "callback_url";
    private static final String FIELD_NAME_CREATION = "creation";
    private static final String FIELD_NAME_PROCESSING_START = "processing_start";
    private static final String FIELD_NAME_PROCESSING_END = "processing_end";
    private static final String FIELD_NAME_ERROR_MESSAGE = "error_message";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = FIELD_NAME_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = FIELD_NAME_STATUS)
    private JobStatus status;

    @Column(name = FIELD_NAME_CALLBACK_URL)
    private URL callbackUrl;

    @Column(name = FIELD_NAME_CREATION)
    private Instant creation;

    @Column(name = FIELD_NAME_PROCESSING_START)
    private Instant processingStart;

    @Column(name = FIELD_NAME_PROCESSING_END)
    private Instant processingEnd;

    @Column(name = FIELD_NAME_ERROR_MESSAGE)
    @Lob
    private String errorMessage;

    protected JobInfo() { }

    private JobInfo(
            Long id,
            JobStatus status,
            URL callbackUrl,
            Instant creation,
            Instant processingStart,
            Instant processingEnd,
            String errorMessage) {
        this.id = id;
        this.status = status;
        this.callbackUrl = callbackUrl;
        this.creation = creation;
        this.processingStart = processingStart;
        this.processingEnd = processingEnd;
        this.errorMessage = errorMessage;
    }

    private JobInfo(@Nullable URL callbackUrl) {
        this.status = JobStatus.PENDING;
        this.callbackUrl = callbackUrl;
        this.creation = Instant.now();
    }

    public static JobInfo of(@Nullable URL callbackUrl) {
        return new JobInfo(callbackUrl);
    }

    private JobInfo createCopy() {
        return new JobInfo(
                this.id,
                this.status,
                this.callbackUrl,
                this.creation,
                this.processingStart,
                this.processingEnd,
                this.errorMessage);
    }

    public JobInfo withStatus(JobStatus status) {
        JobInfo jobInfo = createCopy();
        jobInfo.setStatus(status);
        return jobInfo;
    }

    public JobInfo withProcessingStart(Instant processingStart) {
        JobInfo jobInfo = createCopy();
        jobInfo.setProcessingStart(processingStart);
        return jobInfo;
    }

    public JobInfo withProcessingEnd(Instant processingEnd) {
        JobInfo jobInfo = createCopy();
        jobInfo.setProcessingEnd(processingEnd);
        return jobInfo;
    }

    public JobInfo withErrorMessage(String errorMessage) {
        JobInfo jobInfo = createCopy();
        jobInfo.setErrorMessage(errorMessage);
        return jobInfo;
    }

    public Optional<URL> getCallbackUrl() {
        return Optional.ofNullable(this.callbackUrl);
    }

    public Optional<Instant> getProcessingStart() {
        return Optional.ofNullable(this.processingStart);
    }

    public Optional<Instant> getProcessingEnd() {
        return Optional.ofNullable(this.processingEnd);
    }

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(this.errorMessage);
    }
}
