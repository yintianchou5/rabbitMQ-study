package org.dzq.rabbitmq.fair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * 公平分发
 * 需要关闭自动应答Ack
 * @author xxx-d2q
 *
 */
public class Send {
	private static final String QUEUE_NAME="test_work_queue";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//获取通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/**
		 * 每个消费者发送确认消息之前，消息队列不发送下一个消息给消费者，一次只处理一条消息
		 * 限制发送给同一个消费者，不得超过y一条消息
		 */
		int prefetchCount=1;
		channel.basicQos(prefetchCount);
		
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
