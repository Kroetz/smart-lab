package de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.generic;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.IDataDownloadService;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.IDataUploadService;

public interface IProjectBaseAdapter extends IActuatorAdapter, IDataUploadService, IDataDownloadService { }
