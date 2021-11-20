package org.osrs.loader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarInputStream;

public class JarDownloader {
	public boolean downloaded = false;
	public int jarHashcode = -1;
	public JarDownloader(String jarLocation){
		long start = System.currentTimeMillis();
		System.out.println("[ - Client Downloader - ]");
		try{
			if(!jarLocation.equals("")){
				System.out.println("JAR Location : "+jarLocation);
				JarInputStream stream = new JarInputStream(new URL(jarLocation).openStream());
	            jarHashcode = stream.getManifest().hashCode();
	            stream.close();
				System.out.println("Downloading runescape client... ("+jarHashcode+")");
				if(downloadFile(jarLocation)){
					System.out.println("Succesfully downloaded client in : "+(System.currentTimeMillis()-start)+"ms");
					downloaded=true;
				}
				else
					System.out.println("Failed to download client.");
			}
			else
				System.out.println("Invalid JAR Location!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private boolean downloadFile(final String link) {
		try {
			URL url = new URL(link);
			String referer = url.toExternalForm();
            URLConnection uc = url.openConnection();
            uc.addRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
            uc.addRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            uc.addRequestProperty("Accept-Encoding", "gzip,deflate");
            uc.addRequestProperty("Accept-Language", "en-gb,en;q=0.5");
            uc.addRequestProperty("Connection", "keep-alive");
            uc.addRequestProperty("Host", "oldschool.runescape.com");
            uc.addRequestProperty("Keep-Alive", "300");
            if (referer != null)
                uc.addRequestProperty("Referer", referer);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");		

			BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
			FileOutputStream fos = new FileOutputStream("runescape.jar");
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int x = 0;
			long total=0;
			int lastKB=0;
			//r186 2078kb total
			while((x=in.read(data, 0, 1024))>=0) {
				bout.write(data, 0, x);
				total+=x;
				if(lastKB<total/1024){
					lastKB=(int) (total/1024);
					//System.out.println("Downloaded "+lastKB+"kb");
				}
			}
			bout.close();
			in.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
