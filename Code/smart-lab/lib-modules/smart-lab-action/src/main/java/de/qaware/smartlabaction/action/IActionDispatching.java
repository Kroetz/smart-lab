package de.qaware.smartlabaction.action;

import de.qaware.smartlabaction.action.result.IActionResult;
import org.apache.logging.log4j.util.Supplier;

public interface IActionDispatching extends Supplier<IActionResult> {

    default IActionResult dispatch() {
        return get();
    }
}
