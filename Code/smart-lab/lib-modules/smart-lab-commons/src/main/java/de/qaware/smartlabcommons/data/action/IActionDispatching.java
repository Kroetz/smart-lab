package de.qaware.smartlabcommons.data.action;

import org.apache.logging.log4j.util.Supplier;

public interface IActionDispatching extends Supplier<IActionResult> {

    default IActionResult dispatch() {
        return get();
    }
}
