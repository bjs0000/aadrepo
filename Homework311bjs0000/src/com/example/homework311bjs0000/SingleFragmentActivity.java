package com.example.homework311bjs0000;

import  android.support.v4.app.FragmentManager;
import  android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Abstract class for creating Fragment Activities that support one Fragment
 * @author Brad
 *
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
	/**
	 * abstract method for fragment creation overridden by specializations
	 * @return
	 */
	protected abstract Fragment createFragment();

	protected int getLayoutResId() {
		return R.layout.single_fragment_activity;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_fragment_activity);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction()
			.add(R.id.fragmentContainer, fragment)
			.commit();

		}
	}

}
