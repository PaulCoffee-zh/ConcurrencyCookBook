package com.akcomejf.java.main;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.akcomejf.java.task.FolderProcessor;

public class AnsyMain {
	public static void main(String[] args) {
		// 使用默认构造器创建ForkJoinPool。
		ForkJoinPool pool = new ForkJoinPool();

		// 创建3个FolderProcessor任务。用不同的文件夹路径初始化每个任务。
		FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
		FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
		FolderProcessor documents = new FolderProcessor("C:\\DocumentsAnd Settings", "log");

		// 在池中使用execute()方法执行这3个任务。
		pool.execute(system);
		pool.execute(apps);
		pool.execute(documents);

		// 将关于池每秒的状态信息写入到控制台，直到这3个任务完成它们的执行。
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
		} while ((!system.isDone()) || (!apps.isDone()) || (!documents.isDone()));

		// 使用shutdown()方法关闭ForkJoinPool。
		pool.shutdown();

		// 将每个任务产生的结果数量写入到控制台。
		List<String> results;
		results = system.join();
		System.out.printf("System: %d files found.\n", results.size());
		results = apps.join();
		System.out.printf("Apps: %d files found.\n", results.size());
		results = documents.join();
		System.out.printf("Documents: %d files found.\n", results.size());

	}
}
