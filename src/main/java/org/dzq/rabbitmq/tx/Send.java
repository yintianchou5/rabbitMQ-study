package org.dzq.rabbitmq.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String QUEUE_NAME="test_queue_tx";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String msg="hello tx";
		try {
			channel.txSelect();
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			channel.txCommit();
		} catch (Exception e) {
			channel.txRollback();
			System.out.println("Send msg txRollback");
		}
		System.out.println("Send msg:"+msg);
		channel.close();
		connection.close();
	}
}
