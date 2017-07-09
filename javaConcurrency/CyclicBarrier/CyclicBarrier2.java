/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Administrator
 *对带有两个参数的CyclicBarrier进行测试
 *测试中，等到所有线程都到达屏障之后，先运行参数的线程，然后再运行在屏障中的所有线程
 *如果有屏障没有到达的话，就一直等下去
 */
public class CyclicBarrierTest2 {

	/**
	 * @param args
	 */
	static CyclicBarrier c = new CyclicBarrier(2,new A());
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
		}).start();
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
	static class A implements Runnable{

		public void run() {
			System.out.println(3);
			
		}
		
	}

}
