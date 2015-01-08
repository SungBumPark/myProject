package com.example.travelmaker.main;

import java.util.*;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

import com.example.travelmaker.areainfo.network.*;
import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.info.gps.*;
import com.example.travelmaker.main.PopupFragment.*;
import com.example.travelmaker.tour.gpsinfomain.*;

public class AreaInfoMain extends Activity implements OnClickListener,
		NetworkListener, OnPopupShowListener {

	public static final String DEBUG = "Travel_Maker";
	private View progressDialog;
	private NetworkMgr2 networkMgr2; // ��Ʈ��ũ �Ŵ���
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
	private ImageButton ibSeoul;
	private ImageButton ibIncheon;
	private ImageButton ibDaejeon;
	private ImageButton ibDaegu;
	private ImageButton ibGwangju;
	private ImageButton ibBusan;
	private ImageButton ibUlsan;
	private ImageButton ibGyeongki;
	private ImageButton ibGangwon;
	private ImageButton ibSearch;
	private View currentArea, currentType; // ���� �Ÿ� , ���� �˻� Ÿ��
	private String contentTypeId, areaCode; // ������id, �����ڵ�
	private String contentId;
	private CustomApplication application;

	private FragmentTransaction fragmentTransaction;
	private FragmentManager fragmentManager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_areainfo_main);
		Log.d(GPSInfoMain.DEBUG, "&11");
		// Ŀ��ƴ �׼ǹ� �̿� Ÿ��Ʋ ����
		// getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		// getActionBar().setCustomView(R.layout.title);

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

		ibSeoul = (ImageButton) findViewById(R.id.ib_Seoul);
		ibSeoul.setOnClickListener(this);

		ibIncheon = (ImageButton) findViewById(R.id.ib_Incheon);
		ibIncheon.setOnClickListener(this);

		ibDaejeon = (ImageButton) findViewById(R.id.ib_Daejeon);
		ibDaejeon.setOnClickListener(this);

		ibDaegu = (ImageButton) findViewById(R.id.ib_Daegu);
		ibDaegu.setOnClickListener(this);

		ibGwangju = (ImageButton) findViewById(R.id.ib_Gwangju);
		ibGwangju.setOnClickListener(this);

		ibBusan = (ImageButton) findViewById(R.id.ib_Busan);
		ibBusan.setOnClickListener(this);

		ibUlsan = (ImageButton) findViewById(R.id.ib_Ulsan);
		ibUlsan.setOnClickListener(this);

		ibGyeongki = (ImageButton) findViewById(R.id.ib_Gyeongki);
		ibGyeongki.setOnClickListener(this);

		ibGangwon = (ImageButton) findViewById(R.id.ib_Gangwon);
		ibGangwon.setOnClickListener(this);

		ibSearch = (ImageButton) findViewById(R.id.ib_search);
		ibSearch.setOnClickListener(this);

		// �⺻ ������
		contentTypeId = "12";
		areaCode = "1";
		currentType = ibTour;
		currentType.setSelected(true);
		currentArea = ibSeoul;
		currentArea.setSelected(true);

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

		networkMgr2 = NetworkMgr2.getInstance(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// ��Ʈ��ũ ��û�� ���� ���� �޵��� ����
		networkMgr2.changeNetworkListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
		// ����
		case R.id.ib_Seoul:
			if (currentType == ibSeoul)
				return;
			areaCode = "1";
			currentArea.setSelected(false);
			currentArea = ibSeoul;
			currentArea.setSelected(true);
			break;
		// ��õ
		case R.id.ib_Incheon:
			if (currentType == ibIncheon)
				return;
			areaCode = "2";
			currentArea.setSelected(false);
			currentArea = ibIncheon;
			currentArea.setSelected(true);

			break;
		// ����
		case R.id.ib_Daejeon:
			if (currentType == ibDaejeon)
				return;
			areaCode = "3";
			currentArea.setSelected(false);
			currentArea = ibDaejeon;
			currentArea.setSelected(true);

			break;
		// �뱸
		case R.id.ib_Daegu:
			if (currentType == ibDaegu)
				return;
			areaCode = "4";
			currentArea.setSelected(false);
			currentArea = ibDaegu;
			currentArea.setSelected(true);

			break;
		// ����
		case R.id.ib_Gwangju:
			if (currentType == ibGwangju)
				return;
			areaCode = "5";
			currentArea.setSelected(false);
			currentArea = ibGwangju;
			currentArea.setSelected(true);
			break;
		// �λ�
		case R.id.ib_Busan:
			if (currentType == ibBusan)
				return;
			areaCode = "6";
			currentArea.setSelected(false);
			currentArea = ibBusan;
			currentArea.setSelected(true);
			break;
		// ���
		case R.id.ib_Ulsan:
			if (currentType == ibUlsan)
				return;
			areaCode = "39";
			currentArea.setSelected(false);
			currentArea = ibUlsan;
			currentArea.setSelected(true);
			break;
		// ��⵵
		case R.id.ib_Gyeongki:
			if (currentType == ibGyeongki)
				return;
			areaCode = "31";
			currentArea.setSelected(false);
			currentArea = ibGyeongki;
			currentArea.setSelected(true);
			break;
		// ������
		case R.id.ib_Gangwon:
			if (currentType == ibGangwon)
				return;
			areaCode = "32";
			currentArea.setSelected(false);
			currentArea = ibGangwon;
			currentArea.setSelected(true);
			break;
		// �˻�
		case R.id.ib_search:
			showProgressDialog();
			Log.d(GPSInfoMain.DEBUG, "&1");
			networkMgr2.downloadTourData(contentTypeId, areaCode, "1");
			break;
		/*
		 * // ���ͳ��� ������� ���ϸ� if(NetworkUtil.isOnline(this) == false) {
		 * onPopupShow(PopupFragment.REQUEST_INTERNET_UNABLE); }
		 */
		}

	}

	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPopupShow(int request) {
		// TODO Auto-generated method stub

	}

	public void hideProgressDialog() {
		progressDialog.setVisibility(View.GONE);
	}

	@Override
	public void contentDownloadComplete(Map<String, Object> result) {
		// TODO Auto-generated method stub
		Log.d(GPSInfoMain.DEBUG, "&22");
		hideProgressDialog();
		Log.d(GPSInfoMain.DEBUG, "&23");
		if (result == null) {
			Log.d(GPSInfoMain.DEBUG,
					"contentDownloadComplete receive null data");
			return;
		}

		int request = (Integer) result.get("REQUEST");
		if (request == NetworkMgr2.REQUEST_TOUR_LIST) {
			Log.d(GPSInfoMain.DEBUG, "&24");
			application = ((CustomApplication) getApplicationContext());
			application.setData(result);
			Log.d(GPSInfoMain.DEBUG, "&25");
			Intent intent = new Intent(this, TourListActivity2.class);
			intent.putExtra("contentId", contentId);
			intent.putExtra("contentTypeId", contentTypeId);
			intent.putExtra("areaCode", areaCode);
			Log.d(GPSInfoMain.DEBUG, "&26");
			startActivity(intent);
		}
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
