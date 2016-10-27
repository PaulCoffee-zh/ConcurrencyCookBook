package com.akcomejf.java.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.akcomejf.java.task.DocumentTask;
import com.akcomejf.model.Document;

public class MainResult {
	public static void main(String[] args) {
		// ʹ��Document�࣬����һ������100�У�ÿ��1000�����ʵ�Document��
		Document mock = new Document();
		String[][] document = mock.generateDocument(100, 1000, "the");
		DocumentTask task = new DocumentTask(document, 0, 100, "the");

		// ʹ���޲ι���������һ��ForkJoinPool�����ڳ���ʹ��execute()����ִ���������
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);
		// ʵ��һ������飬������ʾ���ڳر仯����Ϣ��ÿ�������̨д��ص�ĳЩ������ֵ��ֱ�������������ִ�С�
		do {
			System.out.printf("******************************************\n");
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
			System.out.printf("******************************************\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());
		// ʹ��shutdown()�����ر�����ء�
		pool.shutdown();

		// ʹ��awaitTermination()�����ȴ�����Ľ�����
		if(task.isCompletedNormally()){
			try {
				System.out.printf("Main: The word appears %d in the document", task.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		//��ӡ�������ĵ��г��ֵĴ��������������Ƿ���DocumentMock����д�����һ����
		try {
			System.out.printf("Main: The word appears %d in the document", task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}
}
