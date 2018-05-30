package de.qaware.smartlabaction.action.generic;

import de.qaware.smartlabcommons.data.action.generic.IActionExecutable;
import de.qaware.smartlabcommons.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ActionResolver extends AbstractResolver<String, IActionExecutable> {

    public ActionResolver(List<IActionExecutable> actions) {
        super(ActionResolver.getActionsById(actions));
    }

    private static Map<String, IActionExecutable> getActionsById(List<IActionExecutable> actions) {
        return actions.stream().collect(Collectors.toMap(IActionExecutable::getActionId, Function.identity()));
    }
}
