package de.qaware.smartlabcommons.data.workgroup;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.IEntity;

import java.net.URL;
import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Workgroup.class)
})
public interface IWorkgroup extends IEntity {

    String getName();
    Collection<String> getMemberIds();
    URL getKnowledgeBase();
    URL getCodeRepository();
}
