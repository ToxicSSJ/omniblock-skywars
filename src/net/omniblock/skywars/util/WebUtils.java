package net.omniblock.skywars.util;

import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils {

	public static boolean doesURLExist(URL url) {
		
		try {
			
		    HttpURLConnection.setFollowRedirects(false);
		    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		    httpURLConnection.setRequestMethod("HEAD");
		    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
		    
		    int responseCode = httpURLConnection.getResponseCode();

		    return responseCode == HttpURLConnection.HTTP_OK;
			
		} catch (Exception e) { e.printStackTrace(); }
		
		return false;
	   
	}
	
}
