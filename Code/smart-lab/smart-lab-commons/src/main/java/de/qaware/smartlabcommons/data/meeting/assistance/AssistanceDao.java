package de.qaware.smartlabcommons.data.meeting.assistance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AssistanceDao implements IAssistanceDao {

    private String assistance;

    @Override
    public Optional<IAssistance> toAssistance() {
        return AssistanceFactory.fromAssistanceDao(this);
    }
}
