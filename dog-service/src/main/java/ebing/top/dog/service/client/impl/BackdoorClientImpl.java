package ebing.top.dog.service.client.impl;

import ebing.top.dog.client.BackdoorClient;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "后门模块", description = "backdoor 模块相关接口")
@Validated
@RequestMapping("/backdoor")
@RestController
@Service
public class BackdoorClientImpl implements BackdoorClient {

	@Override
	@GetMapping("/monitor")
	public String monitor() {
		return "hello world";
	}
}
