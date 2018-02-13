package spider;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class URLHolder {

	public static Set<String> parsedURL;
	public static Set<URL> remainingURL;
	public static ArrayList<String> mailAdresses;

	public URLHolder() {
		parsedURL = new HashSet<>();
		remainingURL = new HashSet<>();
		mailAdresses = new ArrayList<>();
	}

	/**
	 * Adds an url to the list which is to be searched through
	 * 
	 * @param url
	 */
	public synchronized void addURL(URL url) {
		remainingURL.add(url);
		notifyAll();
	}

	/**
	 * Adds an email address to the result list, if we find one
	 * 
	 * @param mail
	 */
	public synchronized void addMail(String mail) {
		mailAdresses.add(mail);
	}

	/**
	 * After we have searched through an url, we add it to this list in order to
	 * avoid to search through the same website twice
	 * 
	 * @param url
	 */
	private void addParsed(String url) {
		parsedURL.add(url);
	}

	/**
	 * Removes and returns the first object in the list of URL's waiting to be
	 * searched through
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized URL removeURL() throws InterruptedException {
		if (remainingURL.isEmpty()) {
			wait();
		}
		Iterator<URL> iter = remainingURL.iterator();

		URL u = iter.next();
		addParsed(u.toString());
		remainingURL.remove(u);
		return u;
	}

}
