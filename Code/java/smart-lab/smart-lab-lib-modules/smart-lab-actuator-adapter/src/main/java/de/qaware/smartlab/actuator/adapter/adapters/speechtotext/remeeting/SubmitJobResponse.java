package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// TODO: Better class name?
public class SubmitJobResponse {

    private static final String FIELD_NAME_CREATED = "created";
    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_URL = "url";
    private static final String FIELD_NAME_STATUS = "status";

    @JsonProperty(FIELD_NAME_CREATED)
    private String created;

    @JsonProperty(FIELD_NAME_ID)
    private String id;

    @JsonProperty(FIELD_NAME_URL)
    private String url;

    @JsonProperty(FIELD_NAME_STATUS)
    private String status;
}
