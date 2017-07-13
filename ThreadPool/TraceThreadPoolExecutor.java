/**
 * 
 */
package com.liyonghui.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.omg.PortableServer.ThreadPolicyValue;

/**
 * @author Administrator
 *
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor{

	public TraceThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue) {
		super(maximumPoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	/* （非 Javadoc）
	 * @see java.util.concurrent.ThreadPoolExecutor#execute(java.lang.Runnable)
	 */
	@Override
	public void execute(Runnable task) {
		super.execute(wrap(task,clientTrace(),Thread.currentThread().getName()));
	}
	public Future<?> submit(Runnable task){
		return super.submit(wrap(task,clientTrace(),Thread.currentThread().getName()));
		
	}
	private Runnable wrap(final Runnable task, final Exception clientTrace, String clientThreadName) {
		return new Runnable() {
			//匿名内部类，要把task改为终态
			public void run() {
				try {
					task.run();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					clientTrace.printStackTrace();
					e.printStackTrace();
				}
				
			}
		};
	}

	private Exception clientTrace() {
		return new Exception("Client stack trace");
	}
	
}
