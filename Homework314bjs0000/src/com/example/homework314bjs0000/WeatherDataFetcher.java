package com.example.homework314bjs0000;

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


public class WeatherDataFetcher {
	//private final String WEATHER_ENDPOINT =
	//		"http://api.worldweatheronline.com/free/v1/weather.ashx?q=Seattle&format=xml&num_of_days=5&key=jn7tg225uxbhnzvjftgbvpjk";
	private final String URL_PREAMBLE = "http://api.worldweatheronline.com/free/v1/weather.ashx?q=";
	private final String URL_POSTAMBLE = "&format=xml&num_of_days=5&key=jn7tg225uxbhnzvjftgbvpjk";

	// Request
	private static final String XML_REQUEST_TAG = "request";
    private static final String XML_TYPE_TAG    = "type";
    private static final String XML_QUERY_TAG   = "query";
    
    // Current Conditions
    private static final String XML_CURRENT_CONDITION_TAG = "current_condition";
    private static final String XML_OBSERVATION_TIME_TAG  = "observation_time";
    private static final String XML_TEMP_C_TAG            = "temp_C";
    private static final String XML_TEMP_F_TAG            = "temp_F";
    // XML_WEATHER_CODE_TAG
    // XML_WEATHER_ICON_URL_TAG
    // XML_WEATHER_DESC_TAG
    // XML_WIND_SPEED_MILES_TAG
    // XML_WIND_SPEED_KMPH_TAG
    // XML_WIND_DIR_DEGREE_TAG
    // XML_WIND_DIR_16_POINT_TAG
    // XML_PRECIP_MM_TAG
    private static final String XML_HUMIDITY_TAG    = "humidity";
    private static final String XML_VISIBILITY_TAG  = "visibility";
    private static final String XML_PRESSURE_TAG    = "pressure";
    private static final String XML_CLOUD_COVER_TAG = "cloudcover";
    
    // Daily Forecast
    private static final String XML_DAILY_TAG      = "weather";
    private static final String XML_DATE_TAG       = "date";
    private static final String XML_TEMP_MAX_C_TAG = "tempMaxC";
    private static final String XML_TEMP_MAX_F_TAG = "tempMaxF";
    private static final String XML_TEMP_MIN_C_TAG = "tempMinC";
    private static final String XML_TEMP_MIN_F_TAG = "tempMinF";
    // XML_WIND_SPEED_MILES_TAG
    // XML_WIND_SPEED_KMPH_TAG   
    private static final String XML_WIND_DIRECTION_TAG = "winddirection";
    // XML_WIND_DIR_16_POINT_TAG
    // XML_WIND_DIR_DEGREE_TAG
    // XML_WEATHER_CODE_TAG
    // XML_WEATHER_ICON_URL_TAG
    // XML_WEATHER_DESC_TAG
    // XML_PRECIP_MM_TAG
    		
    // Current Conditions & Daily Forecast Common Tags
    private static final String XML_WIND_SPEED_MILES_TAG  = "windspeedMiles";
    private static final String XML_WIND_SPEED_KMPH_TAG   = "windspeedKmph";
    private static final String XML_WIND_DIR_16_POINT_TAG = "winddir16Point";
    private static final String XML_WIND_DIR_DEGREE_TAG   = "winddirDegree";
    private static final String XML_WEATHER_CODE_TAG      = "weatherCode";    
    private static final String XML_WEATHER_ICON_URL_TAG  = "weatherIconUrl";
    private static final String XML_WEATHER_DESC_TAG      = "weatherDesc";
    private static final String XML_PRECIP_MM_TAG         = "precipMM";
    
    boolean isCurrent;
    int forecastDay;
    private final int FORECAST_DAY_1 = 0;
    WeatherData data;
    WeatherDataFetcher() {
		data = new WeatherData();
		forecastDay = FORECAST_DAY_1;
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

	public WeatherData fetchData(String query) {
//		    Log.d("FP", "search Param " + query);
	        try {
	        	String url = Uri.parse(URL_PREAMBLE+query+URL_POSTAMBLE).buildUpon().build().toString();
//	        	Log.d("FP", "URL " + url);
	    		String xmlString = getUrl(url);

	            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	            XmlPullParser parser = factory.newPullParser();
	            parser.setInput(new StringReader(xmlString));           
	            parseData(parser);
	        } catch (IOException ioe) {
	            Log.e("FP", "Failed to fetch items", ioe);
	        } catch (XmlPullParserException xppe) {
	            Log.e("FP", "Failed to parse items", xppe);
	        }
	        return data;
	}

	void parseData(XmlPullParser parser) throws XmlPullParserException, IOException {
	        int eventType = parser.next();
//	        Log.d("FP", "parseData");
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	        	 if (eventType == XmlPullParser.START_TAG) {	       
	        		 if (XML_REQUEST_TAG.equals(parser.getName())) {	        			 
//	        			 Log.d("FP", "REQUEST");
	        	 	 } else if (XML_TYPE_TAG.equals(parser.getName())) {
	        	 		 parser.next();	        	 		 
	        	 		 data.setQueryType(parser.getText());
	        	 	 } else if (XML_QUERY_TAG.equals(parser.getName())) {
	        	 		 parser.next();	  
	        	 		 data.setQurey(parser.getText());	        	 		 
	        	 	 } else if (XML_CURRENT_CONDITION_TAG.equals(parser.getName())) {
	        	 		 this.isCurrent = true;
	        	 	 } else if (XML_DAILY_TAG.equals(parser.getName())) {
	        	 		 isCurrent = false;
//	        	 		 Log.d("FP","DAILY " + ((Integer) forecastDay).toString());
	        	 	 } else if (XML_OBSERVATION_TIME_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 data.getCurrentCondition().setObservationTime(parser.getText());
	        	 	 } else if (XML_TEMP_C_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 data.getCurrentCondition().setTempC(parser.getText());
	        	 	 } else if (XML_TEMP_F_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 data.getCurrentCondition().setTempF(parser.getText());
	        	 	 } else if (XML_WEATHER_CODE_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
	        	 			 data.getCurrentCondition().setWeatherCode(parser.getText());
	        	 		 } else {
	        	 			 data.getDailyWeather()[this.forecastDay].setWeatherCode(parser.getText());
	        	 		 }
	        	 	 } else if (XML_WEATHER_ICON_URL_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
		        	 		 data.getCurrentCondition().setIconUrl(parser.getText());
		    	             if (data.getCurrentCondition().getIconUrl().length() > 0) {
		    	            	 try {
		    	            		 InputStream in = new java.net.URL(data.getCurrentCondition().getIconUrl()).openStream();
		    	                     Bitmap bitmap = BitmapFactory.decodeStream(in);
		    	                     if (bitmap != null) {
		    	                    	 data.getCurrentCondition().setBitmap(bitmap);
		    	                     }
		    	            	 } catch (Exception e) {
		    	                     Log.e("Error", e.getMessage());
		    	                     e.printStackTrace();
		    	            	 }	    	                	
		    	             }
		   	        	
	        	 		 } else {
		        	 		 data.getDailyWeather()[this.forecastDay].setIconUrl(parser.getText());	        	 			 
		    	             if (data.getDailyWeather()[this.forecastDay].getIconUrl().length() > 0) {
		    	            	 try {
		    	            		 InputStream in = new java.net.URL(data.getDailyWeather()[this.forecastDay].getIconUrl()).openStream();
		    	                     Bitmap bitmap = BitmapFactory.decodeStream(in);
		    	                     if (bitmap != null) {
		    	                    	 data.getDailyWeather()[this.forecastDay].setBitmap(bitmap);
		    	                     }
		    	            	 } catch (Exception e) {
		    	                     Log.e("Error", e.getMessage());
		    	                     e.printStackTrace();
		    	            	 }	    	                	
		    	             }

	        	 		 }	        	 		 
	        	 	 } else if (XML_WEATHER_DESC_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
		        	 		 data.getCurrentCondition().setBitmapDescription(parser.getText());;
	        	 		 } else {
	        	 			data.getDailyWeather()[this.forecastDay].setBitmapDescription(parser.getText());
	        	 		 }	 	        	 		 

	        	 	 } else if (XML_WIND_SPEED_MILES_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
		        	 		 data.getCurrentCondition().setWindSpeedMiles(parser.getText());;
	        	 		 } else {
	        	 			data.getDailyWeather()[this.forecastDay].setWindSpeedMiles(parser.getText());
	        	 		 }	 	        	 		 ;
	        	 	 } else if (XML_WIND_SPEED_KMPH_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
		        	 		 data.getCurrentCondition().setWindSpeedKmph(parser.getText());;
	        	 		 } else {
	        	 			data.getDailyWeather()[this.forecastDay].setWindSpeedKmph(parser.getText());
	        	 		 }	 	        	 		 
	        	 	 } else if (XML_WIND_DIR_DEGREE_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
		        	 		 data.getCurrentCondition().setWindDirDegree(parser.getText());;
	        	 		 } else {
	        	 			data.getDailyWeather()[this.forecastDay].setWindDirDegree(parser.getText());
	        	 		 }	 	        	 		 
	        	 	 } else if (XML_WIND_DIR_16_POINT_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
		        	 		 data.getCurrentCondition().setWind16Point(parser.getText());;
	        	 		 } else {
	        	 			data.getDailyWeather()[this.forecastDay].setWindDir16Point(parser.getText());
	        	 		 }	 	        	 		 
	        	 	 } else if (XML_PRECIP_MM_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 if (this.isCurrent == true) {
		        	 		 data.getCurrentCondition().setPrecipMM(parser.getText());;
	        	 		 } else {
	        	 			data.getDailyWeather()[this.forecastDay].setPrecipMM(parser.getText());
	        	 		 }	 	        	 		 
	        	 	 } else if (XML_HUMIDITY_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 data.getCurrentCondition().setHumidity(parser.getText());
	        	 	 } else if (XML_VISIBILITY_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 data.getCurrentCondition().setVisibility(parser.getText());
	        	 	 } else if (XML_PRESSURE_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 data.getCurrentCondition().setPressure(parser.getText());
	        	 	 } else if (XML_CLOUD_COVER_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		 data.getCurrentCondition().setColudcover(parser.getText());
	        	 	 } else if (XML_DATE_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		data.getDailyWeather()[this.forecastDay].setDate(parser.getText());
	        	 	 } else if (XML_TEMP_MAX_C_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		data.getDailyWeather()[this.forecastDay].setTempMaxC(parser.getText());
	        	 	 } else if (XML_TEMP_MAX_F_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		data.getDailyWeather()[this.forecastDay].setTempMaxF(parser.getText());
	        	 	 } else if (XML_TEMP_MIN_C_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		data.getDailyWeather()[this.forecastDay].setTempMinC(parser.getText());
	        	 	 } else if (XML_TEMP_MIN_F_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		data.getDailyWeather()[this.forecastDay].setTempMinF(parser.getText());
	        	 	 } else if (XML_WIND_DIRECTION_TAG.equals(parser.getName())) {
	        	 		 parser.next();
	        	 		data.getDailyWeather()[this.forecastDay].setWindDirection(parser.getText());
	        	 	 }
	        		
	        	 } else if(eventType == XmlPullParser.END_TAG) {
	        		 if (XML_CURRENT_CONDITION_TAG.equals(parser.getName())) {
	        			 this.isCurrent = false;
	        		 }
	        		 else if (XML_DAILY_TAG.equals(parser.getName()))
	        			 if (forecastDay < WeatherData.getNUMBER_OF_FORECAST_DAYS())
	        				 this.forecastDay++;
	        		 		        		 
	        	 }

	            eventType = parser.next();	            

	        }
//            Log.d("FP", data.toString());
	}
}
