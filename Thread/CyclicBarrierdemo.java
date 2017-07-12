/**
 * 
 */
package com.liyonghui.Thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Administrator
 *循环栅栏CyclicBarrier测试
 */
public class CyclicBarrierdemo {

	/**
	 * @param args
	 */
	public static class Soldier implements Runnable{
		private String solider;
		private final CyclicBarrier cyclic;//创建一个final对象，构造方法不能重拍，且在读操作之前初始化
		//准备构造方法
		public  Soldier(String solider,CyclicBarrier cyclic) {
			this.solider = solider;
			this.cyclic = cyclic;
		}
		public void run() {
			try {
				//等待所有士兵到齐
				cyclic.await();
				doWork();//士兵开始干活
				//等待所有士兵完成工作
				cyclic.await();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		private void doWork() {
			//在当前类中穿件士兵工作的方法
			try {
				Thread.sleep(Math.abs(new Random().nextInt()%1000));
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}//当前线程休眠任意时间
			System.out.println(solider+"任务完成");
			
		}
		
	}
	public static class BarrierRun implements Runnable{
		boolean flag;
		int N;
		public  BarrierRun(boolean flag,int N) {
			this.flag = flag;
			this.N = N;
		}
		public void run() {
			if (flag) {
				System.out.println("司令：【士兵】"+N+"个，任务完成！");
			}else {
				System.out.println("司令：【士兵】"+N+"个，集合完毕！");
				flag = true;
			}
			
		}
		
	}
	public static void main(String[] args) {
		//测试主函数开始
		final int N = 10;
		Thread[] allSoldier = new Thread[N];//创建一个线程数组
		boolean flag = false;
		CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
		//设置屏障点，主要是为了执行这个方法
		System.out.println("集合队伍！");
		for (int i = 0; i < 10; i++) {
			System.out.println("士兵"+i+"报道");
			allSoldier[i] = new Thread(new Soldier("士兵"+i, cyclic));
			allSoldier[i].start();//启动线程
			
			
		}
	}

}
