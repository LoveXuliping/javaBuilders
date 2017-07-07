/**
 * 
 */
package com.liyonghui.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 *测试线程的JOIN方法
 */
public class Join {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Thread previous = Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			
			//每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
			Thread thread = new Thread(new Domino(previous), String.valueOf(i));
			thread.start();
			previous = thread;//不断循环的把当前线程赋值给previous，使得每个线程都在等待前一个线程完成
			
		}
		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.currentThread().getName()+"terminate.");

	}
	static class Domino implements Runnable {
		private Thread thread;
		public  Domino(Thread thread) {
			this.thread = thread;
		}
		@SuppressWarnings("static-access")
		public void run() {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			System.out.println(thread.currentThread().getName()+"terminate.");
		}
	}

}
