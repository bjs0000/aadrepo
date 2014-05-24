package com.example.homework314bjs0000;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

public class WorldWeatherOnlineDataDownloader<Token> extends HandlerThread {
	public static final String TAG = "WorldWeatherOnlineDataDownloader";
	private static final int WEATHER_DATA = 0;
	public WorldWeatherOnlineDataDownloader() {
		super(TAG);
	}
    Handler handler;
    Map<Token,String> requestMap = 
            Collections.synchronizedMap(new HashMap<Token,String>());
    Handler mResponseHandler;
    Listener<Token> listener;
    
    public interface Listener<Handle> {
        void onData(WeatherData data);
    }
    
    public void setListener(Listener<Token> listener) {
        this.listener = listener;
    }

    public WorldWeatherOnlineDataDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WEATHER_DATA) {
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    private void handleRequest(final Token handle) {
    	final String query = requestMap.get(handle);
        if (query == null) return;
            
        final WeatherData data = new WeatherDataFetcher().fetchData(query);
            
        mResponseHandler.post(new Runnable() {
        	public void run() {
        		if (requestMap.get(handle) != query) return;
                requestMap.remove(handle);
                listener.onData(data);
        	}
        });
    }
    
    public void queueRequest(Token token, String url) {
        requestMap.put(token, url);
        
        handler
            .obtainMessage(WEATHER_DATA, token)
            .sendToTarget();
    }
    
    public void clearQueue() {
        handler.removeMessages(WEATHER_DATA);
        requestMap.clear();
    }
}
