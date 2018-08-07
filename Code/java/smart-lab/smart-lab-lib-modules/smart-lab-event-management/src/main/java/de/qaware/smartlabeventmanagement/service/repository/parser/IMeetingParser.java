package de.qaware.smartlabeventmanagement.service.repository.parser;

import de.qaware.smartlab.core.data.meeting.IMeeting;

public interface IMeetingParser {

    IMeeting parse(String stringToParse);
    String unparse(IMeeting meeting);
}
