package org.dzq.rabbitmq.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * confirm模式下单条
 * @author xxx-d2q
 *
 */
public class Send {
private static final String QUEUE_NAME="test_queue_confirm";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.confirmSelect();//开启confirm模式
		String msg="hello confirm";
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		if(channel.waitForConfirms()) {//等待确认
			System.out.println("Send msg:"+msg);
		}else {
			System.out.println("Send msg failed");
		}
		channel.close();
		connection.close();
	}
}
