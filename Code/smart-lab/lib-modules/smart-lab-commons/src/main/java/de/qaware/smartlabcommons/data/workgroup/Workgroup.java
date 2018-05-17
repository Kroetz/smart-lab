package de.qaware.smartlabcommons.data.workgroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workgroup implements IWorkgroup {

    private String id;
    private String name;
    private Set<String> memberIds;
    private URL knowledgeBase;
    private URL codeRepository;
}