package com.example.homework312bjs0000;


import  android.support.v4.app.Fragment;

public class ArticleActivity extends SingleFragmentActivity {
	String m_contactName;
	@Override
	protected Fragment createFragment() {

		String articleTitle = (String)getIntent().getSerializableExtra(ArticleFragment.EXTRA_ARTICLE_TITLE);
		m_contactName = articleTitle;
//		Log.d("FP", "articleTitle" + articleTitle.toString());
		return ArticleFragment.newInstance(articleTitle);
	}
	

}
