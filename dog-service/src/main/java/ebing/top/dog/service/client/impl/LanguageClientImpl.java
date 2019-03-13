package ebing.top.dog.service.client.impl;

import ebing.top.dog.service.Utils.BinaryTree;
import ebing.top.dog.service.client.LanguageClient;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;


@Api(tags = "language模块", description = "language 模块相关接口")
@Validated
@RequestMapping("/language")
@RestController
/**
 * @author dog
 */
public class LanguageClientImpl implements LanguageClient {

	@Override
	@GetMapping("/thread")
	public String thread() {
		Integer a = ThreadLocalRandom.current().nextInt();
		return Integer.toString(ThreadLocalRandom.current().nextInt(0) + 100000);
	}
}
