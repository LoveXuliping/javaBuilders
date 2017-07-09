/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author Administrator
 *原子更新字段类
 */
public class AtomicIntegerFiledaterTest {

	/**
	 * @param args
	 */
	//创建原子更新器，，并设置需要更新的对象类和对象属性
	private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class, "old");
	public static void main(String[] args) {
		//设置年龄为10岁
		User conan = new User("conan", 10);
		//柯南长了一岁，但是仍然会输出旧的年龄
		System.out.println(a.getAndIncrement(conan));//对年龄进行更新。获取更新之前的年龄
		//输出柯南现在的年龄
		System.out.println(a.get(conan));//获取更新之后的年龄

	}
	static class User{
		private String name;
		private volatile int old;//要更新的字段必须是volatile类型修饰的字段
		public  User(String name,int old) {
			this.name=name;
			this.old=old;
		}
		public String getName() {
			return name;
		}
		public int getOld() {
			return old;
		}
		
	}

}
