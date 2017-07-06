package com.liyonghui.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * 使用原子操作，使用CAS操作循环实现原子操作，JDK包里提供了很多实现原子操作的方法
 * AtomicBoolean,AtomicInteger,AtomicLong等
 * */

public class Counter {
	private AtomicInteger atomicI = new AtomicInteger(0);
	private int i = 0;
	public static void main(String[] args) {
		// 开始一个基于CAS线程安全的计数器
		final Counter cas = new Counter();
		List<Thread> ts = new ArrayList<Thread>(600);
		long start = System.currentTimeMillis();
		for (int j = 0; j < 100; j++) {
			
			Thread t = new Thread(new Runnable() {
				
				public void run() {
					//开始线程匿名内部类接口的实现
					for (int i = 0; i < 10000; i++) {
						cas.count();
						cas.safeCount();
						
					}
					
				}
			});
			ts.add(t);			
		}
		for (Thread t:ts) {
			t.start();
			
		}
		//等待所有线程执行完成
		for(Thread t:ts)
			try {
				
				t.join();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println(cas.i);
		System.out.println(cas.atomicI.get());
		System.out.println(System.currentTimeMillis()-start);
	}
	protected void safeCount() {
		// 开始安全技术方法的开始
		for (; ; ) {
			
			int i = atomicI.get();
			boolean suc = atomicI.compareAndSet(i, ++i);//使用compareAndSet方法检查当前应用是否等于预期引用，并且检查当前标志是否等于预期标志
			if (suc) {//使用原子操作叠加，线程是安全的
				break;
			}
			
		}
		
		
		
		
	}
	protected void count() {
		// 不安全的技术方法的编写
		i++;//直接进行叠加，线程不安全
	}

}
