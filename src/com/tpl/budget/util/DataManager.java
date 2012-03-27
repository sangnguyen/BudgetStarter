package com.tpl.budget.util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import com.tpl.budget.util.XmlParser;
import android.util.Log;
public class DataManager {

	/**
	 * Generic getting REST request from server.
	 * 
	 * @param urlString
	 * @return
	 * @throws IOException
	 */		
	public  ArrayList<ResourceMoneyArticle> getResourceMoneyArticles(String url){
		InputStream is = null;
		try {			
			is = getData(url);
			XmlParser xp = new XmlParser();
			ArrayList<ResourceMoneyArticle> list = xp.parse(is);			
			assert list != null;
			return list;
		} catch (IOException e) {
			return null;
		
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					Log.e("bugget",
							"Error closing network stream to " + url);
				}
		}
	}		
	public InputStream  getData(String url) throws ClientProtocolException, IOException {
		try{
			 URL feedUrl = new URL(url);
			 return feedUrl.openConnection().getInputStream();
		}catch(Exception ex)
		{
			return null;
		}
	}
}
