package de.qaware.smartlabaction.action.generic;

import de.qaware.smartlabaction.action.generic.result.IActionResult;
import org.apache.logging.log4j.util.Supplier;

public interface IActionDispatching extends Supplier<IActionResult> {

    default IActionResult dispatch() {
        return get();
    }
}
