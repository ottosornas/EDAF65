package application;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMain {
	
	private static List<URL> urls;
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(5);
		RunnerThread rt = new RunnerThread();
		urls = rt.readPage("http://cs229.stanford.edu/syllabus.html");
		
		for(URL url : urls){
			Runnable task = new RunnerExecutor(url);
			service.submit(task);
		}
		service.shutdown();
	}
	
}
