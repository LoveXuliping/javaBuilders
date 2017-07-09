/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Administrator
 *对同步屏障进行测试
 *如果这里设置计数器为3的话，将永远等下去
 *当线程到达的时候调用await方法进行等待
 *因为没有第三个线程到达，所以会一直等下去
 *等到所有线程都到达屏障之后，才开始唤醒所有线程进行执行
 */
public class CyclicBarrierTest {

	/**
	 * @param args
	 */
	static CyclicBarrier c = new CyclicBarrier(2);
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			public void run() {
				try {
					c.await();
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				System.out.println(1);
				
			}
		}).start();;
		try {
			c.await();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println(2);

	}

}
