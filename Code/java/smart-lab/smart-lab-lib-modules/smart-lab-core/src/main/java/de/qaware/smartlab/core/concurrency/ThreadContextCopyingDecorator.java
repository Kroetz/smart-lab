package de.qaware.smartlab.core.concurrency;

import org.springframework.core.task.TaskDecorator;

public class ThreadContextCopyingDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        final ThreadContext context = ThreadContext.copy();
        return () -> {
            try {
                ThreadContext.set(context);
                runnable.run();
            } finally {
                ThreadContext.reset();
            }
        };
    }
}
