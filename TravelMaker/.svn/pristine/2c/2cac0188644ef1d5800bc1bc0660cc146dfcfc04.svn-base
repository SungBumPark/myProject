package com.example.travelmaker.tab;

import com.example.travelmaker.scrap.ScrapListActivity;
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
public class Tab3 extends Fragment {
	Context mContext;
	TextView tv;

	public Tab3(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.community_info, null);

		return view;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.menu_item1).setTitle("게시판");
		menu.findItem(R.id.menu_item2).setTitle("여행담");
		//menu.findItem(R.id.menu_item3).setTitle("SNS");
		menu.removeItem(R.id.menu_item3);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_item1:
			Toast.makeText(mContext, "Q&A", Toast.LENGTH_SHORT)
			.show();
			Intent intent1 = new Intent(getActivity(), com.example.travelmaker.post.PostSecondActivity.class);
			startActivity(intent1);	
			
			break;
		case R.id.menu_item2:
			Toast.makeText(mContext, "여행담 ", Toast.LENGTH_SHORT)
			.show();
			Intent intent2 = new Intent(getActivity(), com.example.travelmaker.post.PostMainActivity.class);
			startActivity(intent2);	
			
			
			break;
		/* case R.id.menu_item3:
			Toast.makeText(mContext, "SNS", Toast.LENGTH_SHORT)
			.show();
			break; */

		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

}