package de.qaware.smartlabcore.data.action.uploaddata;

import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcore.exception.ServiceFailedException;

public interface IUploadDataService {

    String getServiceId();
    void upload(IKnowledgeBaseInfo knowledgeBaseInfo, String dataToUpload) throws ServiceFailedException;
}
