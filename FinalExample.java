/**
 * 
 */
package com.liyonghui.lock;

/**
 * @author Administrator
 *利用final来进行限制重排序，达到不改变语义的效果
 */
//fianl域是基础数据类型的情况
public class FinalExample {
	
	int i;//普通变量
	final int j;//final变量
	
	static FinalExample obj;
	
	public FinalExample(){//构造函数
		i=1;//写普通域
		j=2;//写final域
	}
	
	public static void writer() {//写程序A执行
		obj = new FinalExample();
	}
	
	public static void reader() {//读线程B执行
		FinalExample object = obj;//读线程引用
		int a = object.i;//读普通域
		int b = object.j;//读final域
	}
}


