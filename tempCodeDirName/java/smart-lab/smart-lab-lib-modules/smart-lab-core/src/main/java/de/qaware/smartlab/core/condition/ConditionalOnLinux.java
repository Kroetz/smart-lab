package de.qaware.smartlab.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;

public class ConditionalOnLinux implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        return IS_OS_LINUX;
    }
}
