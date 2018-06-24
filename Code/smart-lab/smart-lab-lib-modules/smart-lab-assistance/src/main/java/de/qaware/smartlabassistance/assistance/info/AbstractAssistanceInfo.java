package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceInfo implements IAssistanceInfo {

    protected final String assistanceId;
    protected final Set<String> assistanceAliases;

    public AbstractAssistanceInfo(
            String assistanceId,
            Set<String> assistanceAliases) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
    }

    @Override
    public String getAssistanceId() {
        return this.assistanceId;
    }

    @Override
    public Set<String> getAssistanceAliases() {
        return this.assistanceAliases;
    }

    // TODO: Possible to force inner class for configuration?
    @Getter
    @Slf4j
    public static abstract class AbstractConfiguration implements IAssistanceConfiguration {

        protected MeetingId meetingId;
        protected RoomId roomId;
        protected String assistanceId;
        protected DeviceId deviceId;

        protected AbstractConfiguration(
                MeetingId meetingId,
                RoomId roomId,
                String assistanceId,
                DeviceId deviceId) {
            this.meetingId = meetingId;
            this.roomId = roomId;
            this.assistanceId = assistanceId;
            this.deviceId = deviceId;
        }
    }
}
