package de.qaware.smartlab.core.service.url;

import java.net.MalformedURLException;
import java.net.URL;

public interface IBaseUrlDetector {

    URL detect() throws MalformedURLException;
}
