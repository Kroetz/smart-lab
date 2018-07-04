package de.qaware.smartlabcore.generic.controller;

import de.qaware.smartlabcore.miscellaneous.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

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
                StringUtils.EMPTY);
    }
}
