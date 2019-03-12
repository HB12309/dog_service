package ebing.top.dog.service.client.impl;

import ebing.top.dog.client.BackdoorClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class BackdoorClientImpl implements BackdoorClient {

	@Override
	@GetMapping("/monitor")
	public String monitor() {
		return "hello world";
	}
}
