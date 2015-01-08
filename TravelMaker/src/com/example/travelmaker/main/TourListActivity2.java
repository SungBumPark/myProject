package com.example.travelmaker.main;

import java.util.*;

import java.util.*;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.*;
import android.location.*;
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
import com.example.travelmaker.tourinfo.network.*;

public class TourListActivity2 extends ListActivity implements OnClickListener,
		OnScrollListener, NetworkListener {

	private CustomApplication application;
	private ArrayList<String> titleList; // 관광 정보 타이틀 리스트
	private ArrayList<TourData2> tourList; // 관광 정보 데이터 리스트
	private int page; // 현재 페이지
	private ImageButton ibPre; // 이전 버튼
	private TextView listTitle; // 검색 결과 타이틀
	private COMNTourData tourData;
	private TourAdapter2 adapter;
	private int totalPage; // 총 페이지
	private View footer; // 불러오는중 푸터
	private boolean mLockListView = false; // 리스트뷰 더 불러오기에 사용
	private View progressDialog;
	private NetworkMgr2 networkMgr2; // 네트워크 매니저
	private String contentId;
	private String contentTypeId; // 관광 정보 타입
	private String areacode;
	int currentItem;	// 현재 리스트뷰에 나타나있는 아이템 갯수
	private final String DO_TRACKING = "0";
	
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_list);
		Log.d(GPSInfoMain.DEBUG, "&27");
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.title_with_button);
		Log.d(GPSInfoMain.DEBUG, "&28");
		// x,y 좌표, 반경, 관광 정보 타입 설정
		Intent intent = getIntent();
		contentTypeId = intent.getStringExtra("contentTypeId");
		contentId = intent.getStringExtra("contentId");
		areacode = intent.getStringExtra("areaCode");
		Log.d(GPSInfoMain.DEBUG, "&29");
		ibPre = (ImageButton) findViewById(R.id.ib_pre);
		ibPre.setOnClickListener(this);
		listTitle = (TextView) findViewById(R.id.list_title);

		progressDialog = findViewById(R.id.progressDialog);
		Log.d(GPSInfoMain.DEBUG, "&30");
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
		Log.d(GPSInfoMain.DEBUG, "&31");
		

		getListView().setOnScrollListener(this);
		footer = View.inflate(this, R.layout.footer, null);
		Log.d(GPSInfoMain.DEBUG, "&33");
		// 1페이지는 이미 불러왔으므로 2페지부터 불러온다.
		page = 2;
		// 전체 list 개수
		int count = ((Integer) data.get("count")).intValue();
		if (count == 0) {
			return;
		}
		// 전체 페이지 계산
		totalPage = count % 10 == 0 ? count / 10 : count / 10 + 1;
		
		// 현재 검색 갯수 결과 계산
		if(page == totalPage)
			currentItem += ((Integer) data.get("count")).intValue() % 10 ;
		else
			currentItem = 10;
		listTitle.setText("총 " + data.get("count") + "곳이 검색되었습니다.  [" + currentItem + "/" + data.get("count") + "]");
		// 전체 페이지가 2 이상이면 리스트뷰에 푸터를 단다
		if (totalPage != 1) {
			getListView().addFooterView(footer);
		}

		tourList = (ArrayList<TourData2>) application.getData().get("list");
		Log.d(GPSInfoMain.DEBUG, "&34");
		titleList = new ArrayList<String>();
		for (TourData2 tourData : tourList) {
			titleList.add(tourData.getTitle());
			Log.d(GPSInfoMain.DEBUG, "&41" + "title : " + tourData.getTitle()
					);
			Log.d(GPSInfoMain.DEBUG, "&41" + "url : " + tourData.getImageUrl()
					);
			Log.d(GPSInfoMain.DEBUG, "&41" + "contentId : " + tourData.getContentId()
					);
		}

		adapter = new TourAdapter2(tourList, this);
		setListAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(GPSInfoMain.DEBUG, "&35");
		networkMgr2 = NetworkMgr2.getInstance(this);
		networkMgr2.changeNetworkListener(this);
		//networkMgr2.release();
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		if (position >= titleList.size())
			return;
		Log.d(GPSInfoMain.DEBUG,
			    "&+1");
		showProgressDialog();
		
		contentId = tourList.get(position).getContentId();
		Log.d(GPSInfoMain.DEBUG,
			    "@contentId  " + tourList.get(position).getContentId()
			    + "   @contentTypeId  "   + contentTypeId);
		networkMgr2.downloadCommonData(contentId, contentTypeId);
		Log.d(GPSInfoMain.DEBUG,
				"###download complete");
		
		
	}

	/**
	 * 네트워크를 통해 다음 페이지 관광 정보 데이터를 다운로드 받는다 다운로드 중에는 리스트뷰를 lock 시킨다
	 */
	private void addItems() {
		// item을 추가하는 동안에는 onScroll에서 addItems()를 호출하지 못하도록 한다.
		Log.d(GPSInfoMain.DEBUG,
				"+1");
		mLockListView = true;
		Map<String, Object> data = application.getData();
		
		if(page == totalPage)
			currentItem += ((Integer) data.get("count")).intValue() % 10 ;
		else
			currentItem += 10;
			 
		listTitle.setText("총 " + data.get("count") + "곳이 검색되었습니다.  [" + currentItem + "/" + data.get("count") + "]");
		networkMgr2.downloadTourData(contentTypeId, areacode,
				String.valueOf(page));
		Log.d(GPSInfoMain.DEBUG, "&36\npageNo : " + String.valueOf(page)
				+ "contentTypeId : " + contentTypeId + "\nareaCode : "
				+ areacode);
	}

	@Override
	public void contentDownloadComplete(Map<String, Object> result) {
		// TODO Auto-generated method stub
		Log.d(GPSInfoMain.DEBUG, "&100");
		hideProgressDialog();
		if (result == null) {
			Log.d(GPSInfoMain.DEBUG,
					"contentDownloadComplete receive null data");
			return;
		}

		int request = (Integer) result.get("REQUEST");
		// 관광 정보 데이터 다운로드인 경우
		if (request == NetworkMgr2.REQUEST_TOUR_LIST) {
			ArrayList<TourData2> tempList = (ArrayList<TourData2>) result
					.get("list");
			Log.d(GPSInfoMain.DEBUG, "&40" + result.get(listTitle));
			Log.d(GPSInfoMain.DEBUG, "&101");
			// 기존 리스트에 다운로드 받은 데이터를 추가한다
			tourList.addAll(tempList);
			for (TourData2 data : tempList){
				titleList.add(data.getTitle());
				Log.d(GPSInfoMain.DEBUG,
					    "&41"
			    		+ data.getTitle() + "    url:" + data.getImageUrl());
			}

			// 마지막 페이지인 경우 푸터를 제거한다
			if (page == totalPage && getListView().getFooterViewsCount() > 0)
				getListView().removeFooterView(footer);

			adapter.notifyDataSetChanged();
			page++;
			mLockListView = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int count = totalItemCount - visibleItemCount;
		if (firstVisibleItem >= count && totalItemCount != 0 && !mLockListView
				&& page <= totalPage) {
			Log.d(GPSInfoMain.DEBUG,
				    "&43");
			addItems();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

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
		Log.d(GPSInfoMain.DEBUG, "#20 "
				+ result.getMapX());
		intent.putExtra("mapy", result.getMapY());
		Log.d(GPSInfoMain.DEBUG, "#20 "
				+ result.getMapY());
		Log.d(GPSInfoMain.DEBUG, "#20 "
				+ result.getHomepage());
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
		//intent.putExtra("url", result);
		//Log.d(GPSInfoMain.DEBUG, "#20# "
				//+ result.get(0));
		
	}

}
