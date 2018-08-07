package de.qaware.smartlab.event.management.service.repository.parser;

import de.qaware.smartlab.core.data.event.IEvent;

public interface IEventParser {

    IEvent parse(String stringToParse);
    String unparse(IEvent event);
}
