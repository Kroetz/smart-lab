package de.qaware.smartlab.data.conversion.converter.event;

import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.data.event.AssistanceConfigurationDto;
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
        IAssistanceInfo assistanceInfo = this.assistanceInfoResolver.resolve(assistanceConfiguration.getAssistanceId());
        return assistanceInfo.createConfiguration(assistanceConfiguration.getConfigProperties());
    }
}
