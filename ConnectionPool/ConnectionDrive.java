/**
 * 
 */
package com.liyonghui.connectPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 *通过动态代理的方法构造一个Connection
 */
public class ConnectionDrive {
	static class ConnectionHandler implements InvocationHandler{

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if (method.getName().equals("commit")) {
				TimeUnit.SECONDS.sleep(100);
			}
			return null;
		}
		
		
	}
	//创建一个Connection代理，在commit时休眠100秒
	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(ConnectionDrive.class.getClassLoader(), new Class<?>[] {Connection.class},
				new ConnectionHandler());
	}
}
