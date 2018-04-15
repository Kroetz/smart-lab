package de.qaware.smartlabcore.entity.meeting.assistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssistanceDao implements IAssistanceDao {

    // Setting this field manually is needed due to a Jackson bug with Java Optionals (see https://stackoverflow.com/questions/49071166/jackson-java-util-optional-serialization-does-not-include-type-id)
    @JsonProperty
    private String type = this.getClass().getName();

    private String assistance;

    @Override
    public Optional<IAssistance> toAssistance() {
        return AssistanceFactory.fromAssistanceDao(this);
    }
}
