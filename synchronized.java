package com.liyonghui.lock;

public class RecorderExample {
	//未进行同步的程序测试
	
	/*
	 * 如下所示，1和2没有依赖性，如果将1和2进行重排序的话，如果在单线程里面，
	 * 那么先进行flag=true，那么因为判断3中正确，所以，执行i=a*a，此时a=0；
	 * 则多线程程序语义被破坏
	 * */
	int a = 0;
	boolean flag = false;
	public void writer() {
		a = 1;//1
		flag = true;//2
		
	}
	/*
	 * 3和4存在控制依赖，控制依赖不影响串行运行，但是在多线程程序中，会破坏多线程程序语义
	 * */
	public void reader() {
		if(flag){//3
			int i = a*a;//4
		}
	}

}
/*
 * java内存模型：happen--before和as-if-serial
 * */

//下面进行同步程序的顺序一致性效果测试
/*
 * 对方发进行加锁，即使在多线程的程序中，线程B根本无法观察到线程A在临界区的排序，
 * 通过加锁的形式，屏蔽了两个线程之间的重排序
 * 使得程序结果不改变的情况下，尽可能地为编译器和处理器的优化打开方便之门
 * */

class SynchronizedExample{
	int a = 0;
	boolean flag = false;
	public synchronized void writer() {//获取锁
		a = 1;
		flag = true;
	}//释放锁
	public synchronized void reader() {//获取锁
		if(flag){
			int i = a;
		}//释放锁
	}
}

//volatile的特性和原理测试
class VolatileFeaturesExample{
	
	volatile long v1 = 0L;
	public void set(long l) {
		v1 = l;
	}
	public void getAndIncrement() {
		v1++;
	}
	public long get(){
		
		return v1;
	}
}

//上述的使用volatle语句的方法等价于下面的
/*
 * 使用volatile定义的变量，相当于对该变量的所有操作都是原子性的，
 * 但是对于像i++这样的操作不具有原子性
 * 该变量的读和取都是具有原子性的
 * */
class VolatileFeaturesExample2{
	long v1 = 0L;
	public synchronized void set(long l) {
		v1 = l;
	}
	public void getAndIncrement() {
		long temp = get();
		temp += 1L;
		set(temp);
	}
	private synchronized long get() {
		return v1;
	}
}
