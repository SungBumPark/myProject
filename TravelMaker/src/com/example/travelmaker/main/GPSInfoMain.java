package com.example.travelmaker.main;

import java.util.*;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.info.gps.*;
import com.example.travelmaker.main.PopupFragment.*;
import com.example.travelmaker.tour.gpsinfomain.*;

/**
 * �ֺ� ������ �˻� ��Ƽ��Ƽ
 */
public class GPSInfoMain extends Activity implements OnClickListener,
		NetworkListener, OnPopupShowListener {

	public static final String DEBUG = "Travel_Maker";
	private View progressDialog;
	private NetworkMgr networkMgr; // ��Ʈ��ũ �Ŵ���
	private GPSTracker tracker; // ��ġ Ʈ��Ŀ
	private String longitude, latitude; // ����, �浵
	private ImageButton ibTour;
	private ImageButton ibCulture;
	private ImageButton ibFestival;
	private ImageButton ibCourse;
	private ImageButton ibLeports;
	private ImageButton ibSleep;
	private ImageButton ibShopping;
	private ImageButton ibFood;
	private ImageButton ibTraffic;
	private ImageButton ib2000;
	private ImageButton ib5000;
	private ImageButton ib8000;
	private ImageButton ib10000;
	private ImageButton ib15000;
	private ImageButton ib20000;
	private ImageButton ibSearch;
	private View currentDistance, currentType; // ���� �Ÿ� , ���� �˻� Ÿ��
	private String contentTypeId, distance;
	private CustomApplication application;
	private FragmentTransaction fragmentTransaction;
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(GPSInfoMain.DEBUG,
			    "&3");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpsinfo_main);
		//setTheme(android.R.style.Theme_NoTitleBar);
		// Ŀ��ƴ �׼ǹ� �̿� Ÿ��Ʋ ����
		
		//getActionBar().setCustomView(R.layout.title);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getActionBar().setDisplayOptions(getActionBar().DISPLAY_HOME_AS_UP);
		//getActionBar().setDisplayShowTitleEnabled(false);
		//getActionBar().setDisplayShowHomeEnabled(false);
		//getActionBar().setDisplayShowCustomEnabled(false);

		ibTour = (ImageButton) findViewById(R.id.ib_tour);
		ibTour.setOnClickListener(this);

		ibCulture = (ImageButton) findViewById(R.id.ib_culture);
		ibCulture.setOnClickListener(this);

		ibCourse = (ImageButton) findViewById(R.id.ib_course);
		ibCourse.setOnClickListener(this);

		ibLeports = (ImageButton) findViewById(R.id.ib_leports);
		ibLeports.setOnClickListener(this);

		ibSleep = (ImageButton) findViewById(R.id.ib_sleep);
		ibSleep.setOnClickListener(this);

		ibFood = (ImageButton) findViewById(R.id.ib_food);
		ibFood.setOnClickListener(this);

		ib2000 = (ImageButton) findViewById(R.id.ib_2000);
		ib2000.setOnClickListener(this);

		ib5000 = (ImageButton) findViewById(R.id.ib_5000);
		ib5000.setOnClickListener(this);

		ib8000 = (ImageButton) findViewById(R.id.ib_8000);
		ib8000.setOnClickListener(this);

		ib10000 = (ImageButton) findViewById(R.id.ib_10000);
		ib10000.setOnClickListener(this);

		ib15000 = (ImageButton) findViewById(R.id.ib_15000);
		ib15000.setOnClickListener(this);

		ib20000 = (ImageButton) findViewById(R.id.ib_20000);
		ib20000.setOnClickListener(this);

		ibSearch = (ImageButton) findViewById(R.id.ib_search);
		ibSearch.setOnClickListener(this);

		// �⺻ ������
		contentTypeId = "12";
		distance = "2000";
		currentType = ibTour;
		currentType.setSelected(true);
		currentDistance = ib2000;
		currentDistance.setSelected(true);

		// ���α׷��� �� ����
		progressDialog = findViewById(R.id.progressDialog);
		progressDialog.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		fragmentManager = getFragmentManager();

		// ��Ʈ��ũ �Ŵ��� ���� (static �̹Ƿ�)
		NetworkMgr.release();
		tracker = new GPSTracker(this);
		networkMgr = NetworkMgr.getInstance(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// ��Ʈ��ũ ��û�� ���� ���� �޵��� ����
		networkMgr.changeNetworkListener(this);
		// gps tracker ����
		tracker.getLocation();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// gps tracker ����
		tracker.stopUsingGPS();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// ����
		case R.id.ib_tour:
			if (currentType == ibTour)
				return;
			contentTypeId = "12";
			currentType.setSelected(false);
			currentType = ibTour;
			currentType.setSelected(true);
			break;
		// ��ȭ
		case R.id.ib_culture:
			if (currentType == ibCulture)
				return;
			contentTypeId = "14";
			currentType.setSelected(false);
			currentType = ibCulture;
			currentType.setSelected(true);
			break;
		// �ڽ�
		case R.id.ib_course:
			if (currentType == ibCourse)
				return;
			contentTypeId = "25";
			currentType.setSelected(false);
			currentType = ibCourse;
			currentType.setSelected(true);
			break;
		// ������
		case R.id.ib_leports:
			if (currentType == ibLeports)
				return;
			contentTypeId = "28";
			currentType.setSelected(false);
			currentType = ibLeports;
			currentType.setSelected(true);
			break;
		// ����
		case R.id.ib_sleep:
			if (currentType == ibSleep)
				return;
			contentTypeId = "32";
			currentType.setSelected(false);
			currentType = ibSleep;
			currentType.setSelected(true);
			break;
		// ����
		case R.id.ib_food:
			if (currentType == ibFood)
				return;
			contentTypeId = "39";
			currentType.setSelected(false);
			currentType = ibFood;
			currentType.setSelected(true);
			break;
		// 2000m
		case R.id.ib_2000:
			if (currentType == ib2000)
				return;
			distance = "2000";
			currentDistance.setSelected(false);
			currentDistance = ib2000;
			currentDistance.setSelected(true);
			break;
		// 5000m
		case R.id.ib_5000:
			if (currentType == ib5000)
				return;
			distance = "5000";
			currentDistance.setSelected(false);
			currentDistance = ib5000;
			currentDistance.setSelected(true);

			break;
		// 8000m
		case R.id.ib_8000:
			if (currentType == ib8000)
				return;
			distance = "8000";
			currentDistance.setSelected(false);
			currentDistance = ib8000;
			currentDistance.setSelected(true);

			break;
		// 10000m
		case R.id.ib_10000:
			if (currentType == ib10000)
				return;
			distance = "10000";
			currentDistance.setSelected(false);
			currentDistance = ib10000;
			currentDistance.setSelected(true);

			break;
		// 15000m
		case R.id.ib_15000:
			if (currentType == ib15000)
				return;
			distance = "15000";
			currentDistance.setSelected(false);
			currentDistance = ib15000;
			currentDistance.setSelected(true);

			break;
		// 20000m
		case R.id.ib_20000:
			if (currentType == ib20000)
				return;
			distance = "20000";
			currentDistance.setSelected(false);
			currentDistance = ib20000;
			currentDistance.setSelected(true);

			break;
		// �˻�
		case R.id.ib_search:

			// ���� gps�� �̿� �������� �ʴ� ���¶�� �˾��� ����
			if (!tracker.isGpsEnable()) {
				onPopupShow(PopupFragment.REQUEST_GPS_UNABLE);
				return;
			}
			// ����� �� gsp �����͸� ���� ���� ��� �˾��� ����
			if (tracker.getLatitude() == 0 || tracker.getLongitude() == 0) {
				tracker.getLocation();
				onPopupShow(PopupFragment.REQUEST_GPS_WAIT);
				return;
			}

			showProgressDialog();

			// ����, �浵 ����
			longitude = String.valueOf(tracker.getLongitude());
			latitude = String.valueOf(tracker.getLatitude());
			// ��Ʈ��ũ�� ���Ͽ� ���� ���� �ٿ�ε�

			networkMgr.downloadTourData(contentTypeId, longitude, latitude,
					distance, "1");
			break;
		}
	}

	/**
	 * ���α׷����ٸ� �����ش�
	 */
	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}

	/**
	 * ���α׷����ٸ� �����
	 */
	public void hideProgressDialog() {
		progressDialog.setVisibility(View.GONE);
	}

	@Override
	public void contentDownloadComplete(Map<String, Object> result) {
		// TODO Auto-generated method stub
		hideProgressDialog();
		if (result == null) {
			Log.d(GPSInfoMain.DEBUG,
					"contentDownloadComplete receive null data");
			return;
		}

		int request = (Integer) result.get("REQUEST");

		if (request == NetworkMgr.REQUEST_TOUR_LIST) {
			application = ((CustomApplication) getApplicationContext());
			application.setData(result);

			Intent intent = new Intent(this, TourListActivity.class);
			intent.putExtra("XCoord", longitude);
			intent.putExtra("YCoord", latitude);
			intent.putExtra("Radius", distance);
			intent.putExtra("ContentType", contentTypeId);
			Log.d(GPSInfoMain.DEBUG, "&100");
			startActivity(intent);
		}

	}

	@Override
	public void onPopupShow(int request) {
		// TODO Auto-generated method stub
		fragmentTransaction = fragmentManager.beginTransaction();
		PopupFragment prev = (PopupFragment) fragmentManager
				.findFragmentByTag("popup");
		if (prev != null) {
			fragmentTransaction.remove(prev);
		}

		PopupFragment newFragment = PopupFragment.newInstance(request);
		newFragment.show(fragmentTransaction, "popup");
	}

	@Override
	public void contentDownloadComplete(COMNTourData result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contentDownloadComplete(ArrayList<String> result) {
		// TODO Auto-generated method stub
		
	}
}
