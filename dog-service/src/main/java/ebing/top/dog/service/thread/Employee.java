package ebing.top.dog.service.thread;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

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
			System.out.println(this.id + "准备上厕所");
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
