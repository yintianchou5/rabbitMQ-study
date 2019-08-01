package org.dzq.rabbitmq.fair;

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
private static final String QUEUE_NAME="test_work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//��ȡ����
		Connection connection = ConnectionUtil.getConnection();
		//����ͨ��
		Channel channel = connection.createChannel();
		//��������
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		
		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("[1]recieve msg:"+msg);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					System.out.println("[1] done msg");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
				
			}
		};
		//��������
		boolean autoAsk=false;//�����Զ�Ӧ��Ϊfalse
		channel.basicConsume(QUEUE_NAME, autoAsk, consumer);
	}
}