/**
 * 
 */
package com.liyonghui.lock;

import java.time.Instant;

/**
 * @author Administrator
 * 实现双重检查锁定与延迟初始化的错误方法
 * 以及正确的方法
 * 为了降低初始化类和创建对象的开销，出现了双检查锁定的延迟初始化技术
 *
 */
//在多线程里面，错误方法的演示，有可能返回的instant还没有初始化
public class UnsafeLazyInitialization {
	
	private static Instant instant;
	public static Instant getInstant(){
		if (instant == null) {//1 A线程执行
			instant = new Instant();//2 B线程执行
			
		}
		return instant;
	}

}

/*
 * 对方法进行加锁之后，对方法进行同步处理，实现线程安全的延迟初始化
 * 用synchronized进行同步
 * 
 * */
public class SafeLazyInitialization{
	
	private static Instant instant;
	public synchronized static Instant getInstant() {
		if (instant == null) {
			instant = new Instant();
		}
		return instant;
	}
}

/*
 * 进行错误的双重检查锁定初始化延迟操作
 * */
public class DoubleCheckLocking{//1
	
	private static Instant isntant;//2
	public static Instant getInstant() {//3
		if (isntant == null) {//4第一次检查
			synchronized (DoubleCheckLocking.class) {//加锁
				if (isntant == null) {//6第二次检查
					isntant = new Instant();//7 问题的根源出在这里
				}
			}
		}
		return isntant;
	}
	
}

/*
 * 基于volatile的解决方案，对变量进行volatile化
 * */
public class SafeDoubleCheckLocking{
	private volatile static Instant instant;
	public static Instant getInstant() {
		if (instant == null) {
			synchronized (SafeDoubleCheckLocking.class) {
				if (instant == null) {
					instant = new Instant();
				}
			}
			return instant;
		}
	}
	
}
