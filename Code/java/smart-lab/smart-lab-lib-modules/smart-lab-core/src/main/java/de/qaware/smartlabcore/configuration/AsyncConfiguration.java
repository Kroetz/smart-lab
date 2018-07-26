package de.qaware.smartlabcore.configuration;

import de.qaware.smartlabcore.concurrency.ThreadContextCopyingDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfiguration extends AsyncConfigurerSupport {

    @Override
    @Bean
    public Executor getAsyncExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setTaskDecorator(new ThreadContextCopyingDecorator());
        return executor;
    }
}
