package com.example.homework314bjs0000;


import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherFragment extends Fragment{
	String WorldWideWeatherOnlineAppKey = "jn7tg225uxbhnzvjftgbvpjk";
	WeatherData weatherData;
	WeatherDataFetcher fetcher;
	private ImageView weatherImage;
	private TextView  imageText;
	private TextView timeView;
	private TextView dateView;
	private TextView searchResultView;
	private TextView tempView;;
	private TextView dayOfWeek1View, dayOfWeek2View, dayOfWeek3View, dayOfWeek4View, dayOfWeek5View;
	private TextView date1View, date2View, date3View, date4View, date5View;
	private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
	private TextView hiTemp1, hiTemp2, hiTemp3, hiTemp4, hiTemp5;
	private TextView loTemp1, loTemp2, loTemp3, loTemp4, loTemp5;
	private TextView creditView;
	private static final int DAY1 = 0;
	private static final int DAY2 = 1;	
	private static final int DAY3 = 2;
	private static final int DAY4 = 3;
	private static final int DAY5 = 4;
	public final static int LOCATION_RESULT_CODE = 1;
	
	private WorldWeatherOnlineDataDownloader<WeatherData> downloadThread; 

	// Set the location for weather information can be city or zip
	// No help on what cities are supported.
	private void selectLocation(String cityOrZip) {
//	Log.d("FP", "Setting location " +  cityOrZip);
	downloadThread.queueRequest(weatherData, cityOrZip);
	}
	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		weatherData = new WeatherData();
		fetcher = new WeatherDataFetcher();
		downloadThread = new WorldWeatherOnlineDataDownloader<WeatherData>(new Handler());
		downloadThread.setListener(new WorldWeatherOnlineDataDownloader.Listener<WeatherData>() {

			@Override
			public void onData(WeatherData data) {
				updateUI(data);
			}
			});
		
	        downloadThread.start();
	        downloadThread.getLooper();
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.weather_fragment, parent, false);
		// This is were you enable Ancestral Navigation (up enabled)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
	
		 weatherImage =
					(ImageView) v.findViewById(R.id.weather_image);
		 imageText = (TextView) v.findViewById(R.id.image_text);
		 
		if (weatherData.getCurrentCondition().iconUrl.length() > 0) {
			weatherImage.setImageBitmap(weatherData.getCurrentCondition().getBitmap());
		}
		
		timeView = (TextView) v.findViewById(R.id.time);
		dateView = (TextView) v.findViewById(R.id.date);		
		searchResultView = (TextView) v.findViewById(R.id.search_text);
		tempView = (TextView) v.findViewById(R.id.temperatue);
		dayOfWeek1View = (TextView) v.findViewById(R.id.day1);
		dayOfWeek2View = (TextView) v.findViewById(R.id.day2);
		dayOfWeek3View = (TextView) v.findViewById(R.id.day3);
		dayOfWeek4View = (TextView) v.findViewById(R.id.day4);
		dayOfWeek5View = (TextView) v.findViewById(R.id.day5);
		date1View = (TextView) v.findViewById(R.id.date1);
		date2View = (TextView) v.findViewById(R.id.date2);
 		date3View = (TextView) v.findViewById(R.id.date3);
		date4View = (TextView) v.findViewById(R.id.date4);
		date5View = (TextView) v.findViewById(R.id.date5);
		imageView1 = (ImageView) v.findViewById(R.id.day1_image);
		imageView2 = (ImageView) v.findViewById(R.id.day2_image);
		imageView3 = (ImageView) v.findViewById(R.id.day3_image);
		imageView4 = (ImageView) v.findViewById(R.id.day4_image);
		imageView5 = (ImageView) v.findViewById(R.id.day5_image);
		hiTemp1 = (TextView) v.findViewById(R.id.temp_hi_1);
		hiTemp2 = (TextView) v.findViewById(R.id.temp_hi_2);
		hiTemp3 = (TextView) v.findViewById(R.id.temp_hi_3);
		hiTemp4 = (TextView) v.findViewById(R.id.temp_hi_4);
		hiTemp5 = (TextView) v.findViewById(R.id.temp_hi_5);
		loTemp1 = (TextView) v.findViewById(R.id.temp_lo_1);
		loTemp2 = (TextView) v.findViewById(R.id.temp_lo_2);
		loTemp3 = (TextView) v.findViewById(R.id.temp_lo_3);
		loTemp4 = (TextView) v.findViewById(R.id.temp_lo_4);
		loTemp5 = (TextView) v.findViewById(R.id.temp_lo_5);
		creditView = (TextView) v.findViewById(R.id.give_credit);
		creditView.setText(R.string.give_credit);
		selectLocation("Seattle");
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_select_location, menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_location:
				Intent i = new Intent(getActivity(), SelectLocationActivity.class);
				startActivityForResult(i, LOCATION_RESULT_CODE);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == LOCATION_RESULT_CODE) {
			selectLocation((String) data.getStringExtra(SelectLocationFragment.EXTRA_LOCATION));
//			Log.d("FP", "WeatherFragment onActivityResult " + (String) data.getStringExtra(SelectLocationFragment.EXTRA_LOCATION));
		} else {
//			Log.d("FP", "onActivityResult select location no location result");
		}
	}
	
	private void updateUI(WeatherData data) {
    	if (data.getCurrentCondition().iconUrl.length() > 0) {
    		weatherImage.setImageBitmap(data.getCurrentCondition().getBitmap());
    		imageText.setText(data.getCurrentCondition().getBitmapDescription());
    		searchResultView.setText(data.getQurey());
    		String tempC = data.getCurrentCondition().getTempC();
    		String tempF = data.getCurrentCondition().getTempF();

    		SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d");
    		String date = df.format(Calendar.getInstance().getTime());
    		dateView.setText((date.toString()));
    		
    	    df = new SimpleDateFormat("EEE");
    		String dayOfWeek = df.format(Calendar.getInstance().getTime());
		
    		df = new SimpleDateFormat("hh:mm a");
    		date = df.format(Calendar.getInstance().getTime());
    		
    		timeView.setText(date.toString());
    		
   		 	DateFormatSymbols weekDays = DateFormatSymbols.getInstance();
   		 	String[] weekDaysNameArray = weekDays.getShortWeekdays();
   		 	// Display name of week day in short from
   		 	for (int i = 1; i < weekDaysNameArray.length; i++) {
   		 		String dayName = weekDaysNameArray[i];
   		 		int j = i;
   		 		if (dayName.contains(dayOfWeek)) {
   	        		dayOfWeek1View.setText(weekDaysNameArray[j%8]);
   	        		j++;
   	        		if (j==8) j++;
   	        		dayOfWeek2View.setText(weekDaysNameArray[j%8]);
   	        		j++;
   	        		if (j==8) j++;
   	        		dayOfWeek3View.setText(weekDaysNameArray[j%8]);
   	        		j++;
   	        		if (j==8) j++;
   	        		dayOfWeek4View.setText(weekDaysNameArray[j%8]);
   	        		j++;
   	        		if (j==8) j++;
   	        		dayOfWeek5View.setText(weekDaysNameArray[j%8]);
   	        		break;
   		 		}
   		 	}
    		tempView.setText(tempF + "F/" + tempC + "C");

    		convertFormat(data.getDailyWeather()[DAY1].getDate());
    		date1View.setText(convertFormat(data.getDailyWeather()[DAY1].getDate()));
    		date2View.setText(convertFormat(data.getDailyWeather()[DAY2].getDate()));
     		date3View.setText(convertFormat(data.getDailyWeather()[DAY3].getDate()));
    		date4View.setText(convertFormat(data.getDailyWeather()[DAY4].getDate()));
    		date5View.setText(convertFormat(data.getDailyWeather()[DAY5].getDate()));
    		imageView1.setImageBitmap(data.getDailyWeather()[DAY1].getBitmap());
    		imageView2.setImageBitmap(data.getDailyWeather()[DAY2].getBitmap());
    		imageView3.setImageBitmap(data.getDailyWeather()[DAY3].getBitmap());
    		imageView4.setImageBitmap(data.getDailyWeather()[DAY4].getBitmap());
    		imageView5.setImageBitmap(data.getDailyWeather()[DAY5].getBitmap());
    		hiTemp1.setText((data.getDailyWeather()[DAY1].getTempMaxF() + "F"));
    		hiTemp2.setText((data.getDailyWeather()[DAY2].getTempMaxF() + "F"));
    		hiTemp3.setText((data.getDailyWeather()[DAY3].getTempMaxF() + "F"));
    		hiTemp4.setText((data.getDailyWeather()[DAY4].getTempMaxF() + "F"));
    		hiTemp5.setText((data.getDailyWeather()[DAY5].getTempMaxF() + "F"));
    		loTemp1.setText((data.getDailyWeather()[DAY1].getTempMinF() + "F"));
    		loTemp2.setText((data.getDailyWeather()[DAY2].getTempMinF() + "F"));
    		loTemp3.setText((data.getDailyWeather()[DAY3].getTempMinF() + "F"));
    		loTemp4.setText((data.getDailyWeather()[DAY4].getTempMinF() + "F"));
    		loTemp5.setText((data.getDailyWeather()[DAY5].getTempMinF() + "F"));

		}
    }

    
    private String convertFormat(String date) {
    	String[] parts = date.split("-");

        String temp = "";
        if (parts.length >= 3) {
	        temp = parts[1] + "-" + parts[2];
        }
        return temp;

    }
}
