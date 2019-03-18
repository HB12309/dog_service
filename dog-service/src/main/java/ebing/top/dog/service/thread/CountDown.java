package ebing.top.dog.service.thread;


import java.util.concurrent.CountDownLatch;

public class CountDown {

	/**
	 * 这个数字就是控制不同线程的先后顺序的。就像 火箭发射的这个步骤，一定是所有程序都检测完成之后，await 之后，才能点火
	 */
	static CountDownLatch countDownLatch = new CountDownLatch(3);


	//创建一个初始化
	public static class InitClass extends Thread {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "InitClass is Running");
			countDownLatch.countDown(); // 程序计数器-1
		}
	}

	//创建一个处理服务的
	public static class ServiceClass extends Thread {

		@Override
		public void run() {
			try {
				//此时，服务线程会等待初始化线程结束（计数器 = 0）时才会执行
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" ServiceClass is Running!");
		}
	}
}
