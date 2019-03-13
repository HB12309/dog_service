package ebing.top.dog.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author dog
 */
@FeignClient(value = "service-dog", path = "/language")
public interface LanguageClient {

	/**
	 * hello world
	 * @param ''
	 * @return String
	 */
	@GetMapping("/thread")
	String thread();

}