package de.qaware.smartlabcommons.data.workgroup;

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
public class Workgroup implements IWorkgroup {

    private long id;
    private String name;
    private Collection<Long> memberIds;
    private URL knowledgeBase;
    private URL codeRepository;
}
