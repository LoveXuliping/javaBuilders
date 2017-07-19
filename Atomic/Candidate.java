package com.liyonghui.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdateDemo {

	/**
	 * @param args
	 */
	public static class Candidate{
		int id;
		volatile int score;
	}
	public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdate = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");               
	//check
	public static AtomicInteger allScore = new AtomicInteger(0);
	public static void main(String[] args) throws InterruptedException  {
		final Candidate stu= new Candidate();
		Thread[] t = new Thread[10000];
		for (int i = 0; i <10000; i++) {
			t[i] = new Thread(){
				public void run(){
					if (Math.random()>0.4) {
						scoreUpdate.incrementAndGet(stu);
						allScore.incrementAndGet();
					}
					
				}
			};
			t[i].start();
		}
		for (int i = 0; i < 10000; i++) {
			t[i].join();//让当前主线程等待所有线程执行完毕，再往下面执行
		}
		System.out.println("score="+stu.score);
		System.out.println("allScore="+allScore);

	}

}
