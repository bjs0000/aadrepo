package com.example.homework311bjs0000;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.format.Time;
import android.util.Log;


public class ArticleDataProvider {
	
    private static final String XML_TITLE = "title";
    private static final String XML_CONTENT = "content";
    
	private Context context;
	ArticleDataProvider(Context c) {
		context = c;
	}
    String getArticles() {
    	BufferedReader reader = null;
    	String result = "";
    	StringBuilder builder = null;
    	try {
    		AssetManager manager = context.getAssets();
    		 
    		InputStream in = manager.open("hrd314_data.xml");
    		// context.openFileInput(R.raw.hrd314_data);
    		reader = new BufferedReader(new InputStreamReader(in));
    		builder = new StringBuilder();
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			builder.append(line);
    		}
    		
    	} catch (FileNotFoundException e) {
    		Log.d("FP", "ArticleDataProvider threw FileNotFoundExcepton" );
    	} catch (IOException e) {
    		Log.d("FP", "ArticleDataProvider threw IOExcepton" ); 		
    	} finally {
    		try {
    			Log.d("FP", "CLOSING READER");
    			if (reader != null ) reader.close();
    		} catch (IOException e) {
        		Log.d("FP", "ArticleDataProvider threw IOExcepton closing reader" );        		
        	}
    	}
    	
    	if (builder != null) result = builder.toString();
//    	Log.d("FP", "*****************************\n" + result + "\n****************************");
    	return result;
    }
	public void fetchItems() {
	        
	        try {
	            String xmlString = getArticles();
	          //  Log.i("FP", "Received xml: " + xmlString);
	            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	            XmlPullParser parser = factory.newPullParser();
	            parser.setInput(new StringReader(xmlString));
	            
	            parseItems(parser);
	        } catch (IOException ioe) {
	            Log.e("FP", "Failed to fetch items", ioe);
	        } catch (XmlPullParserException xppe) {
	            Log.e("FP", "Failed to parse items", xppe);
	        }
	        
	}

	void parseItems(XmlPullParser parser) throws XmlPullParserException, IOException {
			Log.d("FP", "PARSE ITEMS");
	        int eventType = parser.next();
	        ArticleListSingleton singleton = ArticleListSingleton.getInstance(context);
	        String title = "";
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	        	
	            if (eventType == XmlPullParser.START_TAG
	            		&& XML_TITLE.equals(parser.getName())
	                ) {
	            	parser.next();
	            	  Log.d("FP", "START TAG");
//	            	   will want this for the real feed
//	                String icon = parser.getAttributeValue(null, "icon");
//	                content = parser.getAttributeValue(null, "content");
//	                String date = parser.getAttributeValue(null, "date");
	                 title =   parser.getText();
//	                 Log.d("FP", "TITLE " + parser.getText());
	            }
	            if (eventType == XmlPullParser.START_TAG &&
		                XML_CONTENT.equals(parser.getName())) {
	            	parser.next();
	                String content =   parser.getText();
	                ArticleItem item = new ArticleItem();
	                Time today = new Time(Time.getCurrentTimezone());
	                today.setToNow();
	                
	                item.setIcon("");
	                item.setDate(today.format3339(true));
	                item.setContent(content);
	                item.setTitle(title);
//	                Log.d("FP", "\n#################\n" +  item.toString());
	                singleton.addArticle(item);
	            }

	            eventType = parser.next();
	        }
	}
}
