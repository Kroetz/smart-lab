package de.qaware.smartlabcommons.data.action.generic;

import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;
import org.apache.logging.log4j.util.Supplier;

public interface IActionDispatching extends Supplier<IActionResult> {

    default IActionResult dispatch() {
        return get();
    }
}
