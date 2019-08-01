package org.dzq.rabbitmq.tx;

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
import com.rabbitmq.client.ShutdownSignalException;

public class Recieve {
	private static final String QUEUE_NAME="test_queue_tx";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//创建通道
		Channel channel = connection.createChannel();
		//队列声明
		boolean durable=false;
		channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("[tx] recieve msg:"+new String(body,"utf-8"));
			}
		};
		//监听队列
		boolean autoAsk=true;//设置自动应答为false
		channel.basicConsume(QUEUE_NAME, autoAsk, consumer);
	}
}
