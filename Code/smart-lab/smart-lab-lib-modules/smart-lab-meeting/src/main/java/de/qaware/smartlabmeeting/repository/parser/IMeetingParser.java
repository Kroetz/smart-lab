package de.qaware.smartlabmeeting.repository.parser;

import de.qaware.smartlabcore.data.meeting.IMeeting;

public interface IMeetingParser {

    IMeeting parse(String stringToParse);
}
