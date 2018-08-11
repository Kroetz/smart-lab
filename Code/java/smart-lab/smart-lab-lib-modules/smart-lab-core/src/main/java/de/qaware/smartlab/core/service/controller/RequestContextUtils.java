package de.qaware.smartlab.core.service.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

import static de.qaware.smartlab.core.util.StringUtils.EMPTY;

public abstract class RequestContextUtils {

    public static URL currentRequestUrl() throws MalformedURLException {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        return builder.build().toUri().toURL();
    }

    public static URL currentRequestBaseUrl() throws MalformedURLException {
        URL currentRequestUrl = currentRequestUrl();
        return new URL(
                currentRequestUrl.getProtocol(),
                currentRequestUrl.getHost(),
                currentRequestUrl.getPort(),
                EMPTY);
    }
}
