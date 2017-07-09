/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 *利用CountDownLatch工具
 *CountDownLatche的wait方法会阻止线程，直到计数器编程0
 */
public class CountDownLatchTest {

	/**
	 * @param args
	 */
	//实例化一个CountDownLatch类对象
	static CountDownLatch c = new CountDownLatch(2);//计数器为2
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			//放在同一个线程里面执行
			public void run() {
				System.out.println(1);
				c.countDown();
				System.out.println(2);
				c.countDown();
				
				
			}
		}).start();
		c.await();//等待线程的完成
		System.out.println(3);
	}

}
