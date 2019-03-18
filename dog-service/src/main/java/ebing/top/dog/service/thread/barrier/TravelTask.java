package ebing.top.dog.service.thread.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 到达旅游点，统一发放护照，开始旅游
 */
public class TravelTask implements Runnable {
	private CyclicBarrier cyclicBarrier;
	private String name;
	private int arriveTime; //赶到的时间

	public TravelTask(CyclicBarrier cyclicBarrier,String name,int arriveTime){
		this.cyclicBarrier = cyclicBarrier;
		this.name = name;
		this.arriveTime = arriveTime;
	}

	@Override
	public void run() {
		try {
			//模拟达到需要花的时间
			Thread.sleep(arriveTime * 1000);
			System.out.println(name +"到达集合点");
			cyclicBarrier.await();
			System.out.println(name +"开始旅行啦～～");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
