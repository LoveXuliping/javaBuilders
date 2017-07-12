/**
 * 
 */
package com.liyonghui.Thread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Administrator
 *读写锁的测试，读写锁在读操作的时候是并行运行的
 *在写操作的时候是阻塞的
 */
public class ReadWriteLockDemo {

	/**
	 * @param args
	 * 构造读写锁的方法
	 */
	private static Lock lock = new ReentrantLock();//实例化一个重入锁对象
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//构造一个无参数的重入读写锁
	private static Lock readLock = readWriteLock.readLock();//构造一个读操作的锁
	private static Lock writeLock = readWriteLock.writeLock();//构造一个写操作的锁
	private int vaule;
	
	//程序的方法
	public int handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);//当前线程休眠一秒钟，读操作的耗时越多，读写锁的优势越明显
			return vaule;
		}finally{
			lock.unlock();//释放锁
		}
	}
	//程序的写方法
	public void handleWriteLock(Lock lock,int index) throws InterruptedException {
		try {
			lock.lock();//模拟写操作
			Thread.sleep(1000);//当前线程休眠一秒钟
			vaule = index;
		} finally{
			lock.unlock();
		}
		}
		
	public static void main(String[] args) {
		//开始测试主函数
		final ReadWriteLockDemo demo = new ReadWriteLockDemo();//初始化类对象
		Runnable readRunnable = new Runnable() {
			//构造匿名内部类
			public void run() {
				try {
					demo.handleRead(readLock);
					//demo.handleRead(lock);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
			}
		};
		//构造匿名内部类
		Runnable writeRunnble = new Runnable() {
			
			public void run() {
				try {
					demo.handleWriteLock(writeLock, new Random().nextInt());
					//demo.handleWriteLock(lock, new Random().nextInt());
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			
				
			}
		};
		for (int i = 0; i < 18; i++) {
			//构造18个线程出来
			new Thread(readRunnable).start();//开始读的线程
			
		}
		for (int i = 18; i < 20; i++) {
			new Thread(writeRunnble).start();
			
		}
	}

}
