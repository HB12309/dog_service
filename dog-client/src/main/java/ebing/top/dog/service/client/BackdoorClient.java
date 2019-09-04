package ebing.top.dog.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dog
 */
@FeignClient(value = "service-dog", path = "/backdoor")
public interface BackdoorClient {

	/**
	 * hello world
	 * @param ''
	 * @return String
	 */
	@GetMapping("/monitor")
	String monitor(
		@RequestParam(value = "type", required = false)  String type
	);

	@PostMapping("/binary_tree")
	String binaryTree();

}
