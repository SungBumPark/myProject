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
 * 주변 관광지 검색 엑티비티
 */
public class GPSInfoMain extends Activity implements OnClickListener,
		NetworkListener, OnPopupShowListener {

	public static final String DEBUG = "Travel_Maker";
	private View progressDialog;
	private NetworkMgr networkMgr; // 네트워크 매니저
	private GPSTracker tracker; // 위치 트래커
	private String longitude, latitude; // 위도, 경도
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
	private View currentDistance, currentType; // 현재 거리 , 현재 검색 타입
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
		// 커서틈 액션바 이용 타이틀 설정
		
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

		// 기본 설정값
		contentTypeId = "12";
		distance = "2000";
		currentType = ibTour;
		currentType.setSelected(true);
		currentDistance = ib2000;
		currentDistance.setSelected(true);

		// 프로그레스 바 설정
		progressDialog = findViewById(R.id.progressDialog);
		progressDialog.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		fragmentManager = getFragmentManager();

		// 네트워크 매니저 해제 (static 이므로)
		NetworkMgr.release();
		tracker = new GPSTracker(this);
		networkMgr = NetworkMgr.getInstance(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 네트워크 요청에 대한 응답 받도록 설정
		networkMgr.changeNetworkListener(this);
		// gps tracker 시작
		tracker.getLocation();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// gps tracker 종료
		tracker.stopUsingGPS();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 관광
		case R.id.ib_tour:
			if (currentType == ibTour)
				return;
			contentTypeId = "12";
			currentType.setSelected(false);
			currentType = ibTour;
			currentType.setSelected(true);
			break;
		// 문화
		case R.id.ib_culture:
			if (currentType == ibCulture)
				return;
			contentTypeId = "14";
			currentType.setSelected(false);
			currentType = ibCulture;
			currentType.setSelected(true);
			break;
		// 코스
		case R.id.ib_course:
			if (currentType == ibCourse)
				return;
			contentTypeId = "25";
			currentType.setSelected(false);
			currentType = ibCourse;
			currentType.setSelected(true);
			break;
		// 레포츠
		case R.id.ib_leports:
			if (currentType == ibLeports)
				return;
			contentTypeId = "28";
			currentType.setSelected(false);
			currentType = ibLeports;
			currentType.setSelected(true);
			break;
		// 숙박
		case R.id.ib_sleep:
			if (currentType == ibSleep)
				return;
			contentTypeId = "32";
			currentType.setSelected(false);
			currentType = ibSleep;
			currentType.setSelected(true);
			break;
		// 음식
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
		// 검색
		case R.id.ib_search:

			// 현재 gps가 이용 가능하지 않는 상태라면 팝업을 띄운다
			if (!tracker.isGpsEnable()) {
				onPopupShow(PopupFragment.REQUEST_GPS_UNABLE);
				return;
			}
			// 제대로 된 gsp 데이터를 받지 못할 경우 팝업을 띄운다
			if (tracker.getLatitude() == 0 || tracker.getLongitude() == 0) {
				tracker.getLocation();
				onPopupShow(PopupFragment.REQUEST_GPS_WAIT);
				return;
			}

			showProgressDialog();

			// 위도, 경도 설정
			longitude = String.valueOf(tracker.getLongitude());
			latitude = String.valueOf(tracker.getLatitude());
			// 네트워크를 통하여 관광 정보 다운로드

			networkMgr.downloadTourData(contentTypeId, longitude, latitude,
					distance, "1");
			break;
		}
	}

	/**
	 * 프로그레스바르 보여준다
	 */
	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}

	/**
	 * 프로그레스바를 감춘다
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
