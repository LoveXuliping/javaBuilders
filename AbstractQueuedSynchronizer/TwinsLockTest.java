/**
 * 
 */
package com.liyonghui.connectPool;

import java.util.concurrent.locks.Lock;

import com.liyonghui.lock.SleepUtils;

/**
 * @author Administrator
 *对自定义包含自定义同步器的同步组件进行测试
 *实验结果会看到线程是成对的输出的，证明有两个线程获得了同步
 */
public class TwinsLockTest {
	//对类进行单元测试
	@test
	public void test() {
		//初始化一个自定义的同步组件
		final Lock lock = new TwinsLock();
		class Worker extends Thread{

			@Override
			public void run() {
				//重写run方法
				while (true) {
					lock.lock();//启动线程锁
					try {
						SleepUtils.second(1);//没间隔一秒输出一个线程
						System.out.println(Thread.currentThread().getName());//获取线程名称
						SleepUtils.second(1);//间隔一秒钟
					} finally{
						lock.unlock();
					}
					
					
				}
			}
			
		}
		//启动十个线程进行测试
		for (int i = 0; i < 10; i++) {
			//在同一时刻，只有两个线程可以同时获得锁
			Worker w = new Worker();
			w.setDaemon(true);//设置线程的Daemon线程
			w.start();//启动线程
			
			
		}
		//每隔一秒钟换行
		for (int i = 0; i < 10; i++) {
			SleepUtils.second(1);//间隔一秒
			System.out.println();
			
		}
		
	}
}
