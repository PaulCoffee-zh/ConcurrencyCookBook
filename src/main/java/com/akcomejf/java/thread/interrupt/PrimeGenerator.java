package com.akcomejf.java.thread.interrupt;

/**
 * 线程中断
 * 
 * @author sunxinqi
 *
 */
public class PrimeGenerator extends Thread {

	@Override
	public void run(){
		long number=1L;
		while (true) {
		     if (isPrime(number)) {
		        System.out.printf("Number %d is Prime\n",number);
		     }
		     //如果使用继承Thread 的方式，这里的判断可以使用Thread.interrupted()
		     if(isInterrupted()){
		    	 System.out.printf("The Prime Generator has been Interrupted\n");
		    	 return ;
		     }
		     number++;
		}
	}
	
	private boolean isPrime(long number){
		if(number <= 2){
			return true;
		}
		for(long i = 2; i < number ;i++){
			if(number%2 == 0){
				return false;
			}
		}
		return true;
	}
	
	
}
