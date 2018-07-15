package de.qaware.smartlabcore.data.job;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;

@Data
@Entity
public class JobInfo implements IJobInfo {

    // TODO: String literals

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "status")
    private JobStatus status;

    @Column(name = "callback_url")
    private URL callbackUrl;

    @Column(name = "creation")
    private Instant creation;

    @Column(name = "processing_start")
    private Instant processingStart;

    @Column(name = "processing_end")
    private Instant processingEnd;

    @Column(name = "error_message")
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
