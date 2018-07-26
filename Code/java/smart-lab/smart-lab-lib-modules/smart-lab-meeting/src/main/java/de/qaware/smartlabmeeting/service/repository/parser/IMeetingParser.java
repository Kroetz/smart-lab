package de.qaware.smartlabmeeting.service.repository.parser;

import de.qaware.smartlabcore.data.meeting.IMeeting;

public interface IMeetingParser {

    IMeeting parse(String stringToParse);
    String unparse(IMeeting meeting);
}
