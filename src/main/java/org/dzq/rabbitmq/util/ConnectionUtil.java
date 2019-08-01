package org.dzq.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	/**
	 * 获取MQ连接
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public static Connection getConnection() throws IOException, TimeoutException {
		//定义连接工厂
		ConnectionFactory factory=new ConnectionFactory();
		//服务器地址
		factory.setHost("127.0.0.1");
		//端口号amqp 5672
		factory.setPort(5672);
		//vhost
		factory.setVirtualHost("/vhost_dzq");
		//用户名
		factory.setUsername("dzq");
		//密码
		factory.setPassword("123456");
		
		return factory.newConnection();
	}
}
