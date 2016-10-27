package com.akcomejf.java.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 创建FolderProcessor类，指定它继承RecursiveTask类，并参数化为List<String>类型
 * 
 * @author sunxinqi
 *
 */
public class FolderProcessor extends RecursiveTask<List<String>> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7686731072858309994L;

	private String path;

	private String extension;

	public FolderProcessor(String path, String extension) {
		super();
		this.path = path;
		this.extension = extension;
	}

	@Override
	protected List<String> compute() {
		List<String> list = new ArrayList<>();
		List<FolderProcessor> tasks = new ArrayList<>();
		File file = new File(path);
		File content[] = file.listFiles();
		if (content != null) {
			// 如果是子文件夹，则创建一个新的FolderProcessor对象，并使用fork()方法异步地执行它。
			for (int i = 0; i < content.length; i++) {
				if (content[i].isDirectory()) {
					FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
					task.fork();
					tasks.add(task);
				} else {
					// 否则，使用checkFile()方法比较这个文件的扩展名和你想要查找的扩展名，如果它们相等，在前面声明的字符串数列中存储这个文件的全路径
					if (checkFile(content[i].getName())) {
						list.add(content[i].getAbsolutePath());
					}
				}
				// 如果FolderProcessor子任务的数列超过50个元素，写入一条信息到控制台表明这种情况。
				if (tasks.size() > 50) {
					System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
				}
				// 调用辅助方法addResultsFromTask()，将由这个任务发起的子任务返回的结果添加到文件数列中。传入参数：字符串数列和FolderProcessor子任务数列。
				addResultsFromTasks(list, tasks);
			}
		}
		return list;
	}

	/**
	 * 实现addResultsFromTasks()方法。对于保存在tasks数列中的每个任务，
	 * 调用join()方法，这将等待任务执行的完成，并且返回任务的结果。使用addAll()方法将这个结果添加到字符串数列。
	 * 
	 * @param list
	 * @param tasks
	 */
	private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
		for (FolderProcessor item : tasks) {
			list.addAll(item.join());
		}
	}

	/**
	 * 实现checkFile()方法。这个方法将比较传入参数的文件名的结束扩展是否是你想要查找的。
	 * 如果是，这个方法返回true，否则，返回false。
	 * @param name
	 * @return
	 */
	private boolean checkFile(String name) {
		return name.endsWith(extension);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
