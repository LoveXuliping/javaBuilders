/**
 * 
 */
package com.liyonghui.Thread;

/**
 * @author Administrator
 *完成同步锁的创建，正确的创建方法，
 * 创建一个对象同步锁
 */
public class AccountingSyn implements Runnable{

	/**
	 * @param args
	 */
	
	static AccountingSyn instance = new AccountingSyn();//获取一个对象，后来用此对象来进行同步锁加固
	static int i = 0;
	
	/* （非 Javadoc）
	 * @see java.lang.Thread#run()
	 * 重写继承线程的run方法
	 * 或者改为public synchronized void run(){}
	 */
	public void run() {
		for (int j = 0; j < 1000000; j++) {
			//对当前的对象进行线程同步操作
			synchronized (instance) {
				i++;
			}
			
		}
	}

	public static void main(String[] args) throws InterruptedException {
		//获取同一个对象的runnable线程对象
		Thread thread1 = new Thread(instance);
		Thread thread2 = new Thread(instance);
		thread1.start();
		thread2.start();
		thread1.join();//进行线程的等待,确保两个循坏执行完之后，主线程输出的方法才开始进行
		thread2.join();
		System.out.println(i);

	}

}
