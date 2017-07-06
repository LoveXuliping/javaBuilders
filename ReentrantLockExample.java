/**
 * 
 */
package com.liyonghui.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 * 借助ReenrantLock的源代码，来分析所内存语义的具体实现机制
 * ReenrantLock依赖于Java同步框架AbstractQueuedSynchronizer（即AQS）
 * 其中volatie是这个框架的关键
 *
 */
public class ReentrantLockExample {
	
	int a = 0;
	ReentrantLock lock = new ReentrantLock();//获取一个ReentrantLock对象
	
	public void writer() {
		lock.lock();//获取锁
		try {
			a++;
		} finally{
			lock.unlock();//释放锁
		}
	}
	//读操作的开始
	public void reader() {
		lock.lock();//获取锁
		try {
			int i = a;
		} finally{
			lock.unlock();//释放锁
		}
	}


//AQS的关键源码如下
	//加锁的实现过程
protected final boolean tryAcquire(int acquires){
	final Thread current = new Thread().currentThread();//获取当前的线程，并且设置为最终值
	int c = getState();//获取锁的开始，首先读volatile变量state
	if (c==0) {
		if (isFirst(current)&&compareAndSetState(0,acquires)) {
			setExclusiveOwnerThread(current);
			return true;
		}
	}
	else if (current==getExclusiveOwnerThread()) {
		int nextc = c+acquires;
		if (nextc<0) {
			throw new Error("Maximum lock count exceeded");
			setState(nextc);
			return true;
		}
		return false;
	}
	return false;
}
private void setState(int nextc) {
	// 此处省略了很多代码........
	
}
private Thread getExclusiveOwnerThread() {
	//此处省略了很多代码........
	return null;
}
private void setExclusiveOwnerThread(Thread current) {
	// 此处省略了很多代码........
	
}
private boolean compareAndSetState(int i, int acquires) {
	// 此处省略了很多代码........
	return false;
}
private boolean isFirst(Thread current) {
	// 此处省略了很多代码........
	return false;
}
private int getState() {
	//此处省略了很多代码........
	return 0;
}
}

//释放锁的实现过程
protected final boolean tryRelease(int release){
	
	int c = getState()-release;
	if (Thread.currentThread()!=getExclusiveOwnerThread()) {
		throw new IllegalMonitorStateException();
		boolean free = false;
		if (c==0) {
			free = true;
			setExclusiveOwnerThread(null);
		}
		setState(c);
		return free;
	}
}
