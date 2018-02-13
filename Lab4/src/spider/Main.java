package spider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	private static URLHolder holder;

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		holder = new URLHolder();
		ExecutorService service = Executors.newFixedThreadPool(10);
		holder.addURL(new URL("http://cs.lth.se/edaf65/"));
		holder.addURL(new URL("https://en.wikipedia.org/wiki/Main_Page"));
		holder.addURL(new URL("https://docs.oracle.com/javase/8/docs/api/overview-summary.html"));

		while (URLHolder.parsedURL.size() < 200) {
			ReaderThread task = new ReaderThread(holder.removeURL(), holder);
			service.submit(task);
			System.out.println("Parsed size: " + URLHolder.parsedURL.size());
		}

		System.out.println("***** PARSED URLS *****");
		for (String u : URLHolder.parsedURL) {
			System.out.println(u);
		}
		System.out.println("***** EMAILS *****");
		for (String s : URLHolder.mailAdresses) {
			System.out.println(s);
		}
		System.exit(0);

	}

}
