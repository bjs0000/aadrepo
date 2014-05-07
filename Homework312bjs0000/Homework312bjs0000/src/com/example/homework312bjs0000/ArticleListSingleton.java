package com.example.homework312bjs0000;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class ArticleListSingleton {
	private ArrayList<ArticleItem> itemList;
	
	private static ArticleListSingleton sItemList;
	
	
	private ArticleListSingleton(Context context) {
		
		try {
			itemList = new ArrayList<ArticleItem>();
			
		} catch (Exception e) {
			itemList = new ArrayList<ArticleItem>();
			Log.e("FP", "Error loading Articles:",e);
		}

	}
	
	public static ArticleListSingleton getInstance(Context context) {
		if (sItemList == null) {
			sItemList = new ArticleListSingleton(context.getApplicationContext());
		}
		return sItemList;
	}
	
	public ArrayList<ArticleItem> getArticles() {
		return itemList;
	}
	
	public ArticleItem getArticle(String title) {
		for (ArticleItem item : itemList) {
			if (item.getTitle().compareTo(title) == 0) {
				return item;
			}
		}
		return null;
	}
	
	public void addArticle(ArticleItem item) {
		Log.d("FP", "New Article " + item.getTitle());
		itemList.add(item);
		
	}
	
	public void purgeDB() {
		int idx = 0;
		while (idx < itemList.size())
		{
			// Remove item
		    itemList.remove(idx);
		    ++idx;

		}
	}
	public void deleteArticle(String title) {

		int idx = 0;
		while (idx < itemList.size())
		{
		   if(itemList.get(idx).getTitle().equals(title))
		   {
		     // Remove item
		     itemList.remove(idx);
		     Log.d("FP", "DELETED " + title);
		     break;
		  
		  }
		  else
		  {
		    ++idx;
		  }
		}
	}
	
	public void sortList() {
		Collections.sort(itemList, new MyComparator());
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public class MyComparator implements Comparator<ArticleItem> {
	    @Override
	    public int compare(ArticleItem AI1, ArticleItem AI2) {
	    	return compareDates(AI2.getDate(), AI1.getDate());
	    }
	    
	    private int compareDates(String date2, String date1) {
	    	return convertFormat(date2).compareTo(convertFormat(date1));
	    }
	    
	    private Date convertFormat(String date) {
	    	String[] parts = date.split(" ");

	        String tzid =  date.contains("GMT")?"GMT":"EST";
	        String temp = "";
	        Date result = null;
	        if (parts.length >= 4)
	         temp = parts[2] + " " + parts[1] +
	        " " + parts[3] + " " + parts[4] + " " + tzid;

	        try {
		    	result = new SimpleDateFormat("MMMM dd yyyy hh:mm:ss z").parse(temp);

			} catch (ParseException e) {
				Log.d("FP", "THREW");
				e.printStackTrace();
			}
	        return result;

	    }
	}
}
