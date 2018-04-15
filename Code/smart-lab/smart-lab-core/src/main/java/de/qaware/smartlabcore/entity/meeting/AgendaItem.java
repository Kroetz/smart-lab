package de.qaware.smartlabcore.entity.meeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaItem implements IAgendaItem {

    // Setting this field manually is needed due to a Jackson bug with Java Optionals (see https://stackoverflow.com/questions/49071166/jackson-java-util-optional-serialization-does-not-include-type-id)
    @JsonProperty
    private String type = this.getClass().getName();

    private String text;
}
