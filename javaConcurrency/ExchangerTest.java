/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 *线程之间交换数据的Exchanger
 *Exchanger可以用于遗传算法和校对工作
 *下面是应用于校对工作的范例
 */
public class ExchangerTest {

	/**
	 * @param args
	 */
	private static final Exchanger<String> exgr = new Exchanger<String>();
	private static ExecutorService threadpool = Executors.newFixedThreadPool(2);//启动一个拥有2个线程容量的线程池
	
	public static void main(String[] args) {
		threadpool.execute(new Runnable() {
			
			public void run() {
				String A = "银行流水A";//A录入银行流水数据
				try {
					exgr.exchange(A);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
			}
		});
		threadpool.execute(new Runnable() {
			
			public void run() {
				try {
					String B = "银行流水B";//B录入银行流水数据
					String A = exgr.exchange(B);//两个线程之间建立数据交换联系,返回值为一个字符串
					System.out.println("A和B的数据是否一致："+A.equals(B)+"A录入的数据是"+A+"B录入的数据是"+B);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				
			}
		});
		threadpool.shutdown();//关闭线程池
	}

}
