package com.akcomejf.java.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * 继承RecursiveTask类，并参数化为Integer类型。该类将实现统计单词在一组行中出现的次数的任务
 * 
 * @author sunxinqi 有返回值
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
		// 如果属性end和start的差小于10，那么这个任务统计单词位于行在调用processLines()方法的这些位置中出现的次数。
		if (end - start < 10) {
			result = processLines(document, start, end, word);
		} else {
			// 否则，用两个对象分解行组，创建两个新的DocumentTask对象用来处理这两个组，并且在池中使用invokeAll()方法来执行它们。
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
	 * 实现processLines()方法。它接收以下参数：字符串二维数组、start属性、end属性、任务将要查找的word属性。
	 * 
	 * @param document
	 * @param start
	 * @param end
	 * @param word
	 * @return
	 */
	private int processLines(String[][] document, int start, int end, String word) {
		// 对于任务要处理的每行，创建LineTask对象来处理整行，并且将它们存储在任务数列中。
		List<LineTask> tasks = new ArrayList<LineTask>();
		for (int i = start; i < end; i++) {
			LineTask task = new LineTask(document[i], 0, document[i].length, word);
			tasks.add(task);
		}

		// 在那个数列中使用invokeAll()执行所有任务。
		invokeAll(tasks);
		// 合计所有这些任务返回的值，并返回这个结果。
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
	 * 实现groupResults()方法。它合计两个数的值，并返回这个结果。
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
