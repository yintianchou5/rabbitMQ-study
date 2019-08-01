package org.dzq.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	/**
	 * ��ȡMQ����
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public static Connection getConnection() throws IOException, TimeoutException {
		//�������ӹ���
		ConnectionFactory factory=new ConnectionFactory();
		//��������ַ
		factory.setHost("127.0.0.1");
		//�˿ں�amqp 5672
		factory.setPort(5672);
		//vhost
		factory.setVirtualHost("/vhost_dzq");
		//�û���
		factory.setUsername("dzq");
		//����
		factory.setPassword("123456");
		
		return factory.newConnection();
	}
}
