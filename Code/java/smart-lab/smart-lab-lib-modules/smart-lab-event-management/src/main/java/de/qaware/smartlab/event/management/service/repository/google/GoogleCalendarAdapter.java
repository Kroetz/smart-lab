package de.qaware.smartlab.event.management.service.repository.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.collect.BiMap;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.Event;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityCreationException;
import de.qaware.smartlab.core.exception.InvalidSyntaxException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.result.DeletionResult;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.result.ShiftResult;
import de.qaware.smartlab.core.result.ShorteningResult;
import de.qaware.smartlab.core.service.repository.AbstractBasicEntityManagementRepository;
import de.qaware.smartlab.event.management.service.repository.IEventManagementRepository;
import de.qaware.smartlab.event.management.service.repository.parser.IEventParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static de.qaware.smartlab.core.miscellaneous.TimeUtils.isNowInProgress;
import static java.lang.String.format;
import static java.nio.file.Files.newInputStream;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.EVENT_MANAGEMENT_REPOSITORY,
        name = Property.Name.EVENT_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.EventManagementRepository.GOOGLE_CALENDAR)
@Slf4j
public class GoogleCalendarAdapter extends AbstractBasicEntityManagementRepository<IEvent, EventId> implements IEventManagementRepository {

    private final Calendar service;
    private final BiMap<LocationId, String> calendarIdsByLocationId;
    private final IEventParser eventParser;

    public GoogleCalendarAdapter(
            // TODO: String literals
            @Qualifier("googleCalendarCredentialFile") Path googleCalendarCredentialFile,
            // TODO: String literal
            @Qualifier("googleCalendarScopes") Collection<String> googleCalendarScopes,
            // TODO: String literals
            @Qualifier("googleCalendarApplicationName") String googleCalendarApplicationName,
            HttpTransport googleCalendarHttpTransport,
            JsonFactory googleCalendarJsonFactory,
            @Qualifier("googleCalendarLocationMapping") BiMap<LocationId, String> googleCalendarLocationMapping,
            IEventParser eventParser,
            Set<IEvent> initialEvents) throws IOException {
        super(initialEvents);
        GoogleCredential credentials = GoogleCredential.fromStream(
                newInputStream(googleCalendarCredentialFile),
                googleCalendarHttpTransport,
                googleCalendarJsonFactory).createScoped(googleCalendarScopes);
        this.service = new Calendar.Builder(
                googleCalendarHttpTransport,
                googleCalendarJsonFactory,
                credentials)
                .setApplicationName(googleCalendarApplicationName)
                .build();
        this.calendarIdsByLocationId = googleCalendarLocationMapping;
        this.eventParser = eventParser;
    }

    private String resolveCalendarId(LocationId locationId) throws IllegalArgumentException {
        String calendarId = this.calendarIdsByLocationId.get(locationId);
        if(nonNull(calendarId)) return calendarId;
        throw new IllegalArgumentException(format("The location \"%s\" has no mapped calendar", locationId));
    }

    private LocationId resolveLocationId(String calendarId) throws IllegalArgumentException {
        LocationId locationId = this.calendarIdsByLocationId.inverse().get(calendarId);
        if(nonNull(locationId)) return locationId;
        throw new IllegalArgumentException(format("The calendar \"%s\" has no mapped location", calendarId));
    }

    private boolean hasMappedCalendar(LocationId locationId) {
        try {
            resolveCalendarId(locationId);
            return true;
        }
        catch(IllegalArgumentException e) {
            return false;
        }
    }

    private boolean hasMappedLocation(String calendarId) {
        try {
            resolveLocationId(calendarId);
            return true;
        }
        catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Set<IEvent> findAll() {
        return googleCalEventsToSmartLabEvents(findAllGoogleCalEvents());
    }

    private Set<com.google.api.services.calendar.model.Event> findAllGoogleCalEvents() {
        Set<com.google.api.services.calendar.model.Event> foundEvents = new HashSet<>();
        List<CalendarListEntry> calendars = findAllCalendars();
        for(CalendarListEntry calendar : calendars) {
            String calendarId = calendar.getId();
            if(hasMappedLocation(calendarId)) {
                foundEvents.addAll(findAllGoogleCalEvents(calendarId));
            }
            else {
                log.warn("Ignoring events from calendar \"{}\" since it has no mapped location ID", calendarId);
            }
        }
        return foundEvents;
    }

    @Override
    public Set<IEvent> findAll(LocationId locationId) {
        return googleCalEventsToSmartLabEvents(findAllGoogleCalEvents(locationId));
    }

    private Set<com.google.api.services.calendar.model.Event> findAllGoogleCalEvents(LocationId locationId) {
        try {
            return findAllGoogleCalEvents(resolveCalendarId(locationId));
        }
        catch(IllegalArgumentException e) {
            log.warn("Cannot find events at location \"{}\" since it has no mapped calendar ID", locationId, e);
            return emptySet();
        }
    }

    @Override
    public Set<IEvent> findAll(WorkgroupId workgroupId) {
        return findAll().stream()
                .filter(event -> event.getWorkgroupId().equals(workgroupId))
                .collect(toSet());
    }

    @Override
    public Set<IEvent> findAllCurrent() {
        return googleCalEventsToSmartLabEvents(findAllCurrentGoogleCalEvents());
    }

    private Set<com.google.api.services.calendar.model.Event> findAllCurrentGoogleCalEvents() {
        Set<com.google.api.services.calendar.model.Event> events = findAllGoogleCalEvents();
        return events.stream()
                .filter(this::isEventInProgress)
                .collect(toSet());
    }

    private Set<IEvent> findAll(String calendarId) {
        return googleCalEventsToSmartLabEvents(findAllGoogleCalEvents(calendarId));
    }

    private Set<com.google.api.services.calendar.model.Event> findAllGoogleCalEvents(String calendarId) {
        return findAllGoogleCalEvents(calendarId, null, null);
    }

    private Set<IEvent> findAll(String calendarId, @Nullable Instant from, @Nullable Instant until) {
        return googleCalEventsToSmartLabEvents(findAllGoogleCalEvents(calendarId, from, until));
    }

    private Set<com.google.api.services.calendar.model.Event> findAllGoogleCalEvents(String calendarId, @Nullable Instant from, @Nullable Instant until) {
        try {
            Calendar.Events.List listRequest = this.service.events().list(calendarId);
            if(nonNull(from)) listRequest.setTimeMin(instantToDateTime(from));
            if(nonNull(until)) listRequest.setTimeMax(instantToDateTime(until));
            return new HashSet<>(listRequest
                    .execute()
                    .getItems());
        }
        catch(GoogleJsonResponseException e) {
            log.info("Cannot find calendar \"{}\"", calendarId, e);
            return emptySet();
        }
        catch (IOException e) {
            log.error("I/O error while querying events from calendar \"{}\"", calendarId, e);
            return emptySet();
        }
    }

    @Override
    public Optional<IEvent> findOne(EventId eventId) {
        try {
            String calendarId = resolveCalendarId(eventId.getLocationIdPart());
            return findOne(eventId.getNameIdPart(), calendarId);
        }
        catch(IllegalArgumentException e) {
            log.warn("Cannot find event \"{}\" at location \"{}\" since it has no mapped calendar ID", eventId, eventId.getLocationIdPart(), e);
            return Optional.empty();
        }
    }

    private Optional<IEvent> findOne(String eventId, String calendarId) {
        try {
            com.google.api.services.calendar.model.Event event = this.service.events()
                    .get(calendarId, eventId)
                    .execute();
            log.info("Found event \"{}\" in calendar \"{}\"", eventId, calendarId);
            return googleCalEventToSmartLabEvent(event);
        }
        catch(GoogleJsonResponseException e) {
            log.info("Cannot find event \"{}\" in calendar \"{}\"", eventId, calendarId, e);
            return Optional.empty();
        }
        catch(IOException e) {
            log.error("I/O error while querying event \"{}\" from calendar \"{}\"", eventId, calendarId, e);
            return Optional.empty();
        }
    }

    @Override
    public Map<EventId, Optional<IEvent>> findMultiple(Set<EventId> eventIds) {
        Map<EventId, Optional<IEvent>> events = new HashMap<>();
        for(EventId eventId : eventIds) {
            events.put(eventId, findOne(eventId));
        }
        return events;
    }

    @Override
    public Optional<IEvent> findCurrent(LocationId locationId) {
        return findCurrentGoogleCalEvent(locationId)
                .map(this::googleCalEventToSmartLabEvent)
                .orElse(Optional.empty());
    }

    private Optional<com.google.api.services.calendar.model.Event> findCurrentGoogleCalEvent(LocationId locationId) {
        Set<com.google.api.services.calendar.model.Event> events = findAllGoogleCalEvents(locationId);
        return events.stream()
                .filter(this::isEventInProgress)
                .findFirst();
    }

    @Override
    public Optional<IEvent> findCurrent(WorkgroupId workgroupId) {
        return findAllCurrent().stream()
                .filter(event -> event.getWorkgroupId().equals(workgroupId))
                .findFirst();
    }

    @Override
    public IEvent create(IEvent event) {
        try {
            // TODO: Meaningful exception message
            if(isCollidingWithOtherEvents(event)) throw new EntityConflictException();
            String calendarId = resolveCalendarId(event.getId().getLocationIdPart());
            com.google.api.services.calendar.model.Event createdEvent =
                    this.service.events().insert(calendarId, smartLabEventToGoolgeCalEvent(event)).execute();
            log.info("Created event \"{}\"", event);
            // TODO: Meaningful exception message
            return googleCalEventToSmartLabEvent(createdEvent).orElseThrow(EntityCreationException::new);
        }
        catch(IllegalArgumentException e) {
            log.error("Cannot create event \"{}\" at location \"{}\" since it has no mapped calendar ID", event, event.getLocationId());
            // TODO: Meaningful exception message
            throw new EntityCreationException(e);
        }
        catch(IOException e) {
            log.error("I/O error while creating event \"{}\"", event);
            // TODO: Meaningful exception message
            throw new EntityCreationException(e);
        }
    }

    private boolean isCollidingWithOtherEvents(IEvent eventToCheck) throws IllegalArgumentException {
        String calendarId = resolveCalendarId(eventToCheck.getId().getLocationIdPart());
        Set<IEvent> collidingEvents = findAll(calendarId, eventToCheck.getStart(), eventToCheck.getEnd())
                .stream()
                .filter(event -> !event.getId().equals(eventToCheck.getId()))
                .collect(toSet());
        return !collidingEvents.isEmpty();
    }

    @Override
    public Set<IEvent> create(Set<IEvent> events) {
        Set<IEvent> createdEvents = new HashSet<>();
        for(IEvent event : events) {
            createdEvents.add(create(event));
        }
        return createdEvents;
    }

    @Override
    public DeletionResult delete(EventId eventId) {
        try {
            String calendarId = resolveCalendarId(eventId.getLocationIdPart());
            this.service.events().delete(calendarId, eventId.getNameIdPart()).execute();
            log.info("Deleted event \"{}\"", eventId);
            return DeletionResult.SUCCESS;
        }
        catch(IllegalArgumentException e) {
            log.error("Cannot delete event \"{}\" at location \"{}\" since it has no mapped calendar ID", eventId, eventId.getLocationIdPart(), e);
            return DeletionResult.ERROR;
        }
        catch(GoogleJsonResponseException e) {
            log.error("Cannot delete event \"{}\" at location \"{}\" since it does not exist", eventId, eventId.getLocationIdPart(), e);
            return DeletionResult.ERROR;
        }
        catch(IOException e) {
            log.error("I/O error while deleting event \"{}\"", eventId, e);
            return DeletionResult.ERROR;
        }
    }

    @Override
    public ShorteningResult shortenEvent(IEvent event, Duration shortening) {
        IEvent shortenedEvent = event.withEnd(event.getEnd().minus(shortening));
        String calendarId = resolveCalendarId(shortenedEvent.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    shortenedEvent.getId().getNameIdPart(),
                    smartLabEventToGoolgeCalEvent(shortenedEvent)).execute();
        } catch (IOException e) {
            log.error("I/O error while shortening event \"{}\"", event, e);
            return ShorteningResult.ERROR;
        }
        return ShorteningResult.SUCCESS;
    }

    @Override
    public ExtensionResult extendEvent(IEvent event, Duration extension) {
        IEvent extendedEvent = event.withEnd(event.getEnd().plus(extension));
        if(isCollidingWithOtherEvents(extendedEvent)) return ExtensionResult.CONFLICT;
        String calendarId = resolveCalendarId(extendedEvent.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    extendedEvent.getId().getNameIdPart(),
                    smartLabEventToGoolgeCalEvent(extendedEvent)).execute();
        } catch (IOException e) {
            log.error("I/O error while extending event \"{}\"", event, e);
            return ExtensionResult.ERROR;
        }
        return ExtensionResult.SUCCESS;
    }

    @Override
    public ShiftResult shiftEvent(IEvent event, Duration shift) {
        IEvent shiftedEvent = event.withStartAndEnd(
                event.getStart().plus(shift),
                event.getEnd().plus(shift));
        if(isCollidingWithOtherEvents(shiftedEvent)) return ShiftResult.CONFLICT;
        String calendarId = resolveCalendarId(shiftedEvent.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    shiftedEvent.getId().getNameIdPart(),
                    smartLabEventToGoolgeCalEvent(shiftedEvent)).execute();
        } catch (IOException e) {
            log.error("I/O error while shifting event \"{}\"", event, e);
            return ShiftResult.ERROR;
        }
        return ShiftResult.SUCCESS;
    }

    private List<CalendarListEntry> findAllCalendars() {
        try {
            return this.service.calendarList().list().execute().getItems();
        }
        catch(IOException e) {
            log.error("I/O error while querying available calendars", e);
            return emptyList();
        }
    }

    private CalendarListEntry findSpecificCalendar(String calendarId) throws IOException {
        return this.service.calendarList().get(calendarId).execute();
    }

    private Set<IEvent> googleCalEventsToSmartLabEvents(Set<com.google.api.services.calendar.model.Event> events) {
        return events
                .stream()
                .map(this::googleCalEventToSmartLabEvent)
                .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty)) // Filter out empty optionals
                .collect(toSet());
    }

    private Optional<IEvent> googleCalEventToSmartLabEvent(com.google.api.services.calendar.model.Event event) {
        String description = event.getDescription();
        description = isNull(description) ? EMPTY : description;
        IEvent parsedEvent;
        try {
            parsedEvent = this.eventParser.parse(description);
        }
        catch(InvalidSyntaxException e) {
            log.error("Failed to create Smart Lab event from Google calendar event {} because of invalid configuration syntax", event, e);
            return Optional.empty();
        }
        return Optional.of(Event.builder()
                .id(EventId.of(event.getId(), LocationId.of(event.getLocation())))
                .title(event.getSummary())
                .start(eventDateTimeToInstant(event.getStart()))
                .end(eventDateTimeToInstant(event.getEnd()))
                .build()
                .merge(parsedEvent));
    }

    private com.google.api.services.calendar.model.Event smartLabEventToGoolgeCalEvent(IEvent event) {
        String eventConfigString = this.eventParser.unparse(event);
        return new com.google.api.services.calendar.model.Event()
            .setSummary(event.getTitle())
            .setStart(instantToEventDateTime(event.getStart()))
            .setEnd(instantToEventDateTime(event.getEnd()))
            .setLocation(event.getLocationId().getIdValue())
            .setDescription(eventConfigString);
    }

    private boolean isEventInProgress(com.google.api.services.calendar.model.Event event) {
        return isNowInProgress(
                eventDateTimeToInstant(event.getStart()),
                eventDateTimeToInstant(event.getEnd()));
    }

    private Instant dateTimeToInstant(DateTime dateTime) {
        return Instant.ofEpochMilli(dateTime.getValue());
    }

    private Instant eventDateTimeToInstant(EventDateTime eventDateTime) {
        return dateTimeToInstant(eventDateTime.getDateTime());
    }

    private DateTime instantToDateTime(Instant instant) {
        return new DateTime(instant.toEpochMilli());
    }

    private EventDateTime instantToEventDateTime(Instant instant) {
        return new EventDateTime().setDateTime(instantToDateTime(instant));
    }
}
