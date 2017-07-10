package com.liyonghui.Thread;

//线程安全终止的方法测试

public class ChangeObjectThread extends Thread {
	
	volatile boolean stopme = false;//使用volatile修饰，让写在读前面，形成原子锁
	
	public void stopMe() {
		stopme = true;
	}
	
	/* （非 Javadoc）
	 * @see java.lang.Thread#run()
	 * 
	 * 用这种方法退出线程，会让线程在选择一个合适的时间终止线程
	 */
	public void run() {
		//重写run方法
		while (true) {
			if (stopme) {//判断，如果调用了stopMe方法的话，跳出when循环
				System.out.println("exit by stop me");
				break;
			}
			synchronized (u) {//此处 u为一个类对象
				int v = (int)System.currentTimeMillis()/1000;
				u.setId(v);
				//Oh,do sth.else
				try {
					Thread.sleep(100);//调用线程休眠0.1秒
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				u.setName(String.valueOf(v));
				Thread.yield();
				
			}
			
		}
	}
	

}
