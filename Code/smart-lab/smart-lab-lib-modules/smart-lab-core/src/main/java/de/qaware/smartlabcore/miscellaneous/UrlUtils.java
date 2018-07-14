package de.qaware.smartlabcore.miscellaneous;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class UrlUtils {

    public static URL of(URL baseUrl, String filePath) throws MalformedURLException {
        return new URL(
                baseUrl.getProtocol(),
                baseUrl.getHost(),
                baseUrl.getPort(),
                filePath);
    }
}
