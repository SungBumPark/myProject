package com.example.travelmaker.scrap;

import java.util.*;

import android.app.*;
import android.content.*;
import android.database.*;
import android.os.*;
import android.text.*;
import android.text.method.*;
import android.text.util.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import com.example.travelmaker.areainfo.network.*;
import com.example.travelmaker.calendar.*;
import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.image.*;
import com.example.travelmaker.info.adapter.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.*;
import com.example.travelmaker.tour.gpsinfomain.*;

public class ScrapInfoView extends Activity implements OnClickListener,
		NetworkListener {

	private String addr1, addr2, overview, title, tel, zipcode, imgURL,
			homepage, EX, EY, contentTypeId, contentId;
	ImageView goCOMNDataView, goImgView, goIntroDataView;
	View comnDataView, detatilView, imgView;
	ImageButton mapView, scrapView;
	COMNTourData COMNTourData;
	TextView TitleView, AddView, ZipView, TelView, HomepageView, overView;
	private ImgTourAdapter adapter;
	ArrayList<String> imgurl;
	LinearLayout homepageView;
	ImageMgr imageMgr;
	ImageView image, imageView1, imageView2, imageView3, imageView4,
			imageView5, imageView6;
	NetworkMgr networkMgr;
	NetworkMgr2 networkMgr2;
	private View progressDialog;
	final private String DO_TRACKING = "0";
	Context mContext = this;
	Intent intent;
	DbHandler dbHandler;
	Cursor cursor = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scrap_tab_main);
		Log.d(GPSInfoMain.DEBUG, "z1");
		// 상단 버튼 등록
		goCOMNDataView = (ImageView) findViewById(R.id.goScrapDataView_btn);
		goIntroDataView = (ImageView) findViewById(R.id.goIntroDataView_btn);
		goImgView = (ImageView) findViewById(R.id.goImgView_btn);
		goCOMNDataView.setOnClickListener(this);
		Log.d(GPSInfoMain.DEBUG, "z2");
		goIntroDataView.setOnClickListener(this);
		goImgView.setOnClickListener(this);

		// 각 그룹 뷰 등록
		Log.d(GPSInfoMain.DEBUG, "z3");
		comnDataView = (View) findViewById(R.id.ScrapDataView);
		detatilView = (View) findViewById(R.id.poiMapXml);
		imgView = (View) findViewById(R.id.ImgDataView);
		networkMgr = NetworkMgr.getInstance(this);
		networkMgr.changeNetworkListener(this);

		networkMgr2 = NetworkMgr2.getInstance(this);
		networkMgr2.changeNetworkListener(this);
		progressDialog = findViewById(R.id.progressDialog);
		Log.d(GPSInfoMain.DEBUG, "z4");
		intent = getIntent();
		Log.d(GPSInfoMain.DEBUG, "z5");
		initComDataView(intent);
	}

	private void initComDataView(Intent intent) {
		Log.d(GPSInfoMain.DEBUG, "z6");
		comnDataView.setVisibility(View.VISIBLE);
		Log.d(GPSInfoMain.DEBUG, "z6");
		detatilView.setVisibility(View.GONE);
		Log.d(GPSInfoMain.DEBUG, "z6");
		imgView.setVisibility(View.GONE);
		Log.d(GPSInfoMain.DEBUG, "z7");
		goCOMNDataView.setImageResource(R.drawable.info_btn_press);
		goIntroDataView.setImageResource(R.drawable.intro_btn);
		goImgView.setImageResource(R.drawable.more_btn);

		addr1 = intent.getStringExtra("addr1");
		addr2 = intent.getStringExtra("addr2");
		overview = intent.getStringExtra("overview");
		title = intent.getStringExtra("title");
		tel = intent.getStringExtra("tel");
		zipcode = intent.getStringExtra("zipcode");
		imgURL = intent.getStringExtra("imageUrl");
		homepage = intent.getStringExtra("homepage");
		contentId = intent.getStringExtra("contentId");
		EX = intent.getStringExtra("EX");
		EY = intent.getStringExtra("EY");
		Log.d(GPSInfoMain.DEBUG, "DO_TRACKING : " + DO_TRACKING);
		Log.d(GPSInfoMain.DEBUG, "z8 ");

		// 프로그레스 바 설정

		contentTypeId = intent.getStringExtra("contentTypeId");
		Log.d(GPSInfoMain.DEBUG, "z9 ");
		TitleView = (TextView) findViewById(R.id.TextTitle_info);
		TitleView.setText(title);

		if (zipcode == null)
			zipcode = " ";
		ZipView = (TextView) findViewById(R.id.zipCodeView);
		ZipView.setText("우편번호 : " + Html.fromHtml(zipcode));

		if (addr1 == null)
			addr1 = " ";
		if (addr2 == null)
			addr2 = " ";
		AddView = (TextView) findViewById(R.id.addressView);
		AddView.setText("주소 : " + Html.fromHtml(addr1) + " "
				+ Html.fromHtml(addr2));

		if (tel == null)
			tel = " ";
		TelView = (TextView) findViewById(R.id.telView);
		TelView.setText("전화번호 : " + Html.fromHtml(tel));
		Linkify.addLinks(TelView, Linkify.PHONE_NUMBERS);

		imageMgr = ImageMgr.getInstance(this);
		image = (ImageView) findViewById(R.id.imageView);
		Log.d(GPSInfoMain.DEBUG, "^2 " + imgURL);

		if (imgURL == null) {
			imageMgr.displayImage("http://i.imgur.com/5x7Hv9G.png", image);
		} else
			imageMgr.displayImage(imgURL, image);

		mapView = (ImageButton) findViewById(R.id.mapButton);
		mapView.setOnClickListener(this);

		homepageView = (LinearLayout) findViewById(R.id.homepageView_bg);
		if (homepage == null)
			homepageView.setVisibility(View.GONE);
		else {
			HomepageView = (TextView) findViewById(R.id.homepageView);
			HomepageView.setText(" - 홈페이지 - \n" + Html.fromHtml(homepage));
			Linkify.addLinks(HomepageView, Linkify.WEB_URLS);
			HomepageView.setMovementMethod(LinkMovementMethod.getInstance());
		}

		overView = (TextView) findViewById(R.id.overView);
		overView.setText(Html.fromHtml(overview));

	}

	private void initIntroDataView() {
		comnDataView.setVisibility(View.GONE);
		detatilView.setVisibility(View.VISIBLE);
		imgView.setVisibility(View.GONE);
		goCOMNDataView.setImageResource(R.drawable.info_btn);
		goIntroDataView.setImageResource(R.drawable.intro_btn_press);
		goImgView.setImageResource(R.drawable.more_btn);
	}

	private void initImgView(Intent intent) {
		comnDataView.setVisibility(View.GONE);
		detatilView.setVisibility(View.GONE);
		imgView.setVisibility(View.VISIBLE);
		goCOMNDataView.setImageResource(R.drawable.info_btn);
		goIntroDataView.setImageResource(R.drawable.intro_btn);
		goImgView.setImageResource(R.drawable.more_btn_press);

		contentTypeId = intent.getStringExtra("contentTypeId");
		contentId = intent.getStringExtra("contentId");
		networkMgr2.downloadImgData(contentTypeId, contentId);
	}

	private void getMapDataWithoutTracking(String DesX, String DesY) {
		Log.d(GPSInfoMain.DEBUG, "^2 " + title);
		networkMgr.convertTargetCoord(EX, EY);
	}

	@Override
	public void contentDownloadComplete(Map<String, Object> result) {
		// TODO Auto-generated method stub
		String COORDTYPE;
		if (result == null) {
			Log.d(GPSInfoMain.DEBUG,
					"contentDownloadComplete receive null data");
			return;
		}
		int request = (Integer) result.get("REQUEST");
		Log.d(GPSInfoMain.DEBUG, "^10 Request :  " + request);

		// 목적지 좌표와 현재 좌표를 변환한다
		if (request == NetworkMgr.REQUEST_TARGET_CONVERT_COORD) {
			COORDTYPE = (String) result.get("COORDTYPE");
			Log.d(GPSInfoMain.DEBUG, "^11 COORDTYPE : " + COORDTYPE);
			EX = (String) result.get("X");
			EY = (String) result.get("Y");

			// 목적지 좌표 변환후 현위치찾기면 현재 좌표 변환을 한다
			Intent j = new Intent(this, MapActivity2.class);
			j.putExtra("EX", EX);
			Log.d(GPSInfoMain.DEBUG, "Before intent to map EX :  " + EX);
			j.putExtra("EY", EY);
			Log.d(GPSInfoMain.DEBUG, "Before intent to map EY :  " + EY);
			j.putExtra("Title", title);
			Log.d(GPSInfoMain.DEBUG, "Before intent to map Title :  " + title);
			startActivity(j);
			hideProgressDialog();
			// }

		}
		// 현재 좌표의 변환인 경우(WGS84 -> UTM-K)
		else if (request == NetworkMgr.REQUEST_CURRENT_CONVERT_COORD) {
			COORDTYPE = (String) result.get("COORDTYPE");
			// 변환된 현재 좌표와 목적지 좌표를 이용 지도에 경로를 출력한다
			Intent i = new Intent(this, MapActivity.class);
			i.putExtra("EX", EX);
			Log.d(GPSInfoMain.DEBUG, "Before intent to map EX :  " + EX);
			i.putExtra("EY", EY);
			Log.d(GPSInfoMain.DEBUG, "Before intent to map EY :  " + EY);
			i.putExtra("Title", title);
			Log.d(GPSInfoMain.DEBUG, "Before intent to map Title :  " + title);
			hideProgressDialog();
			startActivity(i);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.goScrapDataView_btn:
			initComDataView(intent);
			break;

		case R.id.goIntroDataView_btn:
			initIntroDataView();
			break;

		case R.id.goImgView_btn:
			initImgView(intent);
			break;
		case R.id.mapButton:
			showProgressDialog();
			Log.d(GPSInfoMain.DEBUG, "^1 " + title);
			// TRACKING 안할 때
			if (Integer.parseInt(DO_TRACKING) == 0)
				getMapDataWithoutTracking(EX, EY);
			break;
		}

	}

	@Override
	public void contentDownloadComplete(COMNTourData result) {
		// TODO Auto-generated method stub

	}

	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}

	public void hideProgressDialog() {
		progressDialog.setVisibility(View.GONE);
	}

	@Override
	public void contentDownloadComplete(ArrayList<String> result) {
		// TODO Auto-generated method stub

		Log.d(GPSInfoMain.DEBUG, "^imgSize " + result.size());
		for (int i = 0; i < result.size() - 1; i++)
			Log.d(GPSInfoMain.DEBUG, "^imgUrl " + result.get(i));

		for (int i = 0; i <= (result.size() - 1) && i <= 5; i++) {
			switch (i) {
			case 0:
				imageView1 = (ImageView) findViewById(R.id.imageView1);
				imageView1.setVisibility(View.VISIBLE);
				imageMgr.displayImage(result.get(i), imageView1);
				break;
			case 1:
				imageView2 = (ImageView) findViewById(R.id.imageView2);
				imageView2.setVisibility(View.VISIBLE);
				imageMgr.displayImage(result.get(i), imageView2);
				break;
			case 2:
				imageView3 = (ImageView) findViewById(R.id.imageView3);
				imageView3.setVisibility(View.VISIBLE);
				imageMgr.displayImage(result.get(i), imageView3);
				break;
			case 3:
				imageView4 = (ImageView) findViewById(R.id.imageView4);
				imageView4.setVisibility(View.VISIBLE);
				imageMgr.displayImage(result.get(i), imageView4);
				break;
			case 4:
				imageView5 = (ImageView) findViewById(R.id.imageView5);
				imageView5.setVisibility(View.VISIBLE);
				imageMgr.displayImage(result.get(i), imageView5);
				break;
			case 5:
				imageView6 = (ImageView) findViewById(R.id.imageView6);
				imageView6.setVisibility(View.VISIBLE);
				imageMgr.displayImage(result.get(i), imageView6);
				break;

			}
		}

	}
}
