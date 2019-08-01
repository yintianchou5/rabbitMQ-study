package org.dzq.rabbitmq.pubsub;

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

public class Recieve2 {
	private static final String QUEUE_NAME="test_queue_fanout_sms";
	private static final String EXCHANGE_NAME="test_exchange_fanout";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//创建通道
		Channel channel = connection.createChannel();
		//队列声明
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		//绑定队列到exchange上
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		
		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("[2]recieve msg:"+msg);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					System.out.println("[2] done msg");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
				
			}
		};
		//监听队列
		boolean autoAsk=false;//设置自动应答为false
		channel.basicConsume(QUEUE_NAME, autoAsk, consumer);
	}
}
