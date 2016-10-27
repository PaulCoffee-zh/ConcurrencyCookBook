package com.akcomejf.model;

/**
 * 实现一个类来模拟ATM，它调用subtractAmount()方法来减少账户上的余额（balance值）。这个类必须实现Runnable接口，作为一个线程执行。
 * @author sunxinqi
 *
 */
public class Bank implements Runnable {

	private Account account;
	
	public Bank(Account account) {
		super();
		this.account = account;
	}

	@Override
	public void run() {
		for (int i=0; i<100; i++){
			account.subtractAmount(1000);
		}
	}

}
