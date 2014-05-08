package com.example.homework312bjs0000;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;


public class ArticleDataProvider {
	
    private static final String XML_TITLE = "title";
    private static final String XML_CONTENT = "content";
    private static final String XML_ITEM = "item";
    private static final String XML_DESCRIPTION = "description";
    private static final String XML_PUBDATE = "pubDate";
    private static final String XML_IMAGE = "image";
    private static final String XML_URL = "url";
   
 
    ArticleListSingleton m_singleton;
    
	ArticleDataProvider(ArticleListSingleton singleton) {
		m_singleton = singleton;
	}
	
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024 *100];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);        
            }
            out.close();

            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    
    
    String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
	public void fetchItems(String endpoint) {
		Log.d("FP", "fetchItems");
	        try {
	    		String url = Uri.parse(endpoint).buildUpon().build().toString(); 
	            
	    		String xmlString = getUrl(url);
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
	        int eventType = parser.next();
	        
	        String title = "";
	        String description = "";
	        String pubDate = "";
	        String contentProviderImage = "";
	        Bitmap bitmap = null;
	        
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	        	
	        	 if (eventType == XmlPullParser.START_TAG) {
	        		 if (XML_IMAGE.equals(parser.getName())) {
	 	            	while (eventType != XmlPullParser.END_DOCUMENT) {
		            		if (eventType == XmlPullParser.END_TAG &&
		            				XML_IMAGE.equals(parser.getName())) {
		            			break;
		            		}
		            		if (eventType == XmlPullParser.START_TAG && 
		            				XML_URL.equals(parser.getName())) {
		            			parser.next();
		            			contentProviderImage = parser.getText();
		            		}
		            		eventType = parser.next();
	 	            	}

	        		 }
	        	 }
	        	 
	            if (eventType == XmlPullParser.START_TAG
	            		&& XML_ITEM.equals(parser.getName())) {
	            	while (eventType != XmlPullParser.END_DOCUMENT) {
	            		if (eventType == XmlPullParser.END_TAG &&
	            				XML_ITEM.equals(parser.getName())) {
	    	                ArticleItem item = new ArticleItem();
	    	                // Set Image to provider image.
	    	                // Never saw Image in the article content so did not
	    	                // know how to parse it or would have added it as well.
	    	                item.setIcon(contentProviderImage);
	    	                item.setDate(pubDate);
	    	                item.setContent(description);
	    	                item.setTitle(title);
	    	                
	    	                if (contentProviderImage.length() > 0) {
	    	                    try {
	    	                        InputStream in = new java.net.URL(contentProviderImage).openStream();
	    	                        bitmap = BitmapFactory.decodeStream(in);
	    	                        if (bitmap != null) {
		    	                		item.setBitmap(bitmap);
	    	                        }
	    	                      } catch (Exception e) {
	    	                          Log.e("Error", e.getMessage());
	    	                          e.printStackTrace();
	    	                      }	    	                	
	    	                }
	    	                m_singleton.addArticle(item);
	   	        		 	break;
	   	        	 	}
	   	        	 	if (eventType == XmlPullParser.START_TAG)
	            		if (eventType == XmlPullParser.START_TAG &&
	            				XML_PUBDATE.equals(parser.getName())) {
	            			parser.next();
	    	                pubDate = parser.getText();
	    	                
	            		}
	            		if (eventType == XmlPullParser.START_TAG &&
	            				XML_TITLE.equals(parser.getName())) {
	            			parser.next();
	    	                title  =   parser.getText();
	            		}
	            		if (eventType == XmlPullParser.START_TAG &&
	            				XML_DESCRIPTION.equals(parser.getName())) {
	            			parser.next();
	    	                description = parser.getText();
		   	        	
	            		}
	            		eventType = parser.next();

	            	}

	            }
	            if (eventType == XmlPullParser.START_TAG &&
		                XML_CONTENT.equals(parser.getName())) {

	                ArticleItem item = new ArticleItem();
              
	                item.setIcon("");
	                item.setDate(pubDate);
	                item.setContent(description);
	                item.setTitle(title);
	                m_singleton.addArticle(item);

	            }

	            eventType = parser.next();
	        }
	}
}
