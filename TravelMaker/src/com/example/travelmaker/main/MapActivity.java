package com.example.travelmaker.main;

import java.util.ArrayList;
import java.util.Map;

import ktmap.android.map.Bounds;
import ktmap.android.map.Coord;
import ktmap.android.map.KMap;
import ktmap.android.map.MapEventListener;
import ktmap.android.map.Pixel;
import ktmap.android.map.overlay.Marker;
import ktmap.android.map.overlay.MarkersLayer;
import ktmap.android.map.overlay.Overlay;
import ktmap.android.map.overlay.OverlayEventListener;
import ktmap.android.map.overlay.Polyline;
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
import com.example.travelmaker.main.PopupFragment.*;
import com.example.travelmaker.tour.gpsinfomain.*;
import com.example.travelmaker.tour.gpsinfomain.R.*;

/**
 * 지도에 경로를 표시해주는 엑티비티
 */
public class MapActivity extends Activity implements MapEventListener,
		OverlayEventListener, NetworkListener, OnPopupShowListener {

	private KMap mapView;
	private MarkerLayerSample markerLayer = new MarkerLayerSample();
	private String startX, startY, endX, endY; // 시작, 도착지 좌표 x,y
	private String desTitle; // 도착지 마커 타이틀
	private Coord depatureCoord, destinationCoord, mediumCoord; // 시작, 도착지 좌표
	private ImageButton ib_Show_Tracking;
	private boolean set_selected;
	private String DO_TRACKING;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// 커서틈 액션바 이용 타이틀 설정
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.title_with_tracking_button);
		((ImageView) findViewById(R.id.custom_title2))
				.setBackgroundResource(R.drawable.map_navigation_bg);
		NetworkMgr.getInstance(this).changeNetworkListener(this);

		// 지도 뷰 설정
		mapView = (KMap) findViewById(R.id.map_view);
		mapView.setZoomLevel(10);
		mapView.dispatchMapEvent(this);

		mapView.getOverlays().add(markerLayer);
		markerLayer.dispatchOverlayEvent(this);

		Intent intent = getIntent();

		startX = intent.getStringExtra("SX");
		startY = intent.getStringExtra("SY");
		endX = intent.getStringExtra("EX");
		endY = intent.getStringExtra("EY");
		desTitle = intent.getStringExtra("Title");
		DO_TRACKING = intent.getStringExtra("DO_TRACKING");

		// 출발, 도착지 마커 설정
		

		destinationCoord = new Coord(Float.parseFloat(endX),
				Float.parseFloat(endY));
		Marker desMarker = new Marker(destinationCoord);
		desMarker.setTitle(desTitle);
		markerLayer.addItem(desMarker);
		// 네트워크 처리 후 호출되는 콜백 함수 contentDownloadComplete()가
		// MapActivity에서 호출되도록 변경

		findViewById(R.id.ib_pre2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		ib_Show_Tracking = (ImageButton) findViewById(R.id.ib_Show_Tracking);
		ib_Show_Tracking.setVisibility(View.VISIBLE);
		ib_Show_Tracking.setSelected(false);
		set_selected = false;
		desMarker.setIcon(getResources().getDrawable(R.drawable.red_icon3));

		ib_Show_Tracking.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (set_selected == false) {
					set_selected = true;
					ib_Show_Tracking.setSelected(true);
					doTracking();
				} else if (set_selected == true) {
					set_selected = false;
					ib_Show_Tracking.setSelected(false);
					//eraseTraking();
				}

			}
		});

	}

	public void initMap() {

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
		// TODO Auto-generatedMar method stub
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

			// 지도 초기화 성공 후, 출발지를 지도 중심으로 설정
			mapView.setMapCenter(destinationCoord);
			// 지도 초기화 성공 후, 경로 데이터 다운로드
			// NetworkMgr.getInstance(this).downloadMapInfo(startX, startY,
			// endX, endY);
		}

		return isSuccess;
	}

	public void doTracking() {
		depatureCoord = new Coord(Float.parseFloat(startX),
				Float.parseFloat(startY));
		mediumCoord = new Coord((Float.parseFloat(startX)+Float.parseFloat(endX))/2, 
				(Float.parseFloat(startY)+Float.parseFloat(endY))/2);
		Marker marker = new Marker(depatureCoord);
		marker.setTitle("현위치");
		markerLayer.addItem(marker);
		marker.setIcon(getResources().getDrawable(R.drawable.red_icon3));
		mapView.setMapCenter(mediumCoord);
		NetworkMgr.getInstance(this)
				.downloadMapInfo(startX, startY, endX, endY);
		mapView.setZoomLevel(7);
	}

	public void eraseTraking() {
		mapView.getOverlays().clear();
		Marker desMarker = new Marker(destinationCoord);
		destinationCoord = new Coord(Float.parseFloat(endX),
				Float.parseFloat(endY));
		desMarker.setTitle(desTitle);
		markerLayer.addItem(desMarker);
		mapView.setZoomLevel(10);
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
	public void contentDownloadComplete(Map<String, Object> result) {
		// TODO Auto-generated method stub
		if (result == null) {
			Log.d(GPSInfoMain.DEBUG,
					"contentDownloadComplete receive null data");
			return;
		}

		// 요청했던 네트워크 타입인지 확인
		int request = (Integer) result.get("REQUEST");
		if (request != NetworkMgr.REQUEST_ROUTE_SEARCH)
			return;

		ArrayList<Coord> vertexList = (ArrayList<Coord>) result
				.get("VERTEX_LIST");

		// 데이터 수신 실패
		if (vertexList == null) {
			Toast.makeText(this, "데이터 수신 실패", Toast.LENGTH_LONG).show();
			return;
		}
		// 비어있는 데이터 전달 받을시
		else if (vertexList.isEmpty()) {
			Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_LONG).show();
			return;
		}

		// 지도에 좌표값을 이용 선을 긋는다.
		Coord[] array = vertexList.toArray(new Coord[] {});
		mapView.getOverlays().add(new Polyline(array, 3, Color.RED));
		mapView.invalidate();
	}

	@Override
	public void onPopupShow(int request) {
		// TODO Auto-generated method stub

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
