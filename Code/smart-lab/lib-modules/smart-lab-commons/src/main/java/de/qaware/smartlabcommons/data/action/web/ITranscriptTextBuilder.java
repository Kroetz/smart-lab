package de.qaware.smartlabcommons.data.action.web;

import java.util.List;

public interface ITranscriptTextBuilder {

    String buildText(List<ITextPassage> textPassages);
    void addTextPassages(StringBuilder stringBuilder, List<ITextPassage> textPassages);
    void addTextPassage(StringBuilder stringBuilder, ITextPassage textPassage);
}
