package com.tpl.budget.util;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
/**
 * @author ntr9h
 *
 */
/**
 * @author ntr9h
 *
 */
/**
 * @author ntr9h
 * 
 */
final class XmlParser {	
	public ArrayList<ResourceMoneyArticle> parse(InputStream is){
		try {
			XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
			is.skip(3); // Remove the BOM
			xpp.setInput(new InputStreamReader(is));
			return parse(xpp);
		} catch (XmlPullParserException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}	
	public ArrayList<ResourceMoneyArticle> parse(XmlPullParser xpp)  {
		ArrayList<ResourceMoneyArticle> list = new ArrayList<ResourceMoneyArticle>();
		try {
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tagName = xpp.getName();
					
					if (tagName.equals("entry")) {
						
						list.add(createProgramme(xpp));
					} 
				}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
			return list;
		} catch (IOException e) {
			return null;
		}
		return list;
	}
	private ResourceMoneyArticle createProgramme(XmlPullParser xpp)
	{
		ResourceMoneyArticle item = new ResourceMoneyArticle();		
		try{
			int eventType = xpp.next();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tagName = xpp.getName();
					if (tagName.equals("id")) {
						item.setId(xpp.nextText());
					} else if (tagName.equals("title")) {
						item.setTitle( xpp.nextText());
					} else if (tagName.equals("name")) {
						item.setAuthor(xpp.nextText());
					} else if (tagName.equals("link")) {
						item.setLink(xpp.getAttributeValue(null, "href"));
					} else if (tagName.equals("updated")) {
						item.setDate(xpp.nextText());
					} else if (tagName.equals("summary")) {
						item.setDes(xpp.nextText());
					} 
				} else if (eventType == XmlPullParser.END_TAG) {
					String tagName = xpp.getName();
					if (tagName.equals("entry"))
						break;
				}
				eventType = xpp.next();
			}
		}catch(Exception ex)
		{
			return null;
		}
		return item;
	}	
}
