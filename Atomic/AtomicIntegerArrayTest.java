/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author Administrator
 *数组原子操作范例
 */
public class AtomicIntegerArrayTest {

	/**
	 * @param args
	 */
	static int[] value = new int[] {1,2};
	static AtomicIntegerArray ai = new AtomicIntegerArray(value);//初始化一个原子数组
	
	public static void main(String[] args) {
		//进行测试
		ai.getAndSet(0, 3);
		System.out.println(ai.get(0));
		System.out.println(value[0]);
		/*
		 * 注意，数组通过构造函数传递进去，然后AtomicIntegerArray会复制一份数组，
		 * 所以当AtomicIntegerArray对内部数组进行修改时候，不改变原来数组
		 * */
	}

}
