package com.example.travelmaker.main;

import java.util.ArrayList;
import java.util.Map;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.travelmaker.areainfo.network.*;
import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.info.adapter.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.tab.*;
import com.example.travelmaker.tour.gpsinfomain.*;

/**
 * 주변 관광 정보 출력 리스트 엑티비티
 */
public class TourListActivity extends ListActivity implements OnClickListener,
		OnScrollListener, NetworkListener {

	private CustomApplication application;
	private ArrayList<String> titleList; // 관광 정보 타이틀 리스트
	private ArrayList<String> contentIdList;
	private ArrayList<TourData> tourList; // 관광 정보 데이터 리스트
	private int page; // 현재 페이지
	private ImageButton ibPre; // 이전 버튼
	private TextView listTitle; // 검색 결과 타이틀
	private TourAdapter adapter;
	private int totalPage; // 총 페이지
	private View footer; // 불러오는중 푸터
	private boolean mLockListView = false; // 리스트뷰 더 불러오기에 사용
	private View progressDialog;
	private NetworkMgr networkMgr; // 네트워크 매니저
	private NetworkMgr2 networkMgr2;
	private String desTitle; // 선택한 리스트 아이템 타이틀 ( 목적지 이름)
	private String radius; // 반경
	private String contentTypeId; // 관광 정보 타입
	private String contentId;		// 관광지 정보 ID
	private String currentXCoord; // 좌표계 변환전 X 좌표(WGS84)
	private String currentYCoord; // 좌표계 변환전 Y 좌표(WGS84)
	private String startX, startY, endX, endY; // 좌표 변환 후 현재, 목적지 좌표 (UTMK)
	private final String DO_TRACKING = "1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(GPSInfoMain.DEBUG, "&101");
		setContentView(R.layout.activity_tour_list);

		// 커스텀 액션바 이용 타이틀 설정
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.title_with_button);

		// x,y 좌표, 반경, 관광 정보 타입 설정
		Intent intent = getIntent();
		currentXCoord = intent.getStringExtra("XCoord");
		currentYCoord = intent.getStringExtra("YCoord");
		radius = intent.getStringExtra("Radius");
		contentTypeId = intent.getStringExtra("ContentType");

		ibPre = (ImageButton) findViewById(R.id.ib_pre);
		ibPre.setOnClickListener(this);
		listTitle = (TextView) findViewById(R.id.list_title);

		// 프로그레스 바 설정
		progressDialog = findViewById(R.id.progressDialog);
		progressDialog.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		// Custom Application을 이용 엑티비티간 데이터 전달
		application = (CustomApplication) getApplicationContext();
		Map<String, Object> data = application.getData();

		int count = ((Integer) data.get("count")).intValue();
		listTitle.setText("총 " + data.get("count") + "곳이 검색되었습니다.");

		if (count == 0) {
			return;
		}

		getListView().setOnScrollListener(this);
		footer = View.inflate(this, R.layout.footer, null);

		// 1페이지는 이미 불러왔으므로 2페지부터 불러온다.
		page = 2;
		// 전체 페이지 계산
		totalPage = count % 10 == 0 ? count / 10 : count / 10 + 1;
		// 전체 페이지가 2 이상이면 리스트뷰에 푸터를 단다
		if (totalPage != 1) {
			getListView().addFooterView(footer);
		}

		tourList = (ArrayList<TourData>) application.getData().get("list");
		titleList = new ArrayList<String>();
		for (TourData tourData : tourList) {
			titleList.add(tourData.getTitle());
			Log.d(GPSInfoMain.DEBUG, "&41" + "contentId : " + tourData.getContentId()
					);
			//contentIdList.add(tourData.getContentId());
		}

		adapter = new TourAdapter(tourList, this);
		setListAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// 네트워크 요청에 대한 응답을 받도록 설정
		networkMgr = NetworkMgr.getInstance(this);
		networkMgr.changeNetworkListener(this);
		networkMgr2 = NetworkMgr2.getInstance(this);
		networkMgr2.changeNetworkListener(this);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		if (position >= titleList.size())
			return;

		showProgressDialog();

		contentId = tourList.get(position).getContentId();
		Log.d(GPSInfoMain.DEBUG,
			    "@contentId  " + contentId
			    + "   @contentTypeId  "   + tourList.get(position).getContentId());
		networkMgr2.downloadCommonData(contentId, contentTypeId);
		Log.d(GPSInfoMain.DEBUG,
				"###download complete");
		
		//desTitle = titleList.get(position);
		// 좌표 변환 네트워크 요청
		//networkMgr.convertTargetCoord(tourList.get(position).getxCoord(),
				//tourList.get(position).getyCoord());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_pre:
			finish();
			break;
		}

	}

	/**
	 * 네트워크를 통해 다음 페이지 관광 정보 데이터를 다운로드 받는다 다운로드 중에는 리스트뷰를 lock 시킨다
	 */
	private void addItems() {
		// item을 추가하는 동안에는 onScroll에서 addItems()를 호출하지 못하도록 한다.
		mLockListView = true;

		networkMgr.downloadTourData(contentTypeId, currentXCoord,
				currentYCoord, radius, String.valueOf(page));
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int count = totalItemCount - visibleItemCount;
		if (firstVisibleItem >= count && totalItemCount != 0 && !mLockListView
				&& page <= totalPage) {
			addItems();
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
		
		/*
		hideProgressDialog();
		Log.d(GPSInfoMain.DEBUG, "&102");
		if (result == null) {
			Log.d(GPSInfoMain.DEBUG,
					"contentDownloadComplete receive null data");
			return;
		}
		// 목적지 좌표의 변환인 경우 (WGS84 -> UTM-K)
		if (request == NetworkMgr.REQUEST_TARGET_CONVERT_COORD) {
			String COORDTYPE = (String) result.get("COORDTYPE");
			endX = (String) result.get("X");
			endY = (String) result.get("Y");

			// 목적지 좌표 변환후 현재 좌표 변환을 한다
			networkMgr.convertCurrentCoord(currentXCoord, currentYCoord);
		}
		// 현재 좌표의 변환인 경우(WGS84 -> UTM-K)
		else if (request == NetworkMgr.REQUEST_CURRENT_CONVERT_COORD) {
			String COORDTYPE = (String) result.get("COORDTYPE");
			startX = (String) result.get("X");
			startY = (String) result.get("Y");

			// 변환된 현재 좌표와 목적지 좌표를 이용 지도에 경로를 출력한다
			Intent intent = new Intent(this, MapActivity.class);
			intent.putExtra("SX", startX);
			intent.putExtra("SY", startY);
			intent.putExtra("EX", endX);
			intent.putExtra("EY", endY);
			intent.putExtra("DES_TITLE", desTitle);
			startActivity(intent);
		}*/
		int request = (Integer) result.get("REQUEST");
		// 관광 정보 데이터 다운로드인 경우
		if (request == NetworkMgr.REQUEST_TOUR_LIST) {
			ArrayList<TourData> tempList = (ArrayList<TourData>) result
					.get("list");
			// 기존 리스트에 다운로드 받은 데이터를 추가한다
			tourList.addAll(tempList);
			for (TourData data : tempList)
				titleList.add(data.getTitle());

			// 마지막 페이지인 경우 푸터를 제거한다
			if (page == totalPage && getListView().getFooterViewsCount() > 0)
				getListView().removeFooterView(footer);

			adapter.notifyDataSetChanged();
			page++;
			mLockListView = false;
		}
	}

	@Override
	public void contentDownloadComplete(COMNTourData result) {
		// TODO Auto-generated method stub
		Log.d(GPSInfoMain.DEBUG, "#20 "
				+ result.getTitle());
		
		Intent intent = new Intent(this, InfoTab.class);
		intent.putExtra("addr1", result.getAddress1());
		intent.putExtra("addr2", result.getAddress2());
		intent.putExtra("overview", result.getOverView());
		intent.putExtra("title", result.getTitle());
		intent.putExtra("tel", result.getTel());
		intent.putExtra("zipcode", result.getZipCode());
		intent.putExtra("imgURL", result.getImageUrl());
		intent.putExtra("homepage", result.getHomepage());
		intent.putExtra("mapx", result.getMapX());
		intent.putExtra("SX", currentXCoord);
		intent.putExtra("SY", currentYCoord);
		Log.d(GPSInfoMain.DEBUG, "#20 "
				+ currentXCoord);
		intent.putExtra("mapy", result.getMapY());
		Log.d(GPSInfoMain.DEBUG, "#20 "
				+ result.getMapY());
		intent.putExtra("contentTypeId", contentTypeId);
		intent.putExtra("contentId", contentId);
		intent.putExtra("DO_TRACKING", DO_TRACKING);
		Log.d(GPSInfoMain.DEBUG, "(1) DO_TRACKING : "
				+ DO_TRACKING);
		startActivity(intent);
		
		hideProgressDialog();
	}

	@Override
	public void contentDownloadComplete(ArrayList<String> result) {
		// TODO Auto-generated method stub
		
	}
}
