/**
 * 
 */
package com.liyonghui.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 *对线程中断的中断标识位的测试
 */
public class Interrupted {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 不断的进行睡眠
		Thread sleepThread = new Thread(new SleepRunner(),"SleepThread");
		sleepThread.setDaemon(true);
		//busyThread不断的运行
		Thread busyThread = new Thread(new BusyRunner(),"BusyRunner");
		busyThread.setDeamon(true);
		sleepThread.start();
		busyThread.start();
		//休眠5秒，让sleepThread和busyThread充分运行
		TimeUnit.SECONDS.sleep(5);
		sleepThread.interrupt();
		busyThread.interrupt();
		System.out.println("SleepThread interrupted is"+sleepThread.isInterrupted());
		System.out.println("BusyThread interrupted is"+busyThread.isInterrupted());
		//防止sleepThread和busyThread即可退出
		SleepUtils.second(2);
		}
	static class SleepRunner implements Runnable{

		public void run() {
			while (true) {
				SleepUtils.second(10);
				
			}
		}
		
	}
	static class BusyRunner implements Runnable{

		public void run() {
			while (true) {
				
				
			}
			
		}
		
		
	}

	}
