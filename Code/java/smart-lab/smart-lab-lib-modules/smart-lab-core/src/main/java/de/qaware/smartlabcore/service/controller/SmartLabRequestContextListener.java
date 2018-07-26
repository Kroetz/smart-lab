package de.qaware.smartlabcore.service.controller;

import de.qaware.smartlabcore.concurrency.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletRequestEvent;
import java.net.MalformedURLException;

import static de.qaware.smartlabcore.service.controller.RequestContextUtils.currentRequestUrl;

@Component
@Slf4j
public class SmartLabRequestContextListener extends RequestContextListener {

    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        super.requestInitialized(requestEvent);
        try {
            ThreadContext.get().setAssociatedRequestUrl(currentRequestUrl());
        } catch (MalformedURLException e) {
            log.error("Could not set URL of current request in thread context", e);
        }
        log.debug("Request context initialized");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {
        super.requestDestroyed(requestEvent);
        ThreadContext.reset();
        log.debug("Request context destroyed");
    }
}
