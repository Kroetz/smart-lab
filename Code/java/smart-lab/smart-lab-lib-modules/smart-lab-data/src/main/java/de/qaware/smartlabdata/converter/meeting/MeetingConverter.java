package de.qaware.smartlabdata.converter.meeting;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.meeting.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class MeetingConverter implements IDtoConverter<IMeeting, MeetingDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    private final IDtoConverter<IAgendaItem, AgendaItemDto> agendaItemConverter;
    private final IDtoConverter<IAssistanceConfiguration, AssistanceConfigurationDto> assistanceConfigurationConverter;

    public MeetingConverter(
            IDtoConverter<IAgendaItem, AgendaItemDto> agendaItemConverter,
            IDtoConverter<IAssistanceConfiguration, AssistanceConfigurationDto> assistanceConfigurationConverter) {
        this.agendaItemConverter = agendaItemConverter;
        this.assistanceConfigurationConverter = assistanceConfigurationConverter;
    }

    @Override
    public MeetingDto toDto(IMeeting meeting) {
        return MeetingDto.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .workgroupId(meeting.getWorkgroupId())
                .agenda(meeting.getAgenda().stream()
                        .map(this.agendaItemConverter::toDto)
                        .collect(toList()))
                .assistanceConfigurations(meeting.getAssistanceConfigurations().stream()
                        .map(this.assistanceConfigurationConverter::toDto)
                        .collect(toSet()))
                .start(meeting.getStart())
                .end(meeting.getEnd())
                .build();
    }

    @Override
    public IMeeting toEntity(MeetingDto meeting) {
        return Meeting.of(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getWorkgroupId(),
                meeting.getAgenda().stream()
                        .map(this.agendaItemConverter::toEntity)
                        .collect(toList()),
                meeting.getAssistanceConfigurations().stream()
                        .map(this.assistanceConfigurationConverter::toEntity)
                        .collect(toSet()),
                meeting.getStart(),
                meeting.getEnd());
    }
}
