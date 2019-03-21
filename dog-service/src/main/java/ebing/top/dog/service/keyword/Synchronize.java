package ebing.top.dog.service.keyword;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过volatile修饰的变量可以保证线程之间的可见性，但并不能保证这3个指令的原子执行，在多线程并发执行下，无法做到线程安全，
 * 在add方法加上synchronized修饰解决。但是性能上差了点
 *
 */
public class Synchronize {
	private volatile int n;

	public synchronized void add() {
		n++;
	}
}
