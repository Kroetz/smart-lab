package de.qaware.smartlab.core.data.action.speechtotext;

public interface ITranscript {

    String toHumanReadable(ITranscriptTextBuilder transcriptTextBuilder, ITextPassagesBuilder textPassagesBuilder);
}
