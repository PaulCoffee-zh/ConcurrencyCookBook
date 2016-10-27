package com.akcomejf.java.main;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.akcomejf.java.task.FolderProcessor;

public class AnsyMain {
	public static void main(String[] args) {
		// ʹ��Ĭ�Ϲ���������ForkJoinPool��
		ForkJoinPool pool = new ForkJoinPool();

		// ����3��FolderProcessor�����ò�ͬ���ļ���·����ʼ��ÿ������
		FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
		FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
		FolderProcessor documents = new FolderProcessor("C:\\DocumentsAnd Settings", "log");

		// �ڳ���ʹ��execute()����ִ����3������
		pool.execute(system);
		pool.execute(apps);
		pool.execute(documents);

		// �����ڳ�ÿ���״̬��Ϣд�뵽����̨��ֱ����3������������ǵ�ִ�С�
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

		// ʹ��shutdown()�����ر�ForkJoinPool��
		pool.shutdown();

		// ��ÿ����������Ľ������д�뵽����̨��
		List<String> results;
		results = system.join();
		System.out.printf("System: %d files found.\n", results.size());
		results = apps.join();
		System.out.printf("Apps: %d files found.\n", results.size());
		results = documents.join();
		System.out.printf("Documents: %d files found.\n", results.size());

	}
}
