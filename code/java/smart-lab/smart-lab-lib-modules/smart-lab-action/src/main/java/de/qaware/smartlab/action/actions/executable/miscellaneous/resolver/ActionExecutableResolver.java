package de.qaware.smartlab.action.actions.executable.miscellaneous.resolver;

import de.qaware.smartlab.action.actions.executable.generic.IActionExecutable;
import de.qaware.smartlab.core.resolver.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class ActionExecutableResolver extends AbstractResolver<String, IActionExecutable> {

    public ActionExecutableResolver(List<IActionExecutable> actions) {
        super(getActionsById(actions));
    }

    private static Set<Map.Entry<String, IActionExecutable>> getActionsById(List<IActionExecutable> actions) {
        return actions.stream()
                .collect(toMap(IActionExecutable::getActionId, identity()))
                .entrySet();
    }

    @Override
    protected String getErrorMessage(String actionId) {
        return format("The action \"%s\" is unknown", actionId);
    }
}
