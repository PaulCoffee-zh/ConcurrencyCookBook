package com.akcomejf.java.main;

import com.akcomejf.model.Account;
import com.akcomejf.model.Bank;
import com.akcomejf.model.Company;


/**
 * statement:ʹ�� synchronized�ؼ��������Ʋ������ʷ�����
 * ֻ��һ��ִ���߳̽������һ�������б�synchronized�ؼ��������ķ�����
 * �����һ���߳���ͼ����ͬһ���������κα�synchronized�ؼ��������ķ�������������ͣ��ֱ����һ���߳̽���������ִ�С�
 * @author sunxinqi
 *
 */
public class ATMMain {
	public static void main(String[] args) {
		// ����һ��Account���󣬲��ҳ�ʼ��balanceֵΪ1000��
		Account account = new Account();
		account.setBalance(1000);

		Company company = new Company(account);
		Thread companyThread = new Thread(company);

		Bank bank = new Bank(account);
		Thread bankThread = new Thread(bank);

		System.out.printf("Account : Initial Balance: %f\n", account.getBalance());

		// ������Щ�̡߳�
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
