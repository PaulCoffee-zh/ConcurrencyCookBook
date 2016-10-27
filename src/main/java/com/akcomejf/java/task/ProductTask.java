package com.akcomejf.java.task;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import com.akcomejf.model.Product;

/**
 * 创建Task类，指定它继承RecursiveAction类。
 * 
 * @author sunxinqi 无返回值
 */
public class ProductTask extends RecursiveAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6313768402539191346L;

	private List<Product> products;

	private int first;
	private int last;
	private double increment;

	/**
	 * 实现compute()方法 ，该方法将实现任务的逻辑。
	 */
	@Override
	protected void compute() {
		// 如果last和first的差小于10（任务只能更新价格小于10的产品），使用updatePrices()方法递增的设置产品的价格。
		if (last - first < 10) {
			updatePrices();
		} else {
			// 如果last和first的差大于或等于10，则创建两个新的Task对象，一个处理产品的前半部分，另一个处理产品的后半部分，然后在ForkJoinPool中，使用invokeAll()方法执行它们。
			int middle = (last + first) / 2;
			System.out.printf("Task: Pending tasks:%s\n", getQueuedTaskCount());
			ProductTask t1 = new ProductTask(products, first, middle + 1, increment);
			ProductTask t2 = new ProductTask(products, middle + 1, last, increment);
			invokeAll(t1, t2);
		}
	}

	public ProductTask(List<Product> products, int first, int last, double increment) {
		super();
		this.products = products;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}

	/**
	 * 实现updatePrices()方法。这个方法更新产品队列中位于first值和last值之间的产品。
	 */
	private void updatePrices() {
		for (int i = first; i < last; i++) {
			Product product = products.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}

}
