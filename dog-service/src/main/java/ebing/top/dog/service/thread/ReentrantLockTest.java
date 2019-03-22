package ebing.top.dog.service.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 对于一些 for 循环里面print一些字符串，是非常慢的，影响性能。所以，生产环境，可以不要 log 就不要。
 * static 就说明，这个字段存放的内存区间不同于其他，static 关键字在不同的线程下，能够保持不变。
 */
public class ReentrantLockTest extends Thread {
	public static ReentrantLock lock = new ReentrantLock();
	public static int i = 0;
	private Integer sum;

	/**
	 * haha, implements 没有 name ，但是 Thread 里有name，并且允许被修改
	 * @param name
	 */
	public ReentrantLockTest(String name, Integer sum) {
		this.sum = sum;
		super.setName(name);
	}

	public Integer getI() {
		return i;
	}

	@Override
	public void run() {
		for (int j = 0; j < sum; j++) {
			lock.lock();
			try {
//				System.out.println(this.getName() + " " + i);
				i++;
			} finally {
				lock.unlock();
			}
		}
		System.out.println("ReentrantLockTest     " + i);
	}
}
