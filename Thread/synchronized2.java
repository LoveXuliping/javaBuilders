/**
 * 
 */
package com.liyonghui.Thread;

/**
 * @author Administrator
 *添加类的同步锁
 */
public class Synchronized2 implements Runnable {

	/* （非 Javadoc）
	 * @see java.lang.Runnable#run()
	 * 默认synchronized2默认无参构造方法
	 */
	static int i = 0;
	//构造类方法，对类方法进行同步加锁
	public static synchronized void increase() {
		for (int j = 0; j < 1000000; j++) {
			i++;
			
		}
	}
	public void run() {
		increase();

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Synchronized2());
		Thread thread2 = new Thread(new Synchronized2());
		thread1.start();
		thread2.start();//线程启动
		thread1.join();//让其他线程等待该线程的完成，知道完成之后，该线程会发送notifyAll信号
		thread2.join();//线程等待
		System.out.println(i);

	}

}
