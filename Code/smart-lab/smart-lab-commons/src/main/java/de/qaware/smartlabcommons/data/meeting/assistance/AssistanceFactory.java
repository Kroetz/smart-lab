package de.qaware.smartlabcommons.data.meeting.assistance;

import de.qaware.smartlabcommons.Constants;

import java.util.Optional;

public class AssistanceFactory {

    public static Optional<IAssistance> fromAssistanceDao(IAssistanceDao assistanceDao) {

        if(assistanceDao.getAssistance().equals(Constants.MINUTE_TAKING)) {
            return Optional.of(new MinuteTaking());
        }
        if(assistanceDao.getAssistance().equals(Constants.ROOM_UNLOCKING)) {
            return Optional.of(new RoomUnlocking());
        }
        if(assistanceDao.getAssistance().equals(Constants.AGENDA_SHOWING)) {
            return Optional.of(new AgendaShowing());
        }
        return Optional.empty();
    }
}
