/**
 * 
 */
package com.liyonghui.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author Administrator
 *对线程数据传输工具通道的测试
 *管道输入和管道输出
 */
public class Piped {

	/**
	 * @param args
	 * 类里面只可以定义成员或者方法，不可以调用方法
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		PipedWriter out = new PipedWriter();
		PipedReader in = new PipedReader();
		//将输入流和输出流进行连接，否则在使用时会抛出IOException异常
		out.connect(in);
		Thread printThread = new Thread(new Print(in), "PrintThread");
		printThread.start();//开启输入管道的读操作
		int receive = 0;
		try{
			//下面是输出管道的写入操作
		while ((receive = System.in.read())!=-1) {//预示着输入输出管道也是一种系统流操作
			out.write(receive);}
			
		}finally{
			out.close();
		}

	}
	//编写线程参数的runnable方法，该方法是输入管道的读操作
	static class Print implements Runnable{
		
		private PipedReader in;
		public  Print(PipedReader in) {
			this.in = in;
		}

		public void run() {
			int receive = 0;
			try {
				while ((receive=in.read())!=-1) {
					System.out.println((char)receive);
					
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
	}

}
