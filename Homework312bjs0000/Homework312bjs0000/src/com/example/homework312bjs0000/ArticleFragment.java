package com.example.homework312bjs0000;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ArticleFragment extends Fragment {
	public static final String EXTRA_ARTICLE_TITLE = "com.example.homework311bjs0000.article_title";
	public static final String DIALOG_IMAGE = "image";

	private ArticleItem article;
	
	private ImageView 	iconImageView;	
	private TextView 	titleTextView;
	private TextView 	dateTextView;
	private TextView 	contentTextView;

	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(false);
		String extraArticleTitle = (String)getArguments().getSerializable(EXTRA_ARTICLE_TITLE);
		article = ArticleListSingleton.getInstance(getActivity()).getArticle(extraArticleTitle);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup parent,
			Bundle savedInstanceState) {
		Log.d("FP", "ArticleFragement onCreateView");
		View v = inflater.inflate(R.layout.article_detail, parent, false);
		// THis is were you enable Ancestral Navigation (up enabled)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
	
		
		titleTextView = (TextView)v.findViewById(R.id.article_detail_title);	
		titleTextView.setText(article.getTitle());
		titleTextView.setMovementMethod(new ScrollingMovementMethod());
		dateTextView = (TextView)v.findViewById(R.id.article_detail_date);
		dateTextView.setText(article.getDate());
		contentTextView = (TextView)v.findViewById(R.id.article_detail_content);
		contentTextView.setMovementMethod(new ScrollingMovementMethod());
		contentTextView.setText(Html.fromHtml(article.getContent()), TextView.BufferType.SPANNABLE);

		if (article.getIcon().length() != 0) {
			iconImageView = (ImageButton) v.findViewById(R.id.fc_image);
			iconImageView.setImageBitmap(article.getBitmap());
		}
		return v;
	}
	

	public static ArticleFragment newInstance(String name) {
		Bundle args = new Bundle();
		args.putCharSequence(EXTRA_ARTICLE_TITLE, name);
		ArticleFragment fragment = new ArticleFragment();
		fragment.setArguments(args);
		return fragment;
	}
	


}
