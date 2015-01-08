package com.example.travelmaker.areainfo.network;

import java.io.*;
import java.net.*;
import java.util.*;

import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.gpsinfo.network.NetworkMgr.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.*;
import com.example.travelmaker.tour.gpsinfomain.*;
import com.example.travelmaker.tourinfo.network.*;

import android.app.*;
import android.os.*;
import android.util.*;

public class NetworkMgr2 {

	private volatile static NetworkMgr2 networkMgr2 = null;
	private NetworkListener networkListener = null;
	private Activity mCtx = null;
	private final String authValue;
	public static final int REQUEST_TOUR_LIST = 0;// 리스트뷰에 출력될 데이터 리스트 요청
	public static final int REQUEST_TOUR_INFO = 1;
	public static final int REQUEST_COMMON_INFO = 2; // 공통정보
	public static final int REQUEST_INTRODUCE_INFO = 3; // 소개정보
	public static final int REQUEST_IMAGE_INFO = 4; // 이미지

	private NetworkMgr2(Activity ctx) {
		mCtx = ctx;

		// Activity가 NetworkListener 구현했는지 확인
		if (ctx instanceof NetworkListener)
			networkListener = (NetworkListener) ctx;

		String base64EncodingAppID = Base64.encodeToString(
				mCtx.getString(R.string.app_id).getBytes(), Base64.DEFAULT)
				.trim();
		authValue = "Basic " + base64EncodingAppID;
		base64EncodingAppID = null;
	}

	public static NetworkMgr2 getInstance(Activity ctx) {
		if (networkMgr2 == null) {
			synchronized (NetworkMgr2.class) {
				if (networkMgr2 == null) {
					networkMgr2 = new NetworkMgr2(ctx);
				}
			}
		}
		return networkMgr2;
	}

	/**
	 * 주변 관광 데이터를 서버로부터 다운로드 받는다.
	 * 
	 * @param areacode
	 *            - 지역코드
	 * @param contentTypeId
	 *            - 컨텐츠 타입
	 * @param page
	 *            - 페이지
	 * @return - 성공 여부
	 */
	// NetworkMgr의 contentDownloadComplete()이 호출될 Activity 변경
	public void changeNetworkListener(Activity ctx) {
		// TODO Auto-generated method stub
		mCtx = ctx;
		if (ctx instanceof NetworkListener)
			networkListener = (NetworkListener) ctx;
		else
			Log.d(GPSInfoMain.DEBUG, "ctx is must implement NetworkListener");
	}

	public static void release() {
		networkMgr2 = null;
	}

	public boolean downloadTourData(String contentTypeId, String areacode,
			String page) {
		Log.d(GPSInfoMain.DEBUG, "&2");
		if (areacode == null)
			return false;
		new TourDownAndParsingTask(REQUEST_TOUR_LIST).execute(contentTypeId,
				areacode, page);
		return true;
	}

	/**
	 * 
	 * 관광지 데이터 다운로드
	 * 
	 */
	private class TourDownAndParsingTask extends
			AsyncTask<String, Void, Map<String, Object>> {
		int request;

		public TourDownAndParsingTask(int request) {
			Log.d(GPSInfoMain.DEBUG, "&3");
			this.request = request;
		}

		@Override
		protected Map<String, Object> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d(GPSInfoMain.DEBUG, "&4");
			try {
				switch (request) {
				// 좌표계 변환 요청
				case REQUEST_TOUR_LIST:
					return loadFeed(params);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return null;

		}

		public Map<String, Object> loadFeed(String... params) {
			// TODO Auto-generated method stub
			try {
				Log.d(GPSInfoMain.DEBUG, "&5");
				FeedParser2 parser = FeedParserFactory2.getParser(params);
				return parser.parse2();
			} catch (Throwable t) {
				Log.d(GPSInfoMain.DEBUG, t.getMessage(), t);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Map<String, Object> result) {
			// TODO Auto-generated method stub
			Log.d(GPSInfoMain.DEBUG, "&18");
			if (networkListener != null) {
				Log.d(GPSInfoMain.DEBUG, "&19");
				result.put("REQUEST", request);
				Log.d(GPSInfoMain.DEBUG, "&20");
				networkListener.contentDownloadComplete(result);
				Log.d(GPSInfoMain.DEBUG, "&21");
			} else
				Log.d(GPSInfoMain.DEBUG, "mCtx is not networkListener type");
		}

	}

	public boolean downloadCommonData(String contentId, String contentTypeId) {
		Log.d(GPSInfoMain.DEBUG, "#2");
		if (contentId == null || contentTypeId == null)
			return false;
		new COMNDataAndParsingTask(REQUEST_COMMON_INFO).execute(contentTypeId,
				contentId);
		return true;
	}

	

	/**
	 * 
	 * 
	 * 공통정보 데이터 다운로드
	 * 
	 */
	private class COMNDataAndParsingTask extends
			AsyncTask<String, Void, COMNTourData> {
		int request;

		public COMNDataAndParsingTask(int request) {
			Log.d(GPSInfoMain.DEBUG, "#4");
			this.request = request;
		}

		@Override
		protected COMNTourData doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				switch (request) {
				// 좌표계 변환 요청
				case REQUEST_COMMON_INFO:
					Log.d(GPSInfoMain.DEBUG, "#5" + "  content :   "
							+ params[0] + "  " + params[1]);
					return COMNloadFeed(params);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return null;
		}

		public COMNTourData COMNloadFeed(String... params) {
			// TODO Auto-generated method stub
			try {
				Log.d(GPSInfoMain.DEBUG, "#6");
				FeedParser3 parser = COMNFeedParserFactory.getParser(params);
				return parser.parse3();
			} catch (Throwable t) {
				Log.d(GPSInfoMain.DEBUG, t.getMessage(), t);
			}
			return null;
		}

		@Override
		protected void onPostExecute(COMNTourData result) {
			// TODO Auto-generated method stub
			Log.d(GPSInfoMain.DEBUG, "&18");
			if (networkListener != null) {
				Log.d(GPSInfoMain.DEBUG, "&19");
				result.setResult(result);

				Log.d(GPSInfoMain.DEBUG, "&20");
				networkListener.contentDownloadComplete(result);
			} else
				Log.d(GPSInfoMain.DEBUG, "mCtx is not networkListener type");
		}

	}
	
	public boolean downloadImgData(String contentTypeId, String contentId) {
		Log.d(GPSInfoMain.DEBUG, "#2");
		if (contentId == null || contentTypeId == null)
			return false;
		new ImgDataAndParsingTask(REQUEST_IMAGE_INFO).execute(contentTypeId, contentId);
		return true;
	}
	
	/**
	 * 
	 * 추가 Image URL 데이터
	 *
	 */
	private class ImgDataAndParsingTask extends
			AsyncTask<String, Void, ArrayList<String>> {
		int request;

		public ImgDataAndParsingTask(int request) {
			Log.d(GPSInfoMain.DEBUG, "#4");
			this.request = request;
		}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				switch (request) {
				// 좌표계 변환 요청
				case REQUEST_IMAGE_INFO:
					Log.d(GPSInfoMain.DEBUG, "#5" + "  contentTypeId :   " + params[0]
						+ "contentId :  " + params[1]);
					return ImgloadFeed(params);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return null;
		}

		public ArrayList<String> ImgloadFeed(String... params) {
			// TODO Auto-generated method stub
			try {
				Log.d(GPSInfoMain.DEBUG, "#6");
				FeedParser4 parser = ImgFeedParserFactory.getParser(params);
				Log.d(GPSInfoMain.DEBUG, "#9");
				return parser.parse4();
			} catch (Throwable t) {
				Log.d(GPSInfoMain.DEBUG, t.getMessage(), t);
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			Log.d(GPSInfoMain.DEBUG, "&18");
			if (networkListener != null) {
				Log.d(GPSInfoMain.DEBUG, "&19");

				Log.d(GPSInfoMain.DEBUG, "&20");
				networkListener.contentDownloadComplete(result);
			} else
				Log.d(GPSInfoMain.DEBUG, "mCtx is not networkListener type");
		}


	}

}
