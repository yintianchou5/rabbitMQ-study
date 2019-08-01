package org.dzq.rabbitmq.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import org.dzq.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
/**
 * confirm模式下的异步模式
 * @author xxx-d2q
 *
 */
public class Send3 {
private static final String QUEUE_NAME="test_queue_confirm3";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.confirmSelect();
		String msg="hello confirm";
		
		//未确定的消息标识
		SortedSet<Long> confirmSet=Collections.synchronizedSortedSet(new TreeSet<Long>());
		
		channel.addConfirmListener(new ConfirmListener() {
			
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) {
					System.out.println("---handleNack----multiple");
					confirmSet.headSet(deliveryTag+1).clear();
				}else {
					System.out.println("---handleNack----multiple   false");
					confirmSet.remove(deliveryTag);
				}
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) {
					System.out.println("---handleAck----multiple");
					confirmSet.headSet(deliveryTag+1).clear();
				}else {
					System.out.println("---handleAck----multiple   false");
					confirmSet.remove(deliveryTag);
				}
				
			}
		});
		while(true) {
			long seqNo = channel.getNextPublishSeqNo();
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			confirmSet.add(seqNo);
		}
	}
}
