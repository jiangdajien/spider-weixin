package cn.itcast.spider.huxiu.queue;

import java.util.ArrayDeque;

/**
 * 使用jdk原生的队列在多线程的情况下，会出现一个问题。 队列的元素会被重复消费。
 * 
 * @author maoxiangyi
 *
 */
public class QueueTest {

	public static void main(String[] args) {
		final ArrayDeque<String> arrayDeque = new ArrayDeque<String>();

//		ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(100);
		
		for (int i = 0; i < 100; i++) {
			arrayDeque.offer(i + "");
		}

		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						System.out.println(Thread.currentThread().getId() + " " + arrayDeque.poll());
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

	}
}
