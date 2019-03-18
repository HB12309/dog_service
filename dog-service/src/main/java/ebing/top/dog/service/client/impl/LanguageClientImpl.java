package ebing.top.dog.service.client.impl;

import ebing.top.dog.service.thread.Consumer;
import ebing.top.dog.service.thread.CountDown;
import ebing.top.dog.service.thread.Producer;
import ebing.top.dog.service.utils.CommonUtils;
import ebing.top.dog.service.client.LanguageClient;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;
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
		// 不要显式地创建线程，请使用线程池
		if ("BlockingQueue".equals(type)) {
			BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    		Producer producer = new Producer(queue);
    		Consumer consumer = new Consumer(queue);
    		new Thread(producer).start();
    		new Thread(consumer).start();
		}
		if ("CountDownLatch".equals(type)) {
			//创建有8个线程的线程池
			ExecutorService threadPool = Executors.newFixedThreadPool(8);
			//此时服务线程不会立刻执行，而是等待所有初始化的线程结束后才能运行（等待6个初始化线程结束）
			for (int j = 0; j < 2; j++) {
				threadPool.submit(new CountDown.ServiceClass());
			}

			//初始化线程运行
			for (int i = 0; i < 6; i++) {
				threadPool.submit(new CountDown.InitClass());
			}

			System.out.println("This is the main thread!");
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
