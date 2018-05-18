package de.qaware.smartlabcommons.data.action;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IActionResult {

    Object getValue();
    Void getVoidValue();
    Optional<String> getStringValue();
    Optional<MultipartFile> getMultipartFileValue();
}
