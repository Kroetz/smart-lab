package de.qaware.smartlabaction.action.executable;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ActionExecutableResolver extends AbstractResolver<String, IActionExecutable> {

    public ActionExecutableResolver(List<IActionExecutable> actions) {
        super(ActionExecutableResolver.getActionsById(actions));
    }

    private static Map<String, IActionExecutable> getActionsById(List<IActionExecutable> actions) {
        return actions.stream().collect(Collectors.toMap(IActionExecutable::getActionId, Function.identity()));
    }
}
