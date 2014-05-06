package com.example.homework312bjs0000;


import android.support.v4.app.Fragment;
import android.util.Log;

public class ArticleListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
    	Log.d("FP", "ALA");
        return new ArticleListFragment();
    }
}