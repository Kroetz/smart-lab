package de.qaware.smartlab.microservice.service.connector.device;

import de.qaware.smartlab.api.service.client.device.IDeviceManagementApiClient;
import de.qaware.smartlab.api.service.connector.device.IDeviceManagementService;
import de.qaware.smartlab.core.data.device.DeviceDto;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.microservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
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
