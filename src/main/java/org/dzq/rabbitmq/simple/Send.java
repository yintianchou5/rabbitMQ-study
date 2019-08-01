package org.dzq.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * �򵥶���
 * @author xxx-d2q
 *
 */
public class Send {
	private static final String QUEUE_NAME="test_simple_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		//��ȡͨ��
		Channel channel = connection.createChannel();
		//������������
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String msg="hello world!!!";
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		System.out.println("----send msg:"+msg);
		channel.close();
		connection.close();
	}
}
