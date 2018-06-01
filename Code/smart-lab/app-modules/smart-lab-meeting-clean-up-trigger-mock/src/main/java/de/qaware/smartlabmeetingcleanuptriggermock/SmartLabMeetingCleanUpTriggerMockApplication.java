package de.qaware.smartlabmeetingcleanuptriggermock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableFeignClients(basePackages = "de.qaware.smartlabapi.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabmeetingcleanuptriggermock",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabapi"})
public class SmartLabMeetingCleanUpTriggerMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingCleanUpTriggerMockApplication.class, args);
	}

	@RestController
	class ServiceInstanceController {

		@Autowired
		private DiscoveryClient discoveryClient;

		@RequestMapping("/service-instances/{applicationName}")
		public List<ServiceInstance> serviceInstancesByApplicationName(
				@PathVariable String applicationName) {
			return this.discoveryClient.getInstances(applicationName);
		}
	}
}
