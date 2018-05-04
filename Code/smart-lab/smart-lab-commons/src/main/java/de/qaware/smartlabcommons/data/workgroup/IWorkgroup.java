package de.qaware.smartlabcommons.data.workgroup;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.data.generic.IEntity;

import java.net.URL;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IWorkgroup extends IEntity {

    String getName();
    Set<String> getMemberIds();
    URL getKnowledgeBase();
    URL getCodeRepository();
}
