package com.example.travelmaker.tab;

import com.example.travelmaker.calendar.MyCalendarActivity;
import com.example.travelmaker.plan.PlanMain;
import com.example.travelmaker.scrap.*;
import com.example.travelmaker.tour.gpsinfomain.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Jin ah Lee
 * @since 2013.08.03
 */

@SuppressLint("ValidFragment")
public class Tab2 extends Fragment {
		Context mContext;
		TextView tv;
		
		public Tab2(Context context) {
			mContext = context;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, 
				ViewGroup container, Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			View view = inflater.inflate(R.layout.calender_info, null);

						
	    	return view;
		}
		
		
		 @Override
		 public void onPrepareOptionsMenu(Menu menu) {
		        menu.findItem(R.id.menu_item1).setTitle("스크랩"); 
		        menu.findItem(R.id.menu_item2).setTitle("여행 등록"); 
		        menu.findItem(R.id.menu_item3).setTitle("여행 선택");	  
		    }
		 
		 @Override
		 public boolean onOptionsItemSelected(MenuItem item) {
		     switch(item.getItemId()){
		     
		     case R.id.menu_item1:
		    	  Intent intent3 = new Intent(getActivity(), ScrapListActivity.class);
		    	  startActivity(intent3);
		    	  break;
		    	  
		     case R.id.menu_item2:
		    	  Intent intent1 = new Intent(getActivity(),MyCalendarActivity.class);
		    	  startActivity(intent1);
		    	  break;
		    	  
		     case R.id.menu_item3:
		    	  Intent intent2 = new Intent(getActivity(), PlanMain.class);
		    	  startActivity(intent2);
		    	  break;
		    
    
		     default:
		         return super.onOptionsItemSelected(item);
		     }
			return true;
		 }
	}