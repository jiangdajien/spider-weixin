package cn.itcast.spider.huxiu.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 使用阻塞队列，避免多线程重复消费
 * 
 * @author maoxiangyi
 *
 */
public class BlockingQueueTest {

	public static void main(String[] args) throws Exception {
		final ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(1000);

		for (int i = 0; i <= 100; i++) {
			arrayBlockingQueue.put(i + "");
		}

		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						while (true) {
							String element = arrayBlockingQueue.take();
							System.out.println(Thread.currentThread().getId() + "------" + element);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}

	}
}
