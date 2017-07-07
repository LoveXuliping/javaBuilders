/**
 * 
 */
package com.liyonghui.lock;

/**
 * @author Administrator
 *测试Daemon线程的代码，当Java虚拟机退出时，Daemon线程的finally方法不一定会执行
 */
public class Daemon {

	/**
	 * @param args
	 * 代码中没有任何的输出，证明当虚拟机退出的时候
	 * Daemon线程马上终止，不会输出finally的内容
	 */
	public static void main(String[] args) {
		// 测试方法的入口
		Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
		thread.setDaemon(true);
		thread.start();

	}
	
	static class DaemonRunner implements Runnable{

		public void run() {
			//实现run方法
			try{SleepUtils.second(10);}
			finally{
				/*证明不能依靠finally来确保执行的关闭和清除资源的逻辑*/
			System.out.println("DaemonThread finally run.");}
			
		}
		
		
	}

}
