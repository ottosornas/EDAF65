package application;

import java.net.URL;
import java.util.List;

public class ThreadMain {

	private static List<URL> urls;
	
	/**
	 * Creates the threads and runs the program. Can (and should probably) be done with loops instead
	 * @param args
	 */
	
	public static void main(String[] args) {
		RunnerThread reader = new RunnerThread();
		urls = reader.readPage("http://cs229.stanford.edu/syllabus.html");
		while(urls.iterator().hasNext()){
		
			//This code is for executing RunnerInterface
				
		RunnerInterface thread1 = new RunnerInterface();
		RunnerInterface thread2 = new RunnerInterface();
		RunnerInterface thread3 = new RunnerInterface();
		RunnerInterface thread4 = new RunnerInterface();
		RunnerInterface thread5 = new RunnerInterface();
		
		Thread t1 = new Thread(thread1);	
		Thread t2 = new Thread(thread2);
		Thread t3 = new Thread(thread3);
		Thread t4 = new Thread(thread4);
		Thread t5 = new Thread(thread5);
			
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		/*	
		 	//This code is for executing RunnerThread
		 	 
		 	RunnerThread thread1 = new RunnerThread();
			RunnerThread thread2 = new RunnerThread();
			RunnerThread thread3 = new RunnerThread();
			RunnerThread thread4 = new RunnerThread();
			RunnerThread thread5 = new RunnerThread();
		
			thread1.start();
			thread2.start();
			thread3.start();
			thread4.start();
			thread5.start();
		*/
		
		}
	}
	
	/**
	 * Synchronized so only one thread at a time can execute this method
	 * @return
	 */
	public static synchronized URL getURL(){
		if(!urls.isEmpty()){
			return urls.remove(0);
		}
		return null;
	}

}
