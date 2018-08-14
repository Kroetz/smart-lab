package de.qaware.smartlab.core.data.job;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.constant.Miscellaneous;

import java.net.URL;
import java.time.Instant;
import java.util.Optional;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Miscellaneous.JSON_TYPE_PROPERTY_NAME)
public interface IJobInfo {

    Long getId();
    JobStatus getStatus();
    Optional<URL> getCallbackUrl();
    Instant getCreation();
    Optional<Instant> getProcessingStart();
    Optional<Instant> getProcessingEnd();
    Optional<String> getErrorMessage();
}
