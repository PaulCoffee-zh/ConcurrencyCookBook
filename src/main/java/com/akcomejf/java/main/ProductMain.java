package com.akcomejf.java.main;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.akcomejf.java.task.ProductTask;
import com.akcomejf.model.Product;
import com.akcomejf.model.ProductListGenerator;

public class ProductMain {
	public static void main(String[] args) {
		ProductListGenerator generator = new ProductListGenerator();
		// ʹ��ProductListGenerator�ഴ��һ������10000����Ʒ�����С�
		List<Product> products = generator.generate(1000);

		// ����һ���µ�Task�����������²�Ʒ�����еĲ�Ʒ��first����ʹ��ֵ0��last����ʹ��ֵ10000����Ʒ���еĴ�С����
		ProductTask task = new ProductTask(products, 0, products.size(), 0.20);

		// ʹ���޲ι���������ForkJoinPool����
		ForkJoinPool pool = new ForkJoinPool();

		// �ڳ���ʹ��execute()����ִ��������� ��
		pool.execute(task);
		do {
			System.out.printf("Main: Thread Count: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());

		// ʹ��shutdown()�����ر�����ء�
		pool.shutdown();

		// ʹ��isCompletedNormally()�����������������ʱû�г�������������£�д��һ����Ϣ������̨��
		if (task.isCompletedNormally()) {
			System.out.printf("Main: The process has completednormally.\n");
		}

		// ������֮�����в�Ʒ�ļ۸�Ӧ����12�����۸���12�����в�Ʒ�����ƺͼ۸�д�뵽����̨������������Ǵ�����������ǵļ۸�
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
//			if (product.getPrice() != 12) {
				System.out.printf("Product %s: %f\n", product.getName(), product.getPrice());
//			}
		}
		
		System.out.println("Main: End of the program.\n");
	}
}
