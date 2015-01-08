package com.example.travelmaker.main;

import com.example.travelmaker.tour.gpsinfomain.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;

public class StartApp extends Activity{

	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main_loading);
        
        intent = new Intent(this, com.example.travelmaker.tab.TabActivity.class);
        Log.d(GPSInfoMain.DEBUG,
			    "&1");
        startActivityWithDelay();

	}

	private void startActivityWithDelay() {
		// TODO Auto-generated method stub
		 Handler handler = new Handler();
	        handler.postDelayed(new Runnable() {

	            public void run() {
	            	Log.d(GPSInfoMain.DEBUG,
	        			    "&2");
	                startActivity(intent);
	                
	                finish();
	            }
	        //Do Something 1000 = 1s
	        }, 3000);



	}
}
