package de.qaware.smartlabactuatoradapter.actuator.speechtotext.remeeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// TODO: Better class name?
public class SubmitJobResponse {

    // TODO: Eliminate string literals

    @JsonProperty("created")
    private String created;

    @JsonProperty("id")
    private String id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("status")
    private String status;
}
