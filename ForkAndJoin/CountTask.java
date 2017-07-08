/**
 * 
 */
package com.liyonghi.Lock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author Administrator
 *对fork/join框架的测试
 */
public class CountTask extends RecursiveTask<Integer> {
	private static final int THRESHOLD = 2;//阈值
	private int start;
	private int end;
	public CountTask(int start,int end) {
		//构造方法
		this.start = start;
		this.end = end;
		
	}
	@Override
	protected Integer compute() {
		//重写compute方法
		int sum = 0;
		//如果任务足够小就计算任务
		boolean canCompute = (end-start) <= THRESHOLD;
		//如果任务小于等于阈值,就直接运算
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
				
			}
		}else{
			//如果任务大于阈值，就分裂成两个子任务计算
			int middle = (start+end)/2;
			CountTask lefTask = new CountTask(start, middle);
			CountTask rightTask = new CountTask(middle+1, end);
			//执行子任务
			lefTask.fork();
			rightTask.fork();
			//等待子任务完成，并得到其结果
			int leftResult = lefTask.join();
			int rightResult = rightTask.join();
			//合并子任务
			sum = leftResult+rightResult;
			
		}
		return sum;
			
		}
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		//生成一个计算任务，负责计算1+2+3+4
		CountTask task = new CountTask(1,4);
		//执行一个计算任务
		Future<Integer> result = forkJoinPool.submit(task);
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	}
