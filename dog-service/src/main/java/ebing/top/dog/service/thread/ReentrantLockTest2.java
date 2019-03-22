package ebing.top.dog.service.thread;

public class ReentrantLockTest2 extends Thread {
	public static int i = 0;
	private Integer sum;


	/**
	 * haha, implements 没有 name ，但是 Thread 里有name，并且允许被修改
	 */
	public Integer getI() {
		return i;
	}

	public ReentrantLockTest2(String name, Integer sum) {
		this.sum = sum;
		super.setName(name);
	}

	@Override
	public void run() {
		for (int j = 0; j < sum; j++) {
			i++;
		}
		System.out.println("ReentrantLockTest2     " + i);
	}
}
