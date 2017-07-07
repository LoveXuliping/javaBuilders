/**
 * 
 */
package com.liyonghui.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 *对ThreadLocal进行测试
 *ThreadLocal即线程变量,是一种以ThreadLocal为键，以任意对象为值的存储结构
 *这个结构被附带在线程上
 */
public class Profile {

	/**
	 * @param args
	 */
	//第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
		
		protected Long initialVaule() {//当调用get方法的时候，如果没有调用set方法的话，这个方法会被初始化调用
			return System.currentTimeMillis();
		}
	};
	
	public static final void begin() {
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}
	
	public static final Long end(){
		return System.currentTimeMillis()-TIME_THREADLOCAL.get();
	}
	public static void main(String[] args) throws InterruptedException {
		
		Profile.begin();//调用get方法，把当前时间绑定在当前线程上
		TimeUnit.SECONDS.sleep(1);//线程进行休眠一毫秒
		System.out.println("Cost:"+Profile.end()+"mills");//调用get方法获得时间差

	}

}
