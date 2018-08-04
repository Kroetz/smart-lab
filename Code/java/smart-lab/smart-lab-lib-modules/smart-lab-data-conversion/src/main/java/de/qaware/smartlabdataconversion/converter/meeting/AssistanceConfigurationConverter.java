package de.qaware.smartlabdataconversion.converter.meeting;

import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.meeting.AssistanceConfigurationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AssistanceConfigurationConverter implements IDtoConverter<IAssistanceConfiguration, AssistanceConfigurationDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    private final IResolver<String, IAssistanceInfo> assistanceInfoResolver;

    public AssistanceConfigurationConverter(IResolver<String, IAssistanceInfo> assistanceInfoResolver) {
        this.assistanceInfoResolver = assistanceInfoResolver;
    }

    @Override
    public AssistanceConfigurationDto toDto(IAssistanceConfiguration assistanceConfiguration) {
        return AssistanceConfigurationDto.builder()
                .assistanceId(assistanceConfiguration.getAssistanceId())
                .configProperties(assistanceConfiguration.getConfigProperties())
                .build();
    }

    @Override
    public IAssistanceConfiguration toEntity(AssistanceConfigurationDto assistanceConfiguration) {
        // TODO Exception message and type
        IAssistanceInfo assistanceInfo = this.assistanceInfoResolver
                .resolve(assistanceConfiguration.getAssistanceId())
                .orElseThrow(RuntimeException::new);
        return assistanceInfo.createConfiguration(assistanceConfiguration.getConfigProperties());
    }
}
