package ebing.top.dog.service.thread;

import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录时记录登录次数和登录IP--线程池
 */
public class ExecutorServiceHelper {
	/**
	 *  获取活跃的 cpu数量
	 */
	private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
	private final static BlockingQueue<Runnable> mWorkQueue;
	private final static long KEEP_ALIVE_TIME = 3L;
	private final static TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
	private static ThreadFactory mThreadFactory;
	private static ExecutorService executorService;

	static {
		mWorkQueue = new LinkedBlockingQueue<>();
		mThreadFactory = new NamedThreadFactory();

		/**
		 maximumPoolSize 推荐取值
		 如果是 CPU 密集型任务，就需要尽量压榨CPU，参考值可以设为 NUMBER_OF_CORES + 1 或 NUMBER_OF_CORES + 2
		 */
		executorService = new ThreadPoolExecutor(
				NUMBER_OF_CORES+2,
				NUMBER_OF_CORES * 2,
				KEEP_ALIVE_TIME,
				KEEP_ALIVE_TIME_UNIT,
				mWorkQueue,
				mThreadFactory
		);
	}

	public  static  void  execute(Runnable runnable){
		if (runnable==null){
			return;
		}

		executorService.execute(runnable);
	}


	private static class NamedThreadFactory implements ThreadFactory {

		private final AtomicInteger threadNumberAtomicInteger = new AtomicInteger(1);

		@Override
		public Thread newThread(Runnable r) {
			Thread t =  new Thread(r,String.format(
					Locale.CHINA,
					"%s%d",
					"NamedThreadFactory",
					threadNumberAtomicInteger.getAndIncrement())
			);

			return t;
		}
	}
}
