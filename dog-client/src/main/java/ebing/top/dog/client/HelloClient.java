package ebing.top.dog.client

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "service-tob-problem", path = "/backdoor")
interface BackdoorClient {
	@PostMapping("/update/old_choice_questions")
	fun updateQuestionNewFieldForOldData(): List<Long>
}