package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFDownloader {

	private Pattern pattern;

	/**
	 * Creates the pattern used for matching strings with regex and reads/downloads page
	 */
	public PDFDownloader(String url) {
		pattern = Pattern.compile("<a\\s*?.*?\\s*?href=\"(.*?\\.pdf)\"\\s*?.*?\\s*?>(.*?)</a>"); //denna är fel, men vet ej hur jag ska förbättra
		readPage(url);
	}

	/**
	 * Downloads and represents the webpage as a string object
	 * @param url the webpage being read
	 */
	public void readPage(String url){
	try {
		URL myDoc = new URL(url);
		InputStream is = myDoc.openStream();
		BufferedReader bReader =
		new BufferedReader(new InputStreamReader(is));
		String line;
		List<String> urls = new ArrayList<String>();
		Matcher matcher;
			while ((line = bReader.readLine()) != null) {
				matcher = pattern.matcher(line);
				while(matcher.find()){
					urls.add(matcher.group());
				}
			}
		bReader.close();	
		for(String s: urls){
			download(myDoc, s);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	

	/**
	 * Downloads different pdf file
	 * @param url the url from what webpage the pdf is being downloaded from
	 * @param s the path to the pdf file
	 * @throws IOException
	 */
	public void download(URL url, String s) throws IOException{
		Path p = Paths.get(url.getPath());
		String file = p.getFileName().toString();
		System.out.println(file);
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] contentInBytes = s.getBytes();	//är väldigt osäker på hur man borde göra här
		fos.write(contentInBytes);
		fos.flush();
		fos.close();
	}
	
}
