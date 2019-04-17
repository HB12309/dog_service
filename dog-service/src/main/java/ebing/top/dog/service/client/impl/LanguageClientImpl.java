package ebing.top.dog.service.client.impl;

import com.google.common.collect.ImmutableList;
import ebing.top.dog.service.clazz.Apple;
import ebing.top.dog.service.clazz.UserSerializable;
import ebing.top.dog.service.thread.*;
import ebing.top.dog.service.thread.barrier.TourGuideTask;
import ebing.top.dog.service.thread.barrier.TravelTask;
import ebing.top.dog.service.utils.CommonUtils;
import ebing.top.dog.service.client.LanguageClient;
import ebing.top.dog.service.utils.FileTGZUtil;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.base.ObjectUtil;
import org.springside.modules.utils.base.PropertiesUtil;
import org.springside.modules.utils.collection.MapUtil;

/**
 * import static静态导入是JDK1.5中的新特性。一般我们导入一个类都用 import com.....ClassName;而静态导入是这样：import static com.....ClassName.*;这里的多了个static，还有就是类名ClassName后面多了个 .* ，意思是导入这个类里的静态方法。当然，也可以只导入某个静态方法，只要把 .* 换成静态方法名就行了。然后在这个类中，就可以直接用方法名调用静态方法，而不必用ClassName.方法名 的方式来调用。
 *
 * 这种方法的好处就是可以简化一些操作，例如打印操作System.out.println(...);就可以将其写入一个静态方法print(...)，在使用时直接print(...)就可以了。
 */
import static org.assertj.core.api.Assertions.*;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
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
		/**
		 * 自动装箱 boxing unboxing
		 * 手动装箱 Integer i = new Integer(10); 当然了，Integer.valueOf 这种，还会先判断cache -127 ~ 128
		 * 自动拆箱
		 * 所以，结果就是：由于使用了三目运算符，并且第二、第三位操作数分别是基本类型和对象。所以对对象进行拆箱操作，由于该对象为null，所以在拆箱过程中调用null.booleanValue()的时候就报了NPE。
		 *
		 * 五、问题解决
		 * 如果代码这么写，就不会报错：
		 *
		 * Map<String,Boolean> map =  new HashMap<String, Boolean>();
		 * Boolean b = (map!=null ? map.get("test") : Boolean.FALSE);
		 * 就是保证了三目运算符的第二第三位操作数都为对象类型。
		 */
		if ("boxing".equals(type)) {
			Integer i =10;
			Integer a = Integer.valueOf(1111);
			int b = i;
			Map<String,Boolean> map =  new HashMap<String, Boolean>(8);
			Boolean c = (map!=null ? map.get("test") : false);
			System.out.println("c    " + c);
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
		@RequestParam(value = "string", required = false) String string,
		@RequestParam(value = "number", required = false) Integer number,
		@RequestParam(value = "double1", required = false) Double double1
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
		/**
		 * StringJoiner 和 StringBuilder 差不多，但这是 java.util 提供的，自己人嘛
		 * 参考：https://www.hollischuang.com/archives/3283
		 */
		if ("StringJoiner".equals(type)) {
			StringJoiner sj = new StringJoiner("我");
			sj.add("love");
			sj.add("Java干货");
			System.out.println(sj.toString());
			List<String> list = ImmutableList.of("wo","love","Java干货");
			String result = list.stream().collect(Collectors.joining(":"));
			System.out.println("result   " + result);
		}
		if ("LocalDateTime".equals(type)) {
			LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
			System.out.println("LocalDateTime    " + now);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
			System.out.println("Calendar == " + sdf.format(Calendar.getInstance().getTime()));
		}
		if ("SimperDateFormat".equals(type)) {
			//定义一个线程安全的HashSet
			Set<String> dates = Collections.synchronizedSet(new HashSet<String>());
			for (int i = 0; i < 100; i++) {
				//获取当前时间
				Calendar calendar = Calendar.getInstance();
				System.out.println("calendar     " + calendar);
			}
		}
		/**
		 * Error:(267, 68) java: 未报告的异常错误java.lang.NoSuchMethodException; 必须对其进行捕获或声明以便抛出
		 * 所以 Java 是有些异常一定是要你捕获的，不像 kotlin 有 ?. let
		 * 这里用到了 reflect, 所以是 new 出来的对象要追溯它的模子，就用反射，比如 capacity 这种字段
		 * 在 同一种 JDK 中 ，hashCode 函数，数字的是本身，String 和 Object 长度一样，并且是数字，并且同一个值的 hashCode 一定是唯一的。
		 * 为了保证哈希的结果可以分散、为了提高哈希的效率，JDK在一个小小的hash方法上就有很多考虑，做了很多事情。
		 */
		if ("HashMap".equals(type)) {
			Map<String, String> map = new HashMap<String, String>(1);
			map.put("hahaha", "hollischuang");
			Integer i = 333;
			String str = "1222rew";
			System.out.println(i.hashCode());
			System.out.println(str.hashCode());
			System.out.println(map.hashCode());
			Class<?> mapType = map.getClass();
			try {
				Method capacity = mapType.getDeclaredMethod("capacity");
				capacity.setAccessible(true);
				System.out.println("capacity : " + capacity.invoke(map));
			} catch (Exception e) {
				e.printStackTrace();
			}
			/**
			 * map 居然可以使用 null 值作为 key 和 value 就是 KV 嘛
			 */
			Map map2 = new HashMap(1);
			map2.put(null, 1);
			Object a = map2.get(null);
			Object b = map2.get(1);
			System.out.println("a     " + a);
			System.out.println("b     " + b);

			Apple a1 = new Apple("green");
			Apple a2 = new Apple("green");
			Apple a3 = new Apple("red");

			//hashMap stores apple type and its quantity
			HashMap<Apple, Integer> m = new HashMap<Apple, Integer>();
			m.put(a1, 10);
			m.put(a3, 20);
			System.out.println("a1    " + m.get(a1));
			System.out.println("a2     " + m.get(a2));
		}
		if ("Serializable".equals(type)) {
			//Initializes The Object
			UserSerializable user = new UserSerializable();
			user.setName("hollis");
			user.setGender("male");
			user.setAge(23);
			user.setBirthday(new Date());
			System.out.println("user1   " + user);

			//Write Obj to File
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
				oos.writeObject(user);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(oos);
			}

			//Read Obj from File
			File file = new File("tempFile");
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				UserSerializable newUser = (UserSerializable) ois.readObject();
				System.out.println(newUser);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(ois);
				try {
					FileUtils.forceDelete(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if ("BigDecimal".equals(type)) {
			BigDecimal bd = new BigDecimal(double1);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			System.out.println("bd   " + bd);
		}
		/**
		 * 第一层括弧 实际是定义了一个内部匿名类 （Anonymous Inner Class），第二层括弧 实际上是一个实例初始化块 （instance initializer block），这个块在内部匿名类构造时被执行。这个块之所以被叫做“实例初始化块”是因为它们被定义在了一个类的实例范围内。这和“静态初始化块 （static initialzer）”不同，因为这种块在定义时在括弧前使用了static关键字，因此它的和类在同一个范围内的，也就是说当类加载时就会被执行（更详情，可参考Java语言规范http://java.sun.com/docs/books/jls/third_edition/html/classes.html#8.6 ）。实例初始化块中可以使用其容器范围内的所有方法及变量，但特别需要注意的是实例初始化块是在构造器之前运行的。
		 *
		 * 在Kotlin 中就可以使用 hashSetOf<String>("a", "b") 这种 xxxOf 的方法来初始化容器，装了东西的容器噢
		 */
		if ("HashSet".equals(type)) {
			HashSet a = (new HashSet() {{
				add("XZ13s");
				add("AB21/X");
				add("YYLEX");
				add("AR5E");
			}});
			// iterator 一个游标，迭代器
			System.out.println("a   " + a.iterator());
		}
		/**
		 * log 结果如下：
		 * url     file:/Users/lx/Documents/java/dog_service/dog-service/out/production/classes/static/1.zip
		 * cpr     static/1.zip
		 * file.getAbsolutePath()     /Users/lx/Documents/java/dog_service/dog-service/out/production/classes/static/1.zip
		 * file.getPath();     /Users/lx/Documents/java/dog_service/dog-service/out/production/classes/static/1.zip
		 * file.getCanonicalPath();     /Users/lx/Documents/java/dog_service/dog-service/out/production/classes/static/1.zip
		 * destPath     /Users/lx/Documents/java/dog_service/dog-service/out/production/classes/static/
		 * resutls     [管理平台/, 管理平台/problem_question.json, __MACOSX/, __MACOSX/管理平台/, __MACOSX/管理平台/._problem_question.json, 管理平台/课程地图.png, __MACOSX/管理平台/._课程地图.png, 管理平台/B端地图.png,]
		 */
		if ("unCompress".equals(type)) {
			try {
				URL url = this.getClass().getClassLoader().getResource(string);
				File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + string);
				// 这里可以得到文件夹 不一定要有文件噢，就不用那样裁剪了
				File file2 = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX);
				ClassPathResource cpr = new ClassPathResource("static/1.zip");
				System.out.println("file2.getAbsolutePath();     " + file2.getAbsolutePath());
				System.out.println("url     " + url);
				System.out.println("cpr     " + cpr.getPath());
				System.out.println("file.getAbsolutePath()     " + file.getAbsolutePath());
				System.out.println("file.getPath();     " + file.getPath());
				System.out.println("file.getCanonicalPath();     " + file.getCanonicalPath());

				String destPath = file.getPath().substring(0, file.getPath().length() - file.getName().length());
				System.out.println("destPath     " + destPath);
				List<String> resutls = FileTGZUtil.unCompress(
						file.getAbsolutePath(),
						destPath
				);
				System.out.println("resutls     " + resutls);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("properties".equals(type)) {
			/**
			 * java.lang.AssertionError: should fail before
			 */
			fail("should fail before");
			Properties p1 = PropertiesUtil.loadFromFile("classpath://application.properties");
			System.out.println("p1     " + p1);
			int a = PropertiesUtil.getInt(p1, "springside.min", 0);
			System.out.println("a     " + a);
		}
		if ("treeMap".equals(type)) {
			TreeMap<Integer, String> map = MapUtil.newSortedMap();
			map.put(3, "xxx");
			map.put(2, "xxx2");
			map.put(6, "xxx3");
			map.put(1, "xxx4");
			System.out.println("map     " + map.keySet());
			HashMap<Integer, String> map2 = MapUtil.newHashMapWithCapacity(4, 0.5f);
			map2.put(3, "xxx");
			map2.put(2, "xxx2");
			map2.put(6, "xxx3");
			map2.put(1, "xxx4");
			System.out.println("map2     " + map2.keySet());
			short[] array = new short[] { 1, 2 };
			System.out.println("array     " + array);
			String arrayString = ObjectUtil.toPrettyString(array);
			System.out.println("arrayString     " + arrayString);

		}
		return "test";
	}
}
