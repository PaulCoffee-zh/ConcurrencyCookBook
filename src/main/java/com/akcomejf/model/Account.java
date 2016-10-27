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
	 * .ʵ��һ��addAmount()�������������ݴ���Ĳ�������balance��ֵ������Ӧ��ֻ��һ���߳��ܸı�balance��ֵ������ʹ��synchronized�ؼ��ֽ��������ת�����ٽ�����
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
	 * ʵ��һ��subtractAmount()�������������ݴ���Ĳ�������balance��ֵ������Ӧ��ֻ��һ���߳��ܸı�balance��ֵ������ʹ��synchronized�ؼ��ֽ��������ת�����ٽ���
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
