package com.example.travelmaker.main;

import java.util.ArrayList;
import java.util.Map;

import ktmap.android.map.Bounds;
import ktmap.android.map.Coord;
import ktmap.android.map.KMap;
import ktmap.android.map.MapEventListener;
import ktmap.android.map.Pixel;
import ktmap.android.map.overlay.*;
import android.R.layout;
import android.app.*;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.MapActivity.*;
import com.example.travelmaker.main.PopupFragment.*;
import com.example.travelmaker.tour.gpsinfomain.*;
import android.graphics.drawable.Drawable;

public class MapActivity2 extends Activity implements MapEventListener,
		OverlayEventListener, NetworkListener, OnPopupShowListener {

	private KMap mapView;
	private MarkerLayerSample markerLayer = new MarkerLayerSample();
	private String endX, endY; // 시작, 도착지 좌표 x,y
	private String desTitle; // 도착지 마커 타이틀
	private Coord destinationCoord; // 목적지 좌표
	private Drawable icon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_map);
		Log.d(GPSInfoMain.DEBUG, "^12 ");
		// 커서틈 액션바 이용 타이틀 설정
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.title_with_button);
		((ImageView) findViewById(R.id.custom_title))
				.setBackgroundResource(R.drawable.map_navigation_bg);

		findViewById(R.id.ib_pre).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// 네트워크 처리 후 호출되는 콜백 함수 contentDownloadComplete()가
		// MapActivity에서 호출되도록 변경
		NetworkMgr.getInstance(this).changeNetworkListener(this);
		Log.d(GPSInfoMain.DEBUG, "^13 ");
		// 지도 뷰 설정
		mapView = (KMap) findViewById(R.id.map_view);
		mapView.setZoomLevel(10);
		mapView.dispatchMapEvent(this);
		
		mapView.getOverlays().add(markerLayer);
		markerLayer.dispatchOverlayEvent(this);

		Intent intent = getIntent();
		endX = intent.getStringExtra("EX");
		endY = intent.getStringExtra("EY");
		desTitle = intent.getStringExtra("Title");
		Log.d(GPSInfoMain.DEBUG, "^14 ");
		// 출발, 도착지 마커 설정
		//startX = intent.getStringExtra("SX");
		//startY = intent.getStringExtra("SY");
		//desTitle = intent.getStringExtra("DES_TITLE");
		//depatureCoord = new Coord(Float.parseFloat(startX),
				//Float.parseFloat(startY));

		//Marker marker = new Marker(depatureCoord);
		//marker.setTitle("출발지");
		//markerLayer.addItem(marker);

		//getResources().getDrawable(R.drawable.blue_marker);
				//res.getDrawable(R.drawable.a);
		destinationCoord = new Coord(Float.parseFloat(endX),
				Float.parseFloat(endY));
		Marker desMarker = new Marker(destinationCoord);
		desMarker.setTitle(desTitle);
		markerLayer.addItem(desMarker);
		desMarker.setIcon(getResources().getDrawable(R.drawable.red_icon3));
		Log.d(GPSInfoMain.DEBUG, "Icon : " + desMarker.getIcon());
		
	}
	@Override
	public void onPopupShow(int request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contentDownloadComplete(Map<String, Object> result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contentDownloadComplete(COMNTourData result) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onDoubleTouch(Overlay arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onLongTouch(Overlay arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(Overlay arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onBoundsChange(Bounds arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onChangeZoomLevel(boolean arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTouch(Pixel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onLongTouch(Pixel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMapInitializing(boolean isSuccess) {
		// TODO Auto-generated method stub
		if (isSuccess) {

			AlertDialog m_ConnectionInfo = null;

			// 지도 초기화 성공 후, 목적지를 지도 중심으로 설정
			mapView.setMapCenter(destinationCoord);
		}

		return isSuccess;
	}

	@Override
	public boolean onMultiTouch(Pixel[] arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(Pixel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 마커를 관리하는 레이어 클래스
	 */
	class MarkerLayerSample extends MarkersLayer {
		ArrayList<Marker> list = new ArrayList<Marker>();

		@Override
		protected Marker getMarker(int index) {
			// TODO Auto-generated method stub
			if (index >= list.size())
				return null;

			return list.get(index);
		}

		@Override
		protected int size() {
			// TODO Auto-generated method stub
			return list.size();
		}

		public void addItem(Marker mark) {
			list.add(mark);
		}

		public void removeAll() {
			list = new ArrayList<Marker>();
		}

		@Override
		public void setVisible(boolean on) {
			// TODO Auto-generated method stub
			this.isVisible = on;
		}
	}

	@Override
	public void contentDownloadComplete(ArrayList<String> result) {
		// TODO Auto-generated method stub
		
	}

}
