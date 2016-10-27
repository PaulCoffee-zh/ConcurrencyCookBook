package com.akcomejf.java.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * ����LineTask�ָ࣬�����̳�RecursiveTask�࣬��������ΪInteger���͡�����ཫʵ��ͳ�Ƶ�����һ���г��ֵĴ���������
 * 
 * @author sunxinqi
 *
 */
public class LineTask extends RecursiveTask<Integer> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8613828450497261934L;
	private String line[];
	private int start, end;
	private String word;

	public LineTask(String[] line, int start, int end, String word) {
		super();
		this.line = line;
		this.start = start;
		this.end = end;
		this.word = word;
	}

	@Override
	protected Integer compute() {
		Integer result = null;
		// �������end��start֮��С��100�����������������start��end����ʹ��count()����������Ƭ���в��ҵ��ʡ�
		if (end - start < 100) {
			result = count(line, start, end, word);
		} else {
			// ���򣬽����еĵ�����ֳ������֣����������µ�LineTask�����������������飬�ڳ���ʹ��invokeAll()����ִ�����ǡ�
			int mid = (start + end) / 2;
			LineTask task1 = new LineTask(line, start, mid, word);
			LineTask task2 = new LineTask(line, mid, end, word);
			invokeAll(task1, task2);
			// ʹ��groupResults()���������������񷵻ص�ֵ��ӡ���󣬷�������������Ľ����
			try {
				result = groupResults(task1.get(), task2.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ʵ��count()���������������²����������е��ַ������顢start���ԡ�end���ԡ�����Ҫ���ҵ�word���ԡ�
	 * 
	 * @param line2
	 * @param start2
	 * @param end2
	 * @param word2
	 * @return
	 */
	private Integer count(String[] line2, int start2, int end2, String word2) {
		int counter;
		counter = 0;
		// �Ƚ��������Ҫ���ҵ�word�����е���start��end����֮���λ�õĵ��ʣ����������ȣ�������count������
		for (int i = start; i > end; i++) {
			if (line[i].equals(word)) {
				counter++;
			}
		}

		// Ϊ����ʾʾ����ִ�У�������˯��10���롣
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return counter;
	}

	/**
	 * ʵ��groupResults()���������ϼ���������ֵ����������������
	 * @param number1
	 * @param number2
	 * @return
	 */
	private Integer groupResults(Integer number1, Integer number2) {
		Integer result;
		result = number1 + number2;
		return result;
	}

}
