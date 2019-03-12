package ebing.top.dog.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-dog", path = "/backdoor")
public interface BackdoorClient {

	@GetMapping("/monitor")
	String monitor();

}
