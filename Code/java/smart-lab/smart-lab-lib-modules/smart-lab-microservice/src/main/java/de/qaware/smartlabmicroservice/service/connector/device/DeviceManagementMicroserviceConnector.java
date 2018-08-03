package de.qaware.smartlabmicroservice.service.connector.device;

import de.qaware.smartlabapi.service.client.device.IDeviceManagementApiClient;
import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.device.DeviceDto;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlabmicroservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class DeviceManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IDevice, DeviceId, DeviceDto> implements IDeviceManagementService {

    private final IDeviceManagementApiClient deviceManagementApiClient;

    public DeviceManagementMicroserviceConnector(
            IDeviceManagementApiClient deviceManagementApiClient,
            IDtoConverter<IDevice, DeviceDto> deviceConverter) {
        super(deviceManagementApiClient, deviceConverter);
        this.deviceManagementApiClient = deviceManagementApiClient;
    }

    @Component
    // TODO: String literal
    @Qualifier("deviceManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IDeviceManagementApiClient deviceManagementApiClient;

        public BaseUrlGetter(IDeviceManagementApiClient deviceManagementApiClient) {
            this.deviceManagementApiClient = deviceManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.deviceManagementApiClient.getBaseUrl().getBody();
            }
            catch(RetryableException e) {
                throw e;
            }
            catch(FeignException e) {
                throw new UnknownErrorException();
            }
        }
    }
}
