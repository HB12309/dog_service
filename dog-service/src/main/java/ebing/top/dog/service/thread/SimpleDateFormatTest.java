package ebing.top.dog.service.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;

public class SimpleDateFormatTest {
	/**
	 * 定义一个全局的SimpleDateFormat
	 */
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 使用ThreadFactoryBuilder定义一个线程池
	 */
	private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

	/**
	 * 必须是 public 并且没有 static 的 field 才能在外界被引用，所以可以考虑写个 getter
	 */
	private ExecutorService pool = new ThreadPoolExecutor(
			5,
			200,
			0L,
			TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(1024),
			namedThreadFactory,
			new ThreadPoolExecutor.AbortPolicy()
	);

	public ExecutorService getPoor() {
		return this.pool;
	}

	/**
	 * 定义一个CountDownLatch，保证所有子线程执行完之后主线程再执行
	 */
	private static CountDownLatch countDownLatch = new CountDownLatch(100);
}
