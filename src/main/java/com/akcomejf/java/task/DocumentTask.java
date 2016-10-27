package com.akcomejf.java.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * �̳�RecursiveTask�࣬��������ΪInteger���͡����ཫʵ��ͳ�Ƶ�����һ�����г��ֵĴ���������
 * 
 * @author sunxinqi �з���ֵ
 */
public class DocumentTask extends RecursiveTask<Integer> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3738586004703813894L;

	private String document[][];
	private int start, end;
	private String word;

	public DocumentTask(String[][] document, int start, int end, String word) {
		super();
		this.document = document;
		this.start = start;
		this.end = end;
		this.word = word;
	}

	@Override
	protected Integer compute() {
		int result = 0;
		// �������end��start�Ĳ�С��10����ô�������ͳ�Ƶ���λ�����ڵ���processLines()��������Щλ���г��ֵĴ�����
		if (end - start < 10) {
			result = processLines(document, start, end, word);
		} else {
			// ��������������ֽ����飬���������µ�DocumentTask�������������������飬�����ڳ���ʹ��invokeAll()������ִ�����ǡ�
			int mid = (start + end) / 2;
			DocumentTask t1 = new DocumentTask(document, start, mid, word);
			DocumentTask t2 = new DocumentTask(document, mid, end, word);
			invokeAll(t1, t2);
			try {
				result = groupResults(t1.get(), t2.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ʵ��processLines()���������������²������ַ�����ά���顢start���ԡ�end���ԡ�����Ҫ���ҵ�word���ԡ�
	 * 
	 * @param document
	 * @param start
	 * @param end
	 * @param word
	 * @return
	 */
	private int processLines(String[][] document, int start, int end, String word) {
		// ��������Ҫ�����ÿ�У�����LineTask�������������У����ҽ����Ǵ洢�����������С�
		List<LineTask> tasks = new ArrayList<LineTask>();
		for (int i = start; i < end; i++) {
			LineTask task = new LineTask(document[i], 0, document[i].length, word);
			tasks.add(task);
		}

		// ���Ǹ�������ʹ��invokeAll()ִ����������
		invokeAll(tasks);
		// �ϼ�������Щ���񷵻ص�ֵ����������������
		int result = 0;
		for (int i = 0; i < tasks.size(); i++) {
			LineTask task = tasks.get(i);
			try {
				result = result + task.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return new Integer(result);
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

	public String[][] getDocument() {
		return document;
	}

	public void setDocument(String[][] document) {
		this.document = document;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
