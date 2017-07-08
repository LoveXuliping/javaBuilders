/**
 * 
 */
package com.liyonghui.connectPool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Administrator
 *实现一个自定义同步组件
 *自定义的组件要继承AbstractQueuedSynchronizer类，并重写相应的方法
 *通过将自定义同步器写在Lock里面形成内部类，完成同步组件对同步器的组合
 */
public class TwinsLock implements Lock {

	/* （非 Javadoc）
	 * @see java.util.concurrent.locks.Lock#lock()
	 */
	private Sync sync = new Sync(2);
	private static final class Sync extends AbstractQueuedSynchronizer{
		//创建一个自定义的同步器Sync
		Sync(int count){
			//自定义同步器构造方法的实现
			if (count<0) {
				throw new IllegalArgumentException("count must large than zero");
				
			}
			setState(count);//设置当前同步状态，即有多少数量是同步的
		}

		@Override
		public int tryAcquireShared(int reduceCount) {
			//重写方法
			for (; ; ) {
				int current = getState();//获取当前的同步状态
				int newCount = current-reduceCount;
				if (newCount<0||compareAndSetState(current, newCount)) {//使用原子性操作，确保安全添加同步状态
					//判断是否已经没有现成可以调用，是否可以获得同步
					return newCount;
				}
				
			}
			
		}

		@Override
		public boolean tryReleaseShared(int returnCount) {
			//重写释放同步的方法，如果释放成功，则放回TRUE，否则返回FALSE
			for (; ; ) {
				//开始自旋
				int current = getState();//获取当前的同步状态
				int newCount = current+returnCount;
				if (compareAndSetState(current, newCount)) {//进行原子性保障
					return true;
				}
				
				
			}
		}
		
		
	}
	public void lock() {
		//实现锁方法
		sync.acquireShared(1);
		

	}

	/* （非 Javadoc）
	 * @see java.util.concurrent.locks.Lock#lockInterruptibly()
	 */
	public void lockInterruptibly() throws InterruptedException {
		// TODO 自动生成的方法存根

	}

	/* （非 Javadoc）
	 * @see java.util.concurrent.locks.Lock#newCondition()
	 */
	public Condition newCondition() {
		// TODO 自动生成的方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see java.util.concurrent.locks.Lock#tryLock()
	 */
	public boolean tryLock() {
		// TODO 自动生成的方法存根
		return false;
	}

	/* （非 Javadoc）
	 * @see java.util.concurrent.locks.Lock#tryLock(long, java.util.concurrent.TimeUnit)
	 */
	public boolean tryLock(long arg0, TimeUnit arg1)
			throws InterruptedException {
		// TODO 自动生成的方法存根
		return false;
	}

	/* （非 Javadoc）
	 * @see java.util.concurrent.locks.Lock#unlock()
	 */
	public void unlock() {
		// 实现解锁的方法
		sync.releaseShared(1);

	}

}
