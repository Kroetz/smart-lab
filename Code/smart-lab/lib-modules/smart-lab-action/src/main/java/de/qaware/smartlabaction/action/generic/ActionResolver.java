package de.qaware.smartlabaction.action.generic;

import de.qaware.smartlabcommons.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ActionResolver extends AbstractResolver<String, IAction> {

    public ActionResolver(List<IAction> actions) {
        super(ActionResolver.getActionsById(actions));
    }

    private static Map<String, IAction> getActionsById(List<IAction> actions) {
        return actions.stream().collect(Collectors.toMap(IAction::getActionId, Function.identity()));
    }
}
