package com.example.homework314bjs0000;

import android.support.v4.app.Fragment;
import android.util.Log;

public class SelectLocationActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
//		Log.d("FP", "SelectLocationActivity");
		return new SelectLocationFragment();
	}

}
