package de.qaware.smartlabcore.data.action.speechtotext;

public interface ITranscript {

    String toHumanReadable(ITranscriptTextBuilder transcriptTextBuilder, ITextPassagesBuilder textPassagesBuilder);
}
