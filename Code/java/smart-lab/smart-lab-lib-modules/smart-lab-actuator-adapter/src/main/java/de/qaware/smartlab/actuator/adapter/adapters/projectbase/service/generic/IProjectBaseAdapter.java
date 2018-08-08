package de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.generic;

import de.qaware.smartlab.core.data.actuator.IDeviceAdapter;
import de.qaware.smartlab.core.data.action.datadownload.IDataDownloadService;
import de.qaware.smartlab.core.data.action.dataupload.IDataUploadService;

public interface IProjectBaseAdapter extends IDeviceAdapter, IDataUploadService, IDataDownloadService { }
