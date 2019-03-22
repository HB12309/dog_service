package ebing.top.dog.service.client.impl;

import com.google.common.collect.ImmutableList;
import ebing.top.dog.service.thread.*;
import ebing.top.dog.service.thread.barrier.TourGuideTask;
import ebing.top.dog.service.thread.barrier.TravelTask;
import ebing.top.dog.service.utils.CommonUtils;
import ebing.top.dog.service.client.LanguageClient;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;


@Log
@Api(tags = "language模块", description = "language 模块相关接口")
@Validated
@RequestMapping("/language")
@RestController
/**
 * @author dog
 * java.util.ConcurrentModificationException
 * fail-fast，即快速失败，它是Java集合的一种错误检测机制。当多个线程对集合（非fail-safe的集合类）进行结构上的改变的操作时，有可能会产生fail-fast机制，这个时候就会抛出ConcurrentModificationException（当方法检测到对象的并发修改，但不允许这种修改时就抛出该异常）。
 *
 * 同时需要注意的是，即使不是多线程环境，如果单线程违反了规则，同样也有可能会抛出改异常。
 */
public class LanguageClientImpl implements LanguageClient {

	private static final Logger log = LoggerFactory.getLogger(LanguageClientImpl.class);

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
		if ("CyclicBarrier".equals(type)) {
			CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new TourGuideTask());
        	Executor executor = Executors.newFixedThreadPool(3);
        //登哥最大牌，到的最晚
        	executor.execute(new TravelTask(cyclicBarrier,"哈登",5));
        	executor.execute(new TravelTask(cyclicBarrier,"保罗",3));
        	executor.execute(new TravelTask(cyclicBarrier,"戈登",1));
		}
		if ("Semaphore".equals(type)) {
			final int THREAD_COUNT = 30;
			ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
			Semaphore s = new Semaphore(10);
			for (int i = 0; i < THREAD_COUNT; i++) {
				System.out.println(String.valueOf(i));
				threadPool.execute(new Employee(String.valueOf(i), s));
			}
			threadPool.shutdown();
		}
		/**
		 *获取随机数，它才棒
		 */
		if ("AtomicInteger".equals(type)) {
			AtomicInteger threadNumberAtomicInteger = new AtomicInteger(1);
			String str =  Integer.toString(ThreadLocalRandom.current().nextInt(900000) + 100000);
			Integer i2 = threadNumberAtomicInteger.get();
			Integer i1 = threadNumberAtomicInteger.addAndGet(3);
			System.out.println(i1);
			System.out.println(i2);
			System.out.println(str);

		}
		return "success";
	}

	/**
	 *
	 * @param type
	 * @return
	 * 之所以会抛出ConcurrentModificationException异常，是因为我们的代码中使用了增强for循环，而在增强for循环中，集合遍历是通过iterator进行的，但是元素的add/remove却是直接使用的集合类自己的方法。这就导致iterator在遍历的时候，会发现有一个元素在自己不知不觉的情况下就被删除/添加了，就会抛出一个异常，用来提示用户，可能发生了并发修改。
	 * 解决办法：1、1、直接使用普通for循环进行操作
	 * 2、直接使用Iterator进行操作
	 * 使用Java 8中提供的filter过滤
	 * 用过C#的表示foreach是不允许增删改元素的
	 */
	@Override
	@PostMapping("/study")
	public String study(
		@RequestParam(value = "type", required = false) String type,
		@RequestParam(value = "number", required = false) Integer number
	) {
		if ("foreach".equals(type)) {
			ArrayList<String> list = new ArrayList();
			list.add("a");
			list.add("b");
			list.add("c");
			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				String item = iterator.next();
				System.out.println(item);
			}
			// 使用ImmutableList初始化一个List
			List<String> userNames = ImmutableList.of("Hollis", "hollis", "HollisChuang", "H");

			System.out.println("使用for循环遍历List");
			for (int i = 0; i < userNames.size(); i++) {
				System.out.println(userNames.get(i));
			}

			System.out.println("使用foreach遍历List");
			for (String userName : userNames) {
				System.out.println(userName);
			}

			// 使用双括弧语法（double-brace syntax）建立并初始化一个List
			List<String> names = new ArrayList<String>() {{
				add("Hollis");
				add("hollis");
				add("HollisChuang");
				add("H");
			}};

			for (int i = 0; i < names.size(); i++) {
				if (names.get(i).equals("Hollis")) {
					names.remove(i);
				}
			}

			System.out.println(names);
		}
		if ("Random".equals(type)) {
			/**
			 * 1、java.util.Random类中实现的随机算法是伪随机，也就是有规则的随机，所谓有规则的就是在给定种(seed)的区间内随机生成数字；
			 * 2、相同种子数的Random对象，相同次数生成的随机数字是完全相同的；
			 * 3、Random类中各方法生成的随机数字都是均匀分布的，也就是说区间内部的数字生成的几率均等；
			 *
			 * 这尼玛在相同种子下的 next 的结果是一样的？算是明白了，随机一次，这和你随机给个数字有什么区别？
			 *
			 * 所以，N个 thread 对同一个字段进行操作的话，则需要 加锁、使用原子变量等等
			 * private static volatile AtomicInteger atomCounter = new AtomicInteger(0); //Java提供的int型原子变量
			 */
			Random rand = new Random(2000);
			System.out.println(rand.nextBoolean());
			System.out.println(rand.nextInt());
			System.out.println(rand.nextInt(64));
			System.out.println(rand.ints());
		}
		if ("ReentrantLock".equals(type)) {
			ReentrantLock lock = new ReentrantLock();
			try {
				lock.lock();
				log.info("已经上锁,开始对唯一资源进行并发操作，比如微信的 access token", lock);
			} finally {
				lock.unlock();
			}
//			ReentrantLockTest test1 = new ReentrantLockTest("thread1", number);
			ReentrantLockTest test1 = new ReentrantLockTest("thread2", number);
			ReentrantLockTest test2 = new ReentrantLockTest("thread2", number);
			test1.start();
			test2.start();
			try {
				test1.join();
				test2.join();
				System.out.printf("counter = %d\n", test1.getI());

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.debug(String.valueOf(test1.getI()));
			log.debug(String.valueOf(test2.getI()));
		}
		return "test";
	}
}
