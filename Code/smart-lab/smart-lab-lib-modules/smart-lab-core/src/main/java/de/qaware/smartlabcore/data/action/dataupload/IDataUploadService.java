package de.qaware.smartlabcore.data.action.dataupload;

import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcore.exception.ServiceFailedException;

public interface IDataUploadService {

    String getServiceId();
    void upload(IKnowledgeBaseInfo knowledgeBaseInfo, String dataToUpload) throws ServiceFailedException;
}
