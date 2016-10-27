package com.akcomejf.java.task;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import com.akcomejf.model.Product;

/**
 * ����Task�ָ࣬�����̳�RecursiveAction�ࡣ
 * 
 * @author sunxinqi �޷���ֵ
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
	 * ʵ��compute()���� ���÷�����ʵ��������߼���
	 */
	@Override
	protected void compute() {
		// ���last��first�Ĳ�С��10������ֻ�ܸ��¼۸�С��10�Ĳ�Ʒ����ʹ��updatePrices()�������������ò�Ʒ�ļ۸�
		if (last - first < 10) {
			updatePrices();
		} else {
			// ���last��first�Ĳ���ڻ����10���򴴽������µ�Task����һ�������Ʒ��ǰ�벿�֣���һ�������Ʒ�ĺ�벿�֣�Ȼ����ForkJoinPool�У�ʹ��invokeAll()����ִ�����ǡ�
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
	 * ʵ��updatePrices()����������������²�Ʒ������λ��firstֵ��lastֵ֮��Ĳ�Ʒ��
	 */
	private void updatePrices() {
		for (int i = first; i < last; i++) {
			Product product = products.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}

}
