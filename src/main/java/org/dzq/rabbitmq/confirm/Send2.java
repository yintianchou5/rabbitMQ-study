package org.dzq.rabbitmq.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * confirm模式下多条
 * @author xxx-d2q
 *
 */
public class Send2 {
private static final String QUEUE_NAME="test_queue_confirm2";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.confirmSelect();
		String msg="hello confirm";
		
		for(int i=0;i<10;i++) {
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		}
		if(channel.waitForConfirms()) {
			System.out.println("Send msg:"+msg);
		}else {
			System.out.println("Send msg failed");
		}
		channel.close();
		connection.close();
	}
}
