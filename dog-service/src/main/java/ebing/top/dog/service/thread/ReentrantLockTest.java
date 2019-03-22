package ebing.top.dog.service.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest extends Thread {
	public static ReentrantLock lock = new ReentrantLock();
	public static int i = 0;

	/**
	 * haha, implements 没有 name ，但是 Thread 里有name，并且允许被修改
	 * @param name
	 */
	public ReentrantLockTest(String name) {
		super.setName(name);
	}

	public Integer getI() {
		return i;
	}

	@Override
	public void run() {
		for (int j = 0; j < 5000000; j++) {
			lock.lock();
			try {
//				System.out.println(this.getName() + " " + i);
				i++;
			} finally {
				lock.unlock();
			}
		}
	}
}
