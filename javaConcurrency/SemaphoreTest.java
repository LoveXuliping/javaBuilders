/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Administrator
 *控制并发线程数的Semaphore
 *可以应用于Java程序中的流量控制领域
 *比如写入数据库或者读取数据库，拥有一定量的连接数的
 *
 */
public class SemaphoreTest {

	/**
	 * @param args
	 */
	private static final int THREAD_COUNT=30;
	private static ExecutorService threadpool = Executors.newFixedThreadPool(THREAD_COUNT);//建立一个拥有30个线程容量的线程池
	private static Semaphore s = new Semaphore(10);//创建一个可以容纳10个线程并发的Semophore
	public static void main(String[] args) {
		for (int i = 0; i < 30; i++) {
			threadpool.execute(new Runnable() {
				
				public void run() {
					try {
						s.acquire();
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}//申请一个Semaphore许可证
					System.out.println("save data");
					s.release();//归还许可证
					
				}
			});
		}
		
		threadpool.shutdown();//关闭线程池
	}

}
