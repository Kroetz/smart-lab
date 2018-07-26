package de.qaware.smartlabcore.service.url;

import java.net.MalformedURLException;
import java.net.URL;

public interface IBaseUrlDetector {

    URL detect() throws MalformedURLException;
}
