package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RunnerExecutor extends Thread {
	
	private URL url;
	
	public RunnerExecutor(URL url){
		this.url = url;
	}
	
	public void run(){
		try {
			download(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Downloads different pdf file
	 * 
	 * @param url
	 *            the url from what webpage the pdf is being downloaded from
	 * @param s
	 *            the path to the pdf file
	 * @throws IOException
	 */
	public synchronized void download(URL s) throws IOException {
		URL url = s;
		Path p = Paths.get(url.getPath());
		String file = p.getFileName().toString();
		System.out.println(file);
		InputStream in = url.openStream();
		FileOutputStream fos = new FileOutputStream(new File(file));
		int b;
		while ((b = in.read()) != -1) {
			fos.write(b);
		}
		in.close();
		fos.flush();
		fos.close();
	}
}
