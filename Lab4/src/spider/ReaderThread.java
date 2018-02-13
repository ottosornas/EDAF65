package spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ReaderThread extends Thread {
	private URLHolder holder;
	private URL url;

	public ReaderThread(URL url, URLHolder holder) {
		this.holder = holder;
		this.url = url;
	}

	public void run() {
		try {
			parse(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds two urls which both are eligible for connections and contains text
	 * 
	 * @param url,
	 *            the url which we search for links
	 * @throws IOException
	 */
	public void parse(URL url) throws IOException {
		URLConnection uc = url.openConnection();
		InputStream is;
		try {
			is = uc.getInputStream();
		} catch (Exception e) {
			return;
		}
		Document doc = Jsoup.parse(is, "UTF-8", url.getProtocol() + "://" + url.getAuthority() + "/");
		Elements links = doc.getElementsByTag("a");
		int i = 0;
		int k = 0;
		while (i < 2 && k < links.size()) {
			String linkAbsHref = links.get(k).attr("abs:href");
			if (URLHolder.parsedURL.size() == 200) {
				return;
			}
			if (linkAbsHref.startsWith("mailto")) {
				if (!URLHolder.mailAdresses.contains(linkAbsHref))
					holder.addMail(linkAbsHref);
				i++;
			} else if (linkChecker(linkAbsHref) && !URLHolder.parsedURL.contains(linkAbsHref)) {
				System.out.println("asdasds");
				holder.addURL(new URL(linkAbsHref));
				i++;
			}
			k++;
			is.close();
		}

	}

	/**
	 * Checks if the link contains text
	 * 
	 * @param s
	 *            the url in string format
	 * @return true if the url contains text
	 * @throws IOException
	 */

	private boolean linkChecker(String s) throws IOException {
		URL url;
		String type = null;
		try {
			url = new URL(s);
			URLConnection uc = url.openConnection();
			if (uc.getContentType() != null) {
				type = uc.getContentType().toLowerCase();
			}
		} catch (MalformedURLException e) {
			return false;
		}
		return (type != null) && (type.startsWith("text/html"));
	}

}
