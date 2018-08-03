package de.qaware.smartlabaction.action.actor.generic;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class KnowledgeBaseInfoFactoryResolver extends AbstractResolver<String, IKnowledgeBaseInfoFactory> {

    public KnowledgeBaseInfoFactoryResolver(List<IKnowledgeBaseInfoFactory> factories) {
        super(getFactoriesByKnowledgeBaseId(factories));
    }

    private static Set<Map.Entry<String, IKnowledgeBaseInfoFactory>> getFactoriesByKnowledgeBaseId(List<IKnowledgeBaseInfoFactory> factories) {
        return factories.stream()
                .collect(toMap(IKnowledgeBaseInfoFactory::getServiceId, identity()))
                .entrySet();
    }
}
