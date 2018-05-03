package de.qaware.smartlabcommons.data.workgroup;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.IEntity;

import java.net.URL;
import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface IWorkgroup extends IEntity {

    String getName();
    Set<String> getMemberIds();
    URL getKnowledgeBase();
    URL getCodeRepository();
}
