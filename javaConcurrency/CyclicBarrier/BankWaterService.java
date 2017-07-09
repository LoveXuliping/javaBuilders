/**
 * 
 */
package com.liyonghi.Atomic;

import java.security.KeyStore.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.text.html.parser.Entity;

/**
 * @author Administrator
 *利用同步CyclicBarrier进行应用开发
 */
public class BankWaterService implements Runnable {

	/* （非 Javadoc）
	 * @see java.lang.Runnable#run()
	 */
	//创建4个屏障，处理完之后执行当前类的run方法
	private CyclicBarrier c = new CyclicBarrier(4, this);
	//假设只有4个sheet，所以只启动4个线程
	private Executor executor = Executors.newFixedThreadPool(4);//创建一个含有四个线程的线程池
	//保存每个sheet计算出来的结果
	private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<String, Integer>();//ConcurrentHashMap是线程安全的并发容器
	
		private void count(){
			for (int i = 0; i < 4; i++) {
				executor.execute(new Runnable() {
					
					public void run() {
						//计算当前sheet的银流数据，计算代码省略
						sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
						//银流计算完成，插入一个屏障
						try {
							c.await();
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}		
				});
				
			}
			

	}
		public void run() {
			int result = 0;
			//汇总每个sheet计算的结果
			for (java.util.Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
				result += sheet.getValue();
				
			}
			//将结果输出
			sheetBankWaterCount.put("result", result);
			System.out.println(result);
			
		}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//测试开始
		BankWaterService banWaterService = new BankWaterService();
		banWaterService.count();

	}
}
