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

	/**
	 * Creates the pattern used for matching strings with regex and reads/downloads page
	 */
	public PDFDownloader(String url) {
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
		List<URL> urls = new ArrayList<URL>();
		
		//Find all lines with html tags, then which of them have hrefs and then check if they are pdf
        Pattern htmltag = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>");
        Pattern link = Pattern.compile("href=\\\"[^\">]*\\\">");
        Pattern pdf = Pattern.compile("(.*)\\.pdf");
			while ((line = bReader.readLine()) != null) {
				Matcher matcher = htmltag.matcher(line);
				while(matcher.find()){
					Matcher m = link.matcher(matcher.group());
					while(m.find()){
						Matcher mPDF = pdf.matcher(m.group());
						if(mPDF.find()){
							String pdfURL = mPDF.group(0).replaceAll("href=\"", "");
							urls.add(new URL(pdfURL));
						}
					}
				}
			}
		bReader.close();	
		for(URL u: urls){
			System.out.println(u.toString());
			download(u);
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
	public void download(URL url) throws IOException{
		Path p = Paths.get(url.getPath());
		String file = p.getFileName().toString();
		System.out.println(file);
		InputStream in = url.openStream();
		FileOutputStream fos = new FileOutputStream(new File(file));
		int b;
		while((b = in.read()) != -1) {
			fos.write(b);
		}
		in.close();
		fos.flush();
		fos.close();
	}
	
}
