package com.example.homework311bjs0000;

import android.support.v4.app.Fragment;

public class ArticleListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ArticleListFragment();
    }
}