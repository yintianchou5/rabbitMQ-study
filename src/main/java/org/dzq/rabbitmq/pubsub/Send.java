package org.dzq.rabbitmq.pubsub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * ��������ģʽ
 * @author xxx-d2q
 *
 */
public class Send {
	private static final String EXCHANGE_NAME="test_exchange_fanout";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//��ȡͨ��
		Channel channel = connection.createChannel();
		//������������
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//fanout �ַ�
		String msg="hello ddd22q"; 
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		System.out.println("send msg:"+msg);
		channel.close();
		connection.close();
	}
}
