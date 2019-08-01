package org.dzq.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * ��������
 * @author xxx-d2q
 *
 */
public class Send {
	private static final String QUEUE_NAME="test_work_queue";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//��ȡͨ��
		Channel channel = connection.createChannel();
		//������������
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for(int i=0;i<50;i++) {
			String msg="hello world!!!"+i;
			System.out.println("send msg:"+msg);
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(i*20);
		}
		channel.close();
		connection.close();
	}
}
