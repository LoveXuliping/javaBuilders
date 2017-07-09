/**
 * 
 */
package com.liyonghi.Atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 *对引用对象进行原子更新
 */
public class AtomiReferenceTest {

	/**
	 * @param args
	 */
	public static AtomicReference<User> atomicUserRef = new AtomicReference<User>();
	
	public static void main(String[] args) {
		User user = new User("conan",15);
		atomicUserRef.set(user);//将user对象设置进去
		User updateUser = new User("Shinichi", 17);//设置更新后的结果值
		atomicUserRef.compareAndSet(user,updateUser);//原子更新操作
		System.out.println(atomicUserRef.get().getName());//获取更新后的名字属性
		System.out.println(atomicUserRef.get().getOld());//获取更新后的年龄属性

	}



static class User{
	//内部类User
	private String name;
	private int old;
	public  User(String name,int old) {
		this.name=name;
		this.old=old;
	}
	public String getName(){
		return name;
	}
	public int getOld(){
		return old;
		}
	}
}
