/**
 * 
 */
package com.liyonghui.Thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 *倒计时器的使用
 */
public class CountDownLatchdemo implements Runnable {

	/* （非 Javadoc）
	 * @see java.lang.Runnable#run()
	 */
	static final CountDownLatchdemo demo = new CountDownLatchdemo();//初始化一个对象
	static final CountDownLatch end = new CountDownLatch(10);//初始化有10个计时器的类对象
	
	public void run() {
		
		try {
			Thread.sleep(new Random().nextInt(10)*1000);//当前线程休眠任意时间
			System.out.println("check complete");
			end.countDown();//告诉CountDownLatch,有一个线程已经完成，计数器可以减一了
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		//写主线程函数
		ExecutorService exec = Executors.newFixedThreadPool(10);//初始化一个有10个线程的线程池
		for (int i = 0; i < 20; i++) {
			exec.submit(demo);//运行10个线程
			
		}
		//等待检查
		end.await();//主线程在等待计数器完成所有的任务
		//发射火箭
		System.out.println("Fire!");
		exec.shutdown();//关闭线程池

	}

}
