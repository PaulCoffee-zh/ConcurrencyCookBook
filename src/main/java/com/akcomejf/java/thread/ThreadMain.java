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
			// ����һ��Thread��Ķ��󲻻ᴴ���µ�ִ���̡߳�ͬ��������ʵ��Runnable�ӿڵ�
			// run()����Ҳ���ᴴ��һ���µ�ִ���̡߳�ֻ�е���start()�������ܴ���һ���µ�ִ���̡߳�
			thread.start();
		}
		System.out.println("++++++++main thread over++++++++");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// ����һ��Ϊ10���߳��������ֺ����ȼ��ĳ���Ȼ��չʾ���ǵ�״̬��Ϣֱ���߳̽�������Щ�̻߳�������ֳ˷���

		// ����һ����СΪ10��Thread��������һ����СΪ10��Thread.State���������潫Ҫִ�е��̺߳����ǵ�״̬��
		Thread[] threads = new Thread[10];
		Thread.State[] status = new Thread.State[10];
		// ����10��Calculator��Ķ���ÿ����ʼΪ��ͬ�����֣�Ȼ��ֱ���10���߳����������ǡ�������5��������ֵ��Ϊ��ߣ�������5��������ֵΪ��͡�
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread(new Calculator(i));
			if (i % 2 == 0) {
				threads[i].setPriority(Thread.MAX_PRIORITY);
			} else {
				threads[i].setPriority(Thread.MIN_PRIORITY);
			}
			threads[i].setName("Thread name " + i);
		}
		// ����һ�� PrintWriter�������ڰ��߳�״̬�ĸı�д���ĵ���
		try {
			FileWriter writer = new FileWriter(".\\LeeSunThread\\log.txt");
			PrintWriter pw = new PrintWriter(writer);
			for (int i = 0; i < 10; i++) {
				pw.println("Main : Status of Thread " + i + " : " + threads[i].getState());
				status[i] = threads[i].getState();
			}
			// ��ʼִ����10���߳�
			for (int i = 0; i < 10; i++) {
				threads[i].start();
			}

			// ֱ����10���߳�ִ�н��������ǻ�һֱ������ǵ�״̬�������������״̬�ı䣬�Ͱ�״̬�����ı�
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
