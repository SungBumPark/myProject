package com.example.travelmaker.tab;

import com.example.travelmaker.main.*;
import com.example.travelmaker.tour.gpsinfomain.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;
/**
 * @author Jin ah Lee
 * @since 2013.08.03
 */

@SuppressLint("ValidFragment")
public class Tab1 extends Fragment{
		Context mContext;
		TextView tv;
		//FragmentManager fm = getFragmentManager();
		public Tab1(Context context) {
			mContext = context;
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, 
				ViewGroup container, Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			View view = inflater.inflate(R.layout.main_info, null);

			
		return view;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.menu_item2).setTitle("내 주변 여행지 검색");
		menu.findItem(R.id.menu_item3).setTitle("지역별 여행지 검색");
		menu.removeItem(R.id.menu_item1);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item3:
			Intent intent1 = new Intent(getActivity(), AreaInfoMain.class);

			//Intent intent1 = new Intent(mContext, AreaInfoMain.class);
			//6onCreateView(inflater, container, savedInstanceState)
			Log.d(GPSInfoMain.DEBUG,
				    "&10");
			startActivity(intent1);
			break;
		case R.id.menu_item2:
			//getActivity().startActivity(new Intent(mContext, GPSInfoMain.class));
			Intent intent2 = new Intent(getActivity(), GPSInfoMain.class);
			Log.d(GPSInfoMain.DEBUG,
				    "&1");
			startActivity(intent2);
			break;

		}
		return true;
	}
}

