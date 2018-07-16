package de.qaware.smartlabaction.action.executable;

import de.qaware.smartlabaction.action.executable.generic.IActionExecutable;
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
public class ActionExecutableResolver extends AbstractResolver<String, IActionExecutable> {

    public ActionExecutableResolver(List<IActionExecutable> actions) {
        super(getActionsById(actions));
    }

    private static Set<Map.Entry<String, IActionExecutable>> getActionsById(List<IActionExecutable> actions) {
        return actions.stream()
                .collect(toMap(IActionExecutable::getActionId, identity()))
                .entrySet();
    }
}
