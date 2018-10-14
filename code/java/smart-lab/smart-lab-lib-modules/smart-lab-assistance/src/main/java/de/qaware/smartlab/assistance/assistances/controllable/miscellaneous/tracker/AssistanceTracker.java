package de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.tracker;

import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.IAssistanceControllableFactory;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.exception.assistance.AssistanceTrackingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.String.format;
import static java.util.Objects.hash;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class AssistanceTracker implements IAssistanceTracker {

    /*
     * TODO: Replace this simple map with an persistent alternative that can be replicated between different instances of the assistance service. "MapDB" may be an option.
     * See:
     * http://www.mapdb.org/
     * https://jankotek.gitbooks.io/mapdb/content/
     * https://dzone.com/articles/mapdb-agile-java-data-engine
     * https://github.com/jankotek/mapdb
     * https://github.com/mrfrag/spring-data-mapdb
     */
    private final ConcurrentMap<AssistanceTrackingId, IAssistanceControllable> trackedAssistances;
    private final IResolver<String, IAssistanceControllableFactory> assistanceControllableFactoryResolver;

    public AssistanceTracker(IResolver<String, IAssistanceControllableFactory> assistanceControllableFactoryResolver) {
        this.trackedAssistances = new ConcurrentHashMap<>();
        this.assistanceControllableFactoryResolver = assistanceControllableFactoryResolver;
    }

    @Override
    public IAssistanceControllable track(IAssistanceContext context) throws AssistanceTrackingException {
        IAssistanceControllableFactory factory = this.assistanceControllableFactoryResolver
                .resolve(context.getAssistanceConfiguration().getAssistanceId());
        IAssistanceControllable assistance = factory.newInstance();
        IAssistanceControllable alreadyTracked = this.trackedAssistances.putIfAbsent(
                AssistanceTrackingId.of(context),
                assistance);
        if(nonNull(alreadyTracked)) throw new AssistanceTrackingException(
                format("The assistance with the configuration %s is already tracked", context.getAssistanceConfiguration()));
        return assistance;
    }

    @Override
    public void stopTracking(IAssistanceContext context) throws AssistanceTrackingException {
        requireTracked(context);
        this.trackedAssistances.remove(AssistanceTrackingId.of(context));
    }

    @Override
    public IAssistanceControllable getTracked(IAssistanceContext context) throws AssistanceTrackingException {
        requireTracked(context);
        return this.trackedAssistances.get(AssistanceTrackingId.of(context));
    }

    @Override
    public IAssistanceControllable updateTracked(IAssistanceContext context, IAssistanceControllable assistance) {
        requireTracked(context);
        this.trackedAssistances.replace(AssistanceTrackingId.of(context), assistance);
        return assistance;
    }

    private void requireTracked(IAssistanceContext context) throws AssistanceTrackingException {
        AssistanceTrackingId trackingId = AssistanceTrackingId.of(context);
        if(!this.trackedAssistances.containsKey(trackingId)) {
            String errorMessage = format("The assistance with the tracking ID %s and context %s is not tracked", trackingId, context);
            log.error(errorMessage);
            throw new AssistanceTrackingException(errorMessage);
        }
    }

    private void requireNotTracked(IAssistanceContext context) throws AssistanceTrackingException {
        AssistanceTrackingId trackingId = AssistanceTrackingId.of(context);
        if(this.trackedAssistances.containsKey(trackingId)) {
            String errorMessage = format("The assistance with the trackingId %s and context %s is already tracked", trackingId, context);
            log.error(errorMessage);
            throw new AssistanceTrackingException(errorMessage);
        }
    }

    /**
     * This special tracking ID class is needed because an assistance context cannot be used as a key for the
     * assistance tracker. Properties of the context's content may change during the execution of an assistance
     * (e.g. the end time of an event) so that the tracker would not work anymore.
     */
    @Getter
    @Slf4j
    @ToString
    @EqualsAndHashCode
    public static class AssistanceTrackingId {

        private final int idValue;

        private AssistanceTrackingId(IAssistanceContext context) {
            this.idValue = calculateIdValue(context);
        }

        private int calculateIdValue(IAssistanceContext context) {
            return hash(
                    context.getAssistanceConfiguration(),
                    context.getEvent().getId(),
                    context.getWorkgroup().getId(),
                    context.getPersons().stream()
                            .map(IEntity::getId)
                            .collect(toSet()),
                    context.getLocation().getId());
        }

        public static AssistanceTrackingId of(IAssistanceContext context) {
            return new AssistanceTrackingId(context);
        }
    }
}
