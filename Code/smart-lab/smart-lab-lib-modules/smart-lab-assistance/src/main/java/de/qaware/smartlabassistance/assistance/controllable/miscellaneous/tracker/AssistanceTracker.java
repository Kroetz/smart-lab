package de.qaware.smartlabassistance.assistance.controllable.miscellaneous.tracker;

import de.qaware.smartlabassistance.assistance.controllable.generic.IAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.miscellaneous.factory.IAssistanceControllableFactory;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.AssistanceTrackingException;
import de.qaware.smartlabcore.exception.UnknownAssistanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

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
    private final ConcurrentMap<IAssistanceContext, IAssistanceControllable> trackedAssistances;
    private final IResolver<String, IAssistanceControllableFactory> assistanceControllableFactoryResolver;

    public AssistanceTracker(IResolver<String, IAssistanceControllableFactory> assistanceControllableFactoryResolver) {
        this.trackedAssistances = new ConcurrentHashMap<>();
        this.assistanceControllableFactoryResolver = assistanceControllableFactoryResolver;
    }

    @Override
    public IAssistanceControllable track(IAssistanceContext context) throws AssistanceTrackingException {
        IAssistanceControllableFactory factory = this.assistanceControllableFactoryResolver
                .resolve(context.getAssistanceConfiguration().getAssistanceId())
                .orElseThrow(UnknownAssistanceException::new);
        IAssistanceControllable assistance = factory.newInstance();
        IAssistanceControllable alreadyTracked = this.trackedAssistances.putIfAbsent(context, assistance);
        if(nonNull(alreadyTracked)) throw new AssistanceTrackingException(
                format("The assistance with the configuration %s is already tracked", context.getAssistanceConfiguration()));
        return assistance;
    }

    @Override
    public void stopTracking(IAssistanceContext context) throws AssistanceTrackingException {
        requireTracked(context);
        this.trackedAssistances.remove(context);
    }

    @Override
    public IAssistanceControllable getTracked(IAssistanceContext context) throws AssistanceTrackingException {
        requireTracked(context);
        return this.trackedAssistances.get(context);
    }

    @Override
    public IAssistanceControllable updateTracked(IAssistanceContext context, IAssistanceControllable assistance) {
        requireTracked(context);
        this.trackedAssistances.replace(context, assistance);
        return assistance;
    }

    private void requireTracked(IAssistanceContext context) throws AssistanceTrackingException {
        if(!this.trackedAssistances.containsKey(context)) throw new AssistanceTrackingException(
                format("The assistance with the configuration %s is not tracked", context.getAssistanceConfiguration()));
    }

    private void requireNotTracked(IAssistanceContext context) throws AssistanceTrackingException {
        if(this.trackedAssistances.containsKey(context)) throw new AssistanceTrackingException(
                format("The assistance with the configuration %s is already tracked", context.getAssistanceConfiguration()));
    }
}
