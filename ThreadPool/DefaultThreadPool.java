/**
 * 
 */
package com.liyonghui.connectPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Administrator
 *
 */

/*从线程的实现中可以看出，当客户端调用execute方法的时候
 * 会不断的想任务列表中添加Job,而每个工作者线程会不断地从jobs上取
 * 出一个Job进行执行，当jobs为空时候，工作者线程进入等待状态
 * 
 * */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job>{
	//线程池最大限制数
	private static final int MAX_WORKER_NUMBERS = 10;
	//线程池默认的数量
	private static final int DEFAULT_WORKERS_NUMBERS = 5;
	//线程池最小的数量
	private static final int MIN_WORKERS_NUMBERS = 1;
	//这是一个工作列表，将会向里面插入工作
	private final LinkedList<Job> jobs = new LinkedList<Job>();//这是一个双向列表，用于对线程的取得和释放
	//工作者列表
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
	//工作者线程的数量
	private int workerNum = DEFAULT_WORKERS_NUMBERS;
	//线程编号生成
	private AtomicLong threadNum = new AtomicLong();//使用原子操作变量，实行线程安全操作
	
	//构造方法
	public DefaultThreadPool() {
		initializeWorkers(DEFAULT_WORKERS_NUMBERS);
	}
	
	

	//重载构造方法
	public DefaultThreadPool(int num) {
		workerNum = num>MAX_WORKER_NUMBERS?MAX_WORKER_NUMBERS:num<MIN_WORKERS_NUMBERS?MIN_WORKERS_NUMBERS:num;
		initializeWorkers(workerNum);
	}

	public void execute(Job job) {
		if (job!=null) {
			//添加一个工作，然后进行通知
			synchronized (jobs) {
				jobs.addLast(job);
				jobs.notify();
			}
		}
		
	}

	public void shutdown() {
		for (Worker worker:workers) {
			
			worker.shutdown();
			
		}
		
	}

	public void addWorkers(int num) {
		
		synchronized (jobs) {
			//限制新增的Worker数量不能超过最大值
			if (num+this.workerNum>MAX_WORKER_NUMBERS) {
				num = MAX_WORKER_NUMBERS-this.workerNum;
			}
			initializeWorkers(num);
			this.workerNum += num;
		}
		
	}

	public void removeWorkers(int num) {
		
		synchronized (jobs) {
			if (num>=this.workerNum) {
				throw new IllegalArgumentException("beyond workNum");
			}
			//按照给定的数量停止Worker
			int count = 0;
			while (count<num) {
				Worker worker = workers.get(count);
				if (workers.remove(worker)) {
					worker.shutdown();
					count++;
				}
				
			}
			this.workerNum -= count;
		}
		
	}

	public int getJobSize() {
		return jobs.size();
	}
	
	private void initializeWorkers(int num) {
		for (int i = 0; i < num; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			Thread thread = new Thread(worker, "ThreadPool-Worker-"+threadNum.incrementAndGet());
			thread.start();
			
		}
		
	}
	
	//工作者，负责消费任务
	class Worker implements Runnable{
		//是否工作
		private volatile boolean running = true;//使用volatile定义，线程安全，相当于对变量的读和写都加锁
		public void run() {
			while (running) {
				Job job = null;
				synchronized (jobs) {
					//如果工作者列表为空，那么久wait
					while (jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException e) {
							//感知到外部WorkerThread的中断操作，返回
							Thread.currentThread().interrupt();
							return;
						}
						
					}
					//取出一个Job
					job = jobs.remove();
				}
				if (job!=null) {
					try {
						job.run();
					} catch (Exception e) {
						// 忽略Job执行中的异常
					}
				}
				
			}
			
		}
		public void shutdown() {
			running = false;
		}
	}

}
