package com.akcomejf.java.thread.interrupt;

/**
 * œﬂ≥Ã÷–∂œ
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
