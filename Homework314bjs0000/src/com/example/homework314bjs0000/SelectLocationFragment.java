package com.example.homework314bjs0000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SelectLocationFragment extends Fragment {
	public static final String EXTRA_LOCATION = "com.example.homework314bjs0000.location";

	private TextView titleView;
	private EditText locationView;
	
	private Button okButton;
	private Button cancelButton;
	
	private String location = "";
	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.select_location_fragment, parent, false);
		okButton = (Button)v.findViewById(R.id.ok_button);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Log.d("FP", "ON CLICK");
				location = locationView.getText().toString();
				sendResult(WeatherFragment.LOCATION_RESULT_CODE);
				getActivity().finish();
	
			}
		});
		cancelButton = (Button)v.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				sendResult(Activity.RESULT_CANCELED);
				getActivity().finish();
				
			}
		});
			
		titleView = (TextView)v.findViewById(R.id.location_tite);
		titleView.setText("Location City/Zip");	
		locationView = (EditText)v.findViewById(R.id.location);
		locationView.setText("");	
		return v;
	}
	
	private void sendResult(int resultCode) {
		Intent data = new Intent();
		data.putExtra(EXTRA_LOCATION, location);
		getActivity().setResult(resultCode, data);

//		Log.d("FP", "RETURNING " + data.getStringExtra(EXTRA_LOCATION));
		
	}
	
}
