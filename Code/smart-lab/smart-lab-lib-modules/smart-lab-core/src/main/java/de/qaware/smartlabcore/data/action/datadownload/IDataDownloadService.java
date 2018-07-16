package de.qaware.smartlabcore.data.action.datadownload;

import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcore.exception.ServiceFailedException;

import java.nio.file.Path;

public interface IDataDownloadService {

    String getServiceId();
    Path download(
            IKnowledgeBaseInfo knowledgeBaseInfo,
            String dir,
            String fileName) throws ServiceFailedException;
}
