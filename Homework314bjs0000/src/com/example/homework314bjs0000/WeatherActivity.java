package com.example.homework314bjs0000;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

public class WeatherActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		return new WeatherFragment();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	}
}
