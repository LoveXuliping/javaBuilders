/**
 * 
 */
package com.liyonghui.connectPool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author Administrator
 *这是连接池的主类
 */
public class ConnectionPool {
	private LinkedList<Connection> pool = new LinkedList<Connection>();//创立一个私有的连接双向队列
	public  ConnectionPool(int initialSize) {
		if (initialSize>0) {
			//通过构造函数初始化连接的最大上限
			for (int i = 0; i < initialSize; i++) {
				
				pool.addLast(ConnectionDrive.createConnection());//向队列中加入由代理生成的连接
				
			}
		}
	}
	public void releaseConnection(Connection connection) {
		//如果连接为非空
		if (connection!=null) {
			synchronized (pool) {
				//连接释放后需要进行通知，这样其他消费者才能够感知到连接池已经归还了一个连接
				pool.addLast(connection);
				pool.notifyAll();//向其他线程发出通知，告诉其他线程，在释放连接
				}
		}
	}
	//在mills内无法获取到连接，将会返回null
	public Connection fetchConnection(long mills) throws InterruptedException {
		synchronized (pool) {
			//完全超时
			if (mills<=0) {
				while (pool.isEmpty()) {
					pool.wait();
					
				}
				return pool.removeFirst();
			}else {
				long future = System.currentTimeMillis()+mills;
				long remaining = mills;
				while (pool.isEmpty()&&remaining>0) {
					pool.wait(remaining);
					remaining = future-System.currentTimeMillis();
					
				}
				Connection result = null;
				if (!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result;
			}
		}
	}
}
