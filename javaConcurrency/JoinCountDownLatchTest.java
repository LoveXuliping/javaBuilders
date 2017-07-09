/**
 * 
 */
package com.liyonghi.Atomic;

/**
 * @author Administrator
 *利用join实现主线程等待所有子线程完成之后再执行的程序的程序
 *join方法等待前面的线程完成之后才可以执行从join方法中返回，
 *如果前置线程还存活，则永远等下去，调用wait等待
 *如果前置线程终止了，则调用notifyAll通知所有线程进入同步队列
 */
public class JoinCountDownLatchTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread parse1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO 自动生成的方法存根
				
			}
		});
		Thread parse2 = new Thread(new Runnable() {
			
			public void run() {
				System.out.println("parser2 finish");
				
			}
		});
		parse1.start();
		parse2.start();
		parse1.join();
		parse2.join();
		System.out.println("all parser finish");
	}

}
