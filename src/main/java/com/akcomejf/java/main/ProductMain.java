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
		// 使用ProductListGenerator类创建一个包括10000个产品的数列。
		List<Product> products = generator.generate(1000);

		// 创建一个新的Task对象，用来更新产品队列中的产品。first参数使用值0，last参数使用值10000（产品数列的大小）。
		ProductTask task = new ProductTask(products, 0, products.size(), 0.20);

		// 使用无参构造器创建ForkJoinPool对象。
		ForkJoinPool pool = new ForkJoinPool();

		// 在池中使用execute()方法执行这个任务 。
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

		// 使用shutdown()方法关闭这个池。
		pool.shutdown();

		// 使用isCompletedNormally()方法检查假设任务完成时没有出错，在这种情况下，写入一条信息到控制台。
		if (task.isCompletedNormally()) {
			System.out.printf("Main: The process has completednormally.\n");
		}

		// 在增长之后，所有产品的价格应该是12。将价格不是12的所有产品的名称和价格写入到控制台，用来检查它们错误地增长它们的价格。
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
//			if (product.getPrice() != 12) {
				System.out.printf("Product %s: %f\n", product.getName(), product.getPrice());
//			}
		}
		
		System.out.println("Main: End of the program.\n");
	}
}
