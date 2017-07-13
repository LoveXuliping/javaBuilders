/**
 * 
 */
package com.liyonghui.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 *对newScheduledThreadPool的穿件和三种方法的使用
 */
public class ScheduledExecutorServiceDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScheduledExecutorService  ses = Executors.newScheduledThreadPool(10);//创建一个拥有10个线程的线程池
		//如果前面的任务没有完成，则调度不会启动
		ses.scheduleAtFixedRate(new Runnable() {
			//该方法等待一个个线程的来实现，并且不断的循环
			public void run() {
				try {
					Thread.sleep(1000);//当前线程休眠1秒钟
					System.out.println(System.currentTimeMillis()/1000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}, 0, 2,TimeUnit.SECONDS);

	}

}
