package de.qaware.smartlabcore.content

import geb.Page

class MeetingStatusPage extends Page {

    // TODO

    static url = "/meeting-status_page"

    static at = { title == "Meeting status" }

    static content = {
        heading  { $("h1").text() }
        startMeetingButton { $("Start Meeting") }
        stopMeetingButton { $("Stop Meeting") }
        extendMeetingButton { $("Extend Meeting") }
    }

    void doThings(String foo, String bar) {
        startMeetingButton.click()
    }
}
