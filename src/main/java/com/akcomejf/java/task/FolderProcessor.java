package com.akcomejf.java.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * ����FolderProcessor�ָ࣬�����̳�RecursiveTask�࣬��������ΪList<String>����
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
			// ��������ļ��У��򴴽�һ���µ�FolderProcessor���󣬲�ʹ��fork()�����첽��ִ������
			for (int i = 0; i < content.length; i++) {
				if (content[i].isDirectory()) {
					FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
					task.fork();
					tasks.add(task);
				} else {
					// ����ʹ��checkFile()�����Ƚ�����ļ�����չ��������Ҫ���ҵ���չ�������������ȣ���ǰ���������ַ��������д洢����ļ���ȫ·��
					if (checkFile(content[i].getName())) {
						list.add(content[i].getAbsolutePath());
					}
				}
				// ���FolderProcessor����������г���50��Ԫ�أ�д��һ����Ϣ������̨�������������
				if (tasks.size() > 50) {
					System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
				}
				// ���ø�������addResultsFromTask()���������������������񷵻صĽ����ӵ��ļ������С�����������ַ������к�FolderProcessor���������С�
				addResultsFromTasks(list, tasks);
			}
		}
		return list;
	}

	/**
	 * ʵ��addResultsFromTasks()���������ڱ�����tasks�����е�ÿ������
	 * ����join()�������⽫�ȴ�����ִ�е���ɣ����ҷ�������Ľ����ʹ��addAll()��������������ӵ��ַ������С�
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
	 * ʵ��checkFile()����������������Ƚϴ���������ļ����Ľ�����չ�Ƿ�������Ҫ���ҵġ�
	 * ����ǣ������������true�����򣬷���false��
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
