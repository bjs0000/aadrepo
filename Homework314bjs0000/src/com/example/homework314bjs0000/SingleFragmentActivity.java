package com.example.homework314bjs0000;



import  android.support.v4.app.FragmentManager;
import  android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

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
//		Log.d("FP", "SFA");
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
