package de.qaware.smartlabcommons.data.action.speechtotext;

public interface ITranscript {

    String toHumanReadable(ITranscriptTextBuilder transcriptTextBuilder, ITextPassagesBuilder textPassagesBuilder);
}
