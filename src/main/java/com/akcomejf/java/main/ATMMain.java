package com.akcomejf.java.main;

import com.akcomejf.model.Account;
import com.akcomejf.model.Bank;
import com.akcomejf.model.Company;


/**
 * statement:使用 synchronized关键字来控制并发访问方法。
 * 只有一个执行线程将会访问一个对象中被synchronized关键字声明的方法。
 * 如果另一个线程试图访问同一个对象中任何被synchronized关键字声明的方法，它将被暂停，直到第一个线程结束方法的执行。
 * @author sunxinqi
 *
 */
public class ATMMain {
	public static void main(String[] args) {
		// 创建一个Account对象，并且初始化balance值为1000。
		Account account = new Account();
		account.setBalance(1000);

		Company company = new Company(account);
		Thread companyThread = new Thread(company);

		Bank bank = new Bank(account);
		Thread bankThread = new Thread(bank);

		System.out.printf("Account : Initial Balance: %f\n", account.getBalance());

		// 启动这些线程。
		companyThread.start();
		bankThread.start();

		try {
			companyThread.join();
			bankThread.join();
			System.out.printf("Account : Final Balance: %f\n", account.getBalance());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
