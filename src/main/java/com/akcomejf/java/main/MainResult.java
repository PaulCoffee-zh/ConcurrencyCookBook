package com.akcomejf.java.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.akcomejf.java.task.DocumentTask;
import com.akcomejf.model.Document;

public class MainResult {
	public static void main(String[] args) {
		// 使用Document类，创建一个带有100行，每行1000个单词的Document。
		Document mock = new Document();
		String[][] document = mock.generateDocument(100, 1000, "the");
		DocumentTask task = new DocumentTask(document, 0, 100, "the");

		// 使用无参构造器创建一个ForkJoinPool对象，在池中使用execute()方法执行这个任务。
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);
		// 实现一个代码块，用来显示关于池变化的信息。每秒向控制台写入池的某些参数的值，直到任务完成它的执行。
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
		// 使用shutdown()方法关闭这个池。
		pool.shutdown();

		// 使用awaitTermination()方法等待任务的结束。
		if(task.isCompletedNormally()){
			try {
				System.out.printf("Main: The word appears %d in the document", task.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		//打印单词在文档中出现的次数。检查这个数是否与DocumentMock类中写入的数一样。
		try {
			System.out.printf("Main: The word appears %d in the document", task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}
}
