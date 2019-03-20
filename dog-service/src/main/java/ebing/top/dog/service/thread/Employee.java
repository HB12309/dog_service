package ebing.top.dog.service.thread;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 1、java.util.Random类中实现的随机算法是伪随机，也就是有规则的随机，所谓有规则的就是在给定种(seed)的区间内随机生成数字；
 * 2、相同种子数的Random对象，相同次数生成的随机数字是完全相同的；
 * 3、Random类中各方法生成的随机数字都是均匀分布的，也就是说区间内部的数字生成的几率均等；
 */

public class Employee implements Runnable {

	private String id;
	private Semaphore semaphore;
	private static Random rand = new Random(47);

	public Employee(String id, Semaphore semaphore) {
		this.id = id;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		try {
			System.out.println(this.id + "准备上厕所" + "rand:" + rand);
			semaphore.acquire();
			System.out.println(this.id + "is using the toilet");
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
			semaphore.release();
			System.out.println(this.id + "is leaving");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
