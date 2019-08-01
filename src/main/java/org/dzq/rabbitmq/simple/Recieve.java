package org.dzq.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class Recieve {
	
	private static final String QUEUE_NAME="test_simple_queue";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//创建通道
		Channel channel = connection.createChannel();
		//队列声明
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("new api recieve msg:"+msg);
			}
		};//监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

	private static void oldApi() throws IOException, TimeoutException, InterruptedException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//创建通道
		Channel channel = connection.createChannel();
		//定义队列的消费者    老的API
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//监听队列
		channel.basicConsume(QUEUE_NAME,true,consumer);
		while(true) {
			Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("recieve msg:"+msg);
		}
	}
}
