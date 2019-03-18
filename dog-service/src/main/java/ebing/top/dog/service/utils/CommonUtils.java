package ebing.top.dog.service.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 必须是 Component 才能被注入，Spring 看是不是自己的成员
 * @Async 需要包装一下，如果直接放到 controller，则 rest 入口本身没法被注入到 Spring 中，则404，所以需要包装成一个 class
 */
@Component
public class CommonUtils {

	@Async
	public void asyncTask(String keyword) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		System.out.println(keyword);
	}
}
