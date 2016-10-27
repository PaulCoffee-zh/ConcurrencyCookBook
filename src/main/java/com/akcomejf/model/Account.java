package com.akcomejf.model;

public class Account {

	private double balance;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	/**
	 * .实现一个addAmount()方法，用来根据传入的参数增加balance的值。由于应该只有一个线程能改变balance的值，所以使用synchronized关键字将这个方法转换成临界区。
	 * @param amount
	 */
	public  synchronized void addAmount(double amount){
		double temp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp += amount;
		balance = temp;
	}
	
	/**
	 * 实现一个subtractAmount()方法，用来根据传入的参数减少balance的值。由于应该只有一个线程能改变balance的值，所以使用synchronized关键字将这个方法转换成临界区
	 * @param amount
	 */
	public  synchronized void subtractAmount(double amount){
		double temp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp -= amount;
		balance = temp;
	}
}
