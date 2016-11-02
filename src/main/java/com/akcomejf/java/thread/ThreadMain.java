package com.akcomejf.java.thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class ThreadMain {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Calculator cal = new Calculator(i);
			Thread thread = new Thread(cal);
			// 创建一个Thread类的对象不会创建新的执行线程。同样，调用实现Runnable接口的
			// run()方法也不会创建一个新的执行线程。只有调用start()方法才能创建一个新的执行线程。
			thread.start();
		}
		System.out.println("++++++++main thread over++++++++");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 开发一个为10个线程设置名字和优先级的程序，然后展示它们的状态信息直到线程结束。这些线程会计算数字乘法表。

		// 创建一个大小为10的Thread类的数组和一个大小为10的Thread.State数组来保存将要执行的线程和它们的状态。
		Thread[] threads = new Thread[10];
		Thread.State[] status = new Thread.State[10];
		// 创建10个Calculator类的对象，每个初始为不同的数字，然后分别用10个线程来运行它们。把其中5个的优先值设为最高，把另外5个的优先值为最低。
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread(new Calculator(i));
			if (i % 2 == 0) {
				threads[i].setPriority(Thread.MAX_PRIORITY);
			} else {
				threads[i].setPriority(Thread.MIN_PRIORITY);
			}
			threads[i].setName("Thread name " + i);
		}
		// 创建一个 PrintWriter对象用于把线程状态的改变写入文档。
		try {
			FileWriter writer = new FileWriter(".\\LeeSunThread\\log.txt");
			PrintWriter pw = new PrintWriter(writer);
			for (int i = 0; i < 10; i++) {
				pw.println("Main : Status of Thread " + i + " : " + threads[i].getState());
				status[i] = threads[i].getState();
			}
			// 开始执行这10个线程
			for (int i = 0; i < 10; i++) {
				threads[i].start();
			}

			// 直到这10个线程执行结束，我们会一直检查它们的状态。如果发现它的状态改变，就把状态记入文本
			boolean finish = false;
			while (!finish) {
				for (int i = 0; i < 10; i++) {
					if (threads[i].getState() != status[i]) {
						writeThreadInfo(pw, threads[i], status[i]);
					}
				}
				finish = true;
				for (int i = 0; i < 10; i++) {
					finish = finish && (threads[i].getState() == State.TERMINATED);
				}
			}
			pw.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("+++++++++++Main Thread Over++++++++++++++++++");
	}

	private static void writeThreadInfo(PrintWriter pw, Thread thread, State state) {
		pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
		pw.printf("Main : Priority: %d\n", thread.getPriority());
		pw.printf("Main : Old State: %s\n", state);
		pw.printf("Main : New State: %s\n", thread.getState());
		pw.printf("Main : ************************************\n");
	}
}
