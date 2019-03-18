package ebing.top.dog.service.client.impl;

import ebing.top.dog.service.utils.CommonUtils;
import ebing.top.dog.service.client.LanguageClient;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;


@Api(tags = "language模块", description = "language 模块相关接口")
@Validated
@RequestMapping("/language")
@RestController
/**
 * @author dog
 */
public class LanguageClientImpl implements LanguageClient {

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * 哦，在调用第二个方法的时候，我的流已经被使用了，这样是不可以的。
	 *
	 * 也就是说：一个 Stream 只可以使用一次
	 */
	private void streamAPI() {
		Stream<String> stream = Stream.of("", "1", "11", "111", "1111", "11111", "Hollis");
		stream.filter( i -> i.length() < 4).forEach(System.out::println);
	}

	@Override
	@GetMapping("/thread")
	public String thread(
		@RequestParam(value = "type", required = false) String type
	) {
		if ("stream".equals(type)) {
			Integer a = ThreadLocalRandom.current().nextInt();
			streamAPI();
			return Integer.toString(ThreadLocalRandom.current().nextInt(10) + 100000);
		}
		if ("async".equals(type)) {
			System.out.println("start async");
			commonUtils.asyncTask("喜欢 async ");
			System.out.println("end async");

		}
		return "success";
	}

	@Override
	@PostMapping("/study")
	public String study(
		@RequestParam(value = "type", required = false) String type
	) {
		return "test";
	}
}
