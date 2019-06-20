package ebing.top.dog.service.client;

import ebing.top.dog.service.domain.outbound.Resp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	String thread(
		@RequestParam(value = "type", required = false)  String type
	);

	@PostMapping("/study")
	String study(
		@RequestParam(value = "type", required = false)  String type,
		@RequestParam(value = "string", required = false) String string,
		@RequestParam(value = "number", required = false) Integer number,
		@RequestParam(value = "double1", required = false) Double double1
	);

	@GetMapping("/resp")
	ResponseEntity<Resp> getProduct();

	@GetMapping("/resp2")
	Resp getProduct2();
}
