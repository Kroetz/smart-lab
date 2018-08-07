package de.qaware.smartlab.core.concurrency;

import java.net.URL;
import java.util.Optional;

public class ThreadContext {

    private URL associatedRequestUrl;

    private static final ThreadLocal<ThreadContext> threadLocal = ThreadLocal.withInitial(ThreadContext::new);

    private ThreadContext() { }

    private ThreadContext(ThreadContext context) {
        context.getAssociatedRequestUrl().ifPresent(this::setAssociatedRequestUrl);
    }

    public Optional<URL> getAssociatedRequestUrl() {
        return Optional.ofNullable(this.associatedRequestUrl);
    }

    public ThreadContext setAssociatedRequestUrl(URL associatedRequestUrl) {
        this.associatedRequestUrl = associatedRequestUrl;
        return this;
    }

    public static ThreadContext get() {
        return threadLocal.get();
    }

    public static ThreadContext copy() {
        return new ThreadContext(threadLocal.get());
    }

    public static void set(ThreadContext context) {
        threadLocal.set(context);
    }

    public static void reset() {
        threadLocal.set(new ThreadContext());
    }
}
