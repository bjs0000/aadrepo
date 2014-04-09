package com.example.homework311bjs0000;

import java.util.ArrayList;

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
			Log.d("FP", "NEW ArticleListSingleton");
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
		Log.d("FP", "New Contact " + item.getTitle());
		itemList.add(item);
		
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
	
}
