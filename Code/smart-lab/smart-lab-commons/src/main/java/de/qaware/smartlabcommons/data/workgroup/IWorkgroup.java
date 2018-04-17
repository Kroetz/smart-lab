package de.qaware.smartlabcommons.data.workgroup;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.net.URL;
import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Workgroup.class)
})
public interface IWorkgroup {

    long getId();
    String getName();
    Collection<Long> getMemberIds();
    URL getKnowledgeBase();
    URL getCodeRepository();
}
