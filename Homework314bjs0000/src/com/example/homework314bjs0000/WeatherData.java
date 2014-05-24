package com.example.homework314bjs0000;


import java.util.Arrays;

import android.graphics.Bitmap;

public class WeatherData {
	
	@Override
	public String toString() {
		return "WeatherData [queryType=" + queryType + ", query=" + query
				+ ", currentCondition=" + currentCondition + ", dailyWeather="
				+ Arrays.toString(dailyWeather) + "]";
	}


	public String getQueryType() {
		return queryType;
	}


	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}


	public String getQurey() {
		return query;
	}


	public void setQurey(String query) {
		this.query = query;
	}


	public CurrentCondition getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(CurrentCondition currentCondition) {
		this.currentCondition = currentCondition;
	}

	public DailyWeather[] getDailyWeather() {
		return dailyWeather;
	}

	public void setDailyWeather(DailyWeather[] dailyWeather) {
		this.dailyWeather = dailyWeather;
	}
	private String queryType = "";
	private String query = "";
	private CurrentCondition currentCondition;
	private DailyWeather[] dailyWeather;
	private static final int NUMBER_OF_FORECAST_DAYS = 5;
	WeatherData() { 
		dailyWeather = new DailyWeather[NUMBER_OF_FORECAST_DAYS];
		currentCondition = new CurrentCondition();
		for (int i = 0; i < dailyWeather.length; i++) {
			dailyWeather[i] = new DailyWeather();			
		}
	}
	
	public static int getNUMBER_OF_FORECAST_DAYS() {
		return NUMBER_OF_FORECAST_DAYS;
	}

	class CurrentCondition {

		@Override
		public String toString() {
			return "CurrentCondition [observationTime=" + observationTime
					+ ", tempC=" + tempC + ", tempF=" + tempF
					+ ", weatherCode=" + weatherCode + ", iconUrl=" + iconUrl
					+ ", bitmap=" + bitmap + ", bitmapDescription="
					+ bitmapDescription + ", windSpeedMiles=" + windSpeedMiles
					+ ", windSpeedKmph=" + windSpeedKmph + ", windDirDegree="
					+ windDirDegree + ", wind16Point=" + wind16Point
					+ ", precipMM=" + precipMM + ", humidity=" + humidity
					+ ", visibility=" + visibility + ", pressure=" + pressure
					+ ", coludcover=" + coludcover + "]";
		}
		public String getObservationTime() {
			return observationTime;
		}
		public void setObservationTime(String observationTime) {
			this.observationTime = observationTime;
		}
		public String getTempC() {
			return tempC;
		}
		public void setTempC(String tempC) {
			this.tempC = tempC;
		}
		public String getTempF() {
			return tempF;
		}
		public void setTempF(String tempF) {
			this.tempF = tempF;
		}
		public String getWeatherCode() {
			return weatherCode;
		}
		public void setWeatherCode(String weatherCode) {
			this.weatherCode = weatherCode;
		}
		public String getIconUrl() {
			return iconUrl;
		}
		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}
		public Bitmap getBitmap() {
			return bitmap;
		}
		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}
		public String getBitmapDescription() {
			return bitmapDescription;
		}
		public void setBitmapDescription(String bitmapDescription) {
			this.bitmapDescription = bitmapDescription;
		}
		public String getWindSpeedMiles() {
			return windSpeedMiles;
		}
		public void setWindSpeedMiles(String windSpeedMiles) {
			this.windSpeedMiles = windSpeedMiles;
		}
		public String getWindSpeedKmph() {
			return windSpeedKmph;
		}
		public void setWindSpeedKmph(String windSpeedKmph) {
			this.windSpeedKmph = windSpeedKmph;
		}
		public String getWindDirDegree() {
			return windDirDegree;
		}
		public void setWindDirDegree(String windDirDegree) {
			this.windDirDegree = windDirDegree;
		}
		public String getWind16Point() {
			return wind16Point;
		}
		public void setWind16Point(String wind16Point) {
			this.wind16Point = wind16Point;
		}
		public String getPrecipMM() {
			return precipMM;
		}
		public void setPrecipMM(String precipMM) {
			this.precipMM = precipMM;
		}
		public String getHumidity() {
			return humidity;
		}
		public void setHumidity(String humidity) {
			this.humidity = humidity;
		}
		public String getVisibility() {
			return visibility;
		}
		public void setVisibility(String visibility) {
			this.visibility = visibility;
		}
		public String getPressure() {
			return pressure;
		}
		public void setPressure(String pressure) {
			this.pressure = pressure;
		}
		public String getColudcover() {
			return coludcover;
		}
		public void setColudcover(String coludcover) {
			this.coludcover = coludcover;
		}
		String observationTime = "";
		String tempC = "";
		String tempF = "";
		String weatherCode = "";
		String iconUrl = "";
		Bitmap bitmap = null;
		String bitmapDescription = "";
		String windSpeedMiles = "";
		String windSpeedKmph = "";
		String windDirDegree = "";
		String wind16Point = "";
		String precipMM = "";
		String humidity = "";
		String visibility = "";
		String pressure = "";
		String coludcover = "";
	}
	
	class DailyWeather {

		@Override
		public String toString() {
			return "DailyWeather [date=" + date + ", tempMaxC=" + tempMaxC
					+ ", tempMaxF=" + tempMaxF + ", tempMinC=" + tempMinC
					+ ", tempMinF=" + tempMinF + ", windSpeedMiles="
					+ windSpeedMiles + ", windSpeedKmph=" + windSpeedKmph
					+ ", windDirection=" + windDirection + ", windDIr16Point="
					+ windDir16Point + ", windDirDegree=" + windDirDegree
					+ ", weatherCode=" + weatherCode + ", iconUrl=" + iconUrl
					+ ", bitmap=" + bitmap + ", bitmapDescription="
					+ bitmapDescription + ", precipMM=" + precipMM + "]";
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTempMaxC() {
			return tempMaxC;
		}
		public void setTempMaxC(String tempMaxC) {
			this.tempMaxC = tempMaxC;
		}
		public String getTempMaxF() {
			return tempMaxF;
		}
		public void setTempMaxF(String tempMaxF) {
			this.tempMaxF = tempMaxF;
		}
		public String getTempMinC() {
			return tempMinC;
		}
		public void setTempMinC(String tempMinC) {
			this.tempMinC = tempMinC;
		}
		public String getTempMinF() {
			return tempMinF;
		}
		public void setTempMinF(String tempMinF) {
			this.tempMinF = tempMinF;
		}
		public String getWindSpeedMiles() {
			return windSpeedMiles;
		}
		public void setWindSpeedMiles(String windSpeedMiles) {
			this.windSpeedMiles = windSpeedMiles;
		}
		public String getWindSpeedKmph() {
			return windSpeedKmph;
		}
		public void setWindSpeedKmph(String windSpeedKmph) {
			this.windSpeedKmph = windSpeedKmph;
		}
		public String getWindDirection() {
			return windDirection;
		}
		public void setWindDirection(String windDirection) {
			this.windDirection = windDirection;
		}
		public String getWindDir16Point() {
			return windDir16Point;
		}
		public void setWindDir16Point(String windDir16Point) {
			this.windDir16Point = windDir16Point;
		}
		public String getWindDirDegree() {
			return windDirDegree;
		}
		public void setWindDirDegree(String windDirDegree) {
			this.windDirDegree = windDirDegree;
		}
		public String getWeatherCode() {
			return weatherCode;
		}
		public void setWeatherCode(String weatherCode) {
			this.weatherCode = weatherCode;
		}
		public String getIconUrl() {
			return iconUrl;
		}
		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}
		public Bitmap getBitmap() {
			return bitmap;
		}
		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}
		public String getBitmapDescription() {
			return bitmapDescription;
		}
		public void setBitmapDescription(String bitmapDescription) {
			this.bitmapDescription = bitmapDescription;
		}
		public String getPrecipMM() {
			return precipMM;
		}
		public void setPrecipMM(String precipMM) {
			this.precipMM = precipMM;
		}
		String date = "";
		String tempMaxC = "";
		String tempMaxF = "";
		String tempMinC = "";
		String tempMinF = "";
		String windSpeedMiles = "";
		String windSpeedKmph = "";
		String windDirection = "";
		String windDir16Point = "";
		String windDirDegree = "";
		String weatherCode = "";
		String iconUrl = "";
		Bitmap bitmap = null;
		String bitmapDescription = "";
		String precipMM = "";
	}
}
