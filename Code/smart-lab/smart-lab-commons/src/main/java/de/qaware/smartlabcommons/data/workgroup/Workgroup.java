package de.qaware.smartlabcommons.data.workgroup;

import de.qaware.smartlabcommons.data.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workgroup {

    private long id;
    private String name;
    private Collection<Long> memberIds;
    private URL knowledgeBase;
    private URL codeRepository;
}
