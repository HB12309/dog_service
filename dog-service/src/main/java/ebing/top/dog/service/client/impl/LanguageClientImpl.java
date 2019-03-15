package ebing.top.dog.service.client.impl;

import ebing.top.dog.service.client.LanguageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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
		@RequestParam(value = "type", required = false) @ApiParam("意见反馈") String type
	) {
		if ("stream".equals(type)) {
			Integer a = ThreadLocalRandom.current().nextInt();
			streamAPI();
			return Integer.toString(ThreadLocalRandom.current().nextInt(10) + 100000);
		}
		return "success";
	}
}
