package com.example.homework311bjs0000;


import java.util.ArrayList;

import uk.co.jarofgreen.lib.ShakeDetectActivity;
import uk.co.jarofgreen.lib.ShakeDetectActivityListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleListFragment extends ListFragment {
	private ArrayList<ArticleItem> items = null;
	ArticleDataProvider provider = null;
	ShakeDetectActivity shakeDetectActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("FP", "ArticleListFragment::OnCreate");
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.article_list_title);
		items = ArticleListSingleton.getInstance(getActivity()).getArticles();
		
		ArticleAdapter adapter = 
				new ArticleAdapter(items);
		setListAdapter(adapter);
	
		provider = new ArticleDataProvider(this.getActivity());
		provider.fetchItems();
        shakeDetectActivity = new ShakeDetectActivity(getActivity());
        shakeDetectActivity.addListener(
        new ShakeDetectActivityListener() {
            @Override
            public void shakeDetected() {
                triggerShakeDetected();
            }
        });
	}
		
	
	@Override
	public void onListItemClick(ListView l , View v, int position, long id) {
		Log.d("FP", "onListItemClick");
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
        shakeDetectActivity.onPause();
	}
	@Override
	public void onResume() {
		super.onResume();
		((ArticleAdapter)getListAdapter()).notifyDataSetChanged();
        shakeDetectActivity.onResume();
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
	//		Log.d("FP", "Adapter.getView");
			
			TextView title = (TextView) convertView.findViewById(R.id.article_list_title);
			title.setText(item.getTitle());
			
			TextView  date = (TextView) convertView.findViewById(R.id.article_list_date);
			date.setText(item.getDate());
			
			return convertView;
		}
	}
	
	public void updateUI() {
		((ArticleAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
    public void triggerShakeDetected() {
    	if (provider == null) return;
    	provider.getArticles();
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
