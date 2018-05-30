package de.qaware.smartlabcommons.data.action.uploaddata;

import de.qaware.smartlabcommons.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcommons.exception.ServiceFailedException;

public interface IUploadDataService {

    String getServiceId();
    void upload(IKnowledgeBaseInfo knowledgeBaseInfo, String dataToUpload) throws ServiceFailedException;
}
