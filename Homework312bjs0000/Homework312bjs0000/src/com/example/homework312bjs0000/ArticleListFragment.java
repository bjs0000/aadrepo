package com.example.homework312bjs0000;


import java.util.ArrayList;

import uk.co.jarofgreen.lib.ShakeDetectActivity;
import uk.co.jarofgreen.lib.ShakeDetectActivityListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleListFragment extends ListFragment {
	private ArrayList<ArticleItem> items = null;
    private final String GOOGLE_ENDPOINT = "https://news.google.com/news/section?topic=w&output=rss";
    private final String YAHOO_ENDPOINT = "http://news.yahoo.com/rss/world/";    
    ArticleListSingleton singleton = null;
	ShakeDetectActivity shakeDetect;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		singleton = ArticleListSingleton.getInstance(getActivity());

		new ArticleFetcher().execute();
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.article_list_title);
		

		items = ArticleListSingleton.getInstance(getActivity()).getArticles();
		
		ArticleAdapter adapter = 
				new ArticleAdapter(items);
		setListAdapter(adapter);
	
		// Not an activity third party naming issue
        shakeDetect = new ShakeDetectActivity(getActivity());
        shakeDetect.addListener(
        new ShakeDetectActivityListener() {
            @Override
            public void shakeDetected() {
                triggerShakeDetected();
            }
        });
	}
	void disable() {
		setListShown(false);
	}
	void enable() {
		setListShown(true);
	}
    private class ArticleFetcher extends AsyncTask<Void,Void,Void>{
        @Override
        protected  Void doInBackground(Void... params) {
        	singleton.purgeDB();
        	new ArticleDataProvider(singleton).fetchItems(GOOGLE_ENDPOINT);
        	new ArticleDataProvider(singleton).fetchItems(YAHOO_ENDPOINT);
        	singleton.sortList();
			return null;
        }

        @Override
        protected void onPostExecute(Void dummyArgument) {
        	updateUI();   		
        }
    }	
	
	@Override
	public void onListItemClick(ListView l , View v, int position, long id) {
		ArticleItem article = ((ArticleAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), ArticleActivity.class);
        i.putExtra(ArticleFragment.EXTRA_ARTICLE_TITLE, article.getTitle());
        startActivityForResult(i, 0);	

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onPause() {
		super.onPause();
        shakeDetect.onPause();
	}
	@Override
	public void onResume() {
		super.onResume();
		((ArticleAdapter)getListAdapter()).notifyDataSetChanged();
        shakeDetect.onResume();
	}
	
	private class ArticleAdapter extends ArrayAdapter<ArticleItem> {
		public ArticleAdapter(ArrayList<ArticleItem> items) {
			super (getActivity(), 0, items);
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().
						inflate(R.layout.fragment_article_list_item, null);
			}
			
			ArticleItem item = getItem(position);
			
			TextView title = (TextView) convertView.findViewById(R.id.article_list_title);
			title.setText(item.getTitle());
			
			TextView  date = (TextView) convertView.findViewById(R.id.article_list_date);
			date.setText(item.getDate());
			
			if (item.getIcon().length() != 0) {
				ImageButton iconImageView = (ImageButton) convertView.findViewById(R.id.fc_image);

				iconImageView.setImageBitmap(item.getBitmap());
			}
			return convertView;
		}
	}
	
	public void updateUI() {
		enable();
		((ArticleAdapter) getListAdapter()).notifyDataSetChanged();
	}
	

    public void triggerShakeDetected() {
    	disable();
    	new ArticleFetcher().execute();
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.shake_toast,
				(ViewGroup) getActivity().findViewById(R.id.erroLayout));
		Toast toast = new Toast(getActivity());
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	    Log.d("FP", "SHAKE DETETED!!!");

    }
}
