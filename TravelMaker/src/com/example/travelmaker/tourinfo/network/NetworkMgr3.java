package com.example.travelmaker.tourinfo.network;

import java.util.*;

import com.example.travelmaker.areainfo.network.*;
import com.example.travelmaker.tourinfo.network.*;
import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.gpsinfo.network.NetworkMgr.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.*;
import com.example.travelmaker.tour.gpsinfomain.*;
import com.example.travelmaker.gpsinfo.network.*;
import com.example.travelmaker.gpsinfo.network.NetworkMgr.*;
import com.example.travelmaker.main.*;
import com.example.travelmaker.tour.gpsinfomain.*;

import android.app.*;
import android.os.*;
import android.util.*;

import android.app.*;
import android.os.*;
import android.util.*;
import com.example.travelmaker.info.data.*;
public class NetworkMgr3 {

	private volatile static NetworkMgr3 networkMgr3 = null;
	private NetworkListener networkListener = null;
	private Activity mCtx = null;
	private final String authValue;
	public static final int REQUEST_COMMON_INFO = 0; // ��������
	public static final int REQUEST_INTRODUCE_INFO = 1; // �Ұ�����
	public static final int REQUEST_IMAGE_INFO = 2; // �Ұ�����

	public NetworkMgr3(Activity ctx) {
		// TODO Auto-generated constructor stub

		// Activity�� NetworkListener �����ߴ��� Ȯ��
		if (ctx instanceof NetworkListener)
			networkListener = (NetworkListener) ctx;

		String base64EncodingAppID = Base64.encodeToString(
				mCtx.getString(R.string.app_id).getBytes(), Base64.DEFAULT)
				.trim();
		authValue = "Basic " + base64EncodingAppID;
		base64EncodingAppID = null;
	}

	public static NetworkMgr3 getInstance(Activity ctx) {
		
		if (networkMgr3 == null) {
			synchronized (NetworkMgr3.class) {
				if (networkMgr3 == null) {
					networkMgr3 = new NetworkMgr3(ctx);
				}
			}
		}
		return networkMgr3;
	}

	/**
	 * �ֺ� ���� �����͸� �����κ��� �ٿ�ε� �޴´�.
	 * 
	 * @param areacode
	 *            - �����ڵ�
	 * @param contentTypeId
	 *            - ������ Ÿ��
	 * @param page
	 *            - ������
	 * @return - ���� ����
	 */

	// NetworkMgr�� contentDownloadComplete()�� ȣ��� Activity ����
	public void changeNetworkListener(Activity ctx) {
		// TODO Auto-generated method stub
		mCtx = ctx;
		if (ctx instanceof NetworkListener)
			networkListener = (NetworkListener) ctx;
		else
			Log.d(GPSInfoMain.DEBUG, "ctx is must implement NetworkListener");
	}

	public static void release() {
		networkMgr3 = null;
	}

	public boolean downloadCommonData(String contentId, String contentTypeId) {
		Log.d(GPSInfoMain.DEBUG, "#2");
		if (contentId == null || contentTypeId == null)
			return false;
		new TourDownAndParsingTask(REQUEST_COMMON_INFO).execute(contentTypeId,
				contentId);
		return true;
	}
	
	public boolean downloadIntroduceInfo(String contentId, String contentTypeId) {
		Log.d(GPSInfoMain.DEBUG, "&2");
		if (contentId == null || contentTypeId == null)
			return false;
		new TourDownAndParsingTask(REQUEST_INTRODUCE_INFO).execute(contentTypeId,
				contentId);
		return true;
	}
	
	public boolean downloadImageInfo(String contentId, String contentTypeId) {
		Log.d(GPSInfoMain.DEBUG, "&2");
		if (contentId == null || contentTypeId == null)
			return false;
		new TourDownAndParsingTask(REQUEST_INTRODUCE_INFO).execute(contentTypeId,
				contentId);
		return true;
	}

	private class TourDownAndParsingTask extends
			AsyncTask<String, Void, COMNTourData> {
		int request;

		public TourDownAndParsingTask(int request) {
			Log.d(GPSInfoMain.DEBUG, "#3");
			this.request = request;
		}

		@Override
		protected COMNTourData doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d(GPSInfoMain.DEBUG, "&4");
			
				switch (request) {
				// ��ǥ�� ��ȯ ��û
				case REQUEST_COMMON_INFO:
					Log.d(GPSInfoMain.DEBUG, "#4");
					return COMNloadFeed(params);
				case REQUEST_INTRODUCE_INFO:
					//return INTROloadFeed(params);
				case REQUEST_IMAGE_INFO:
					break;
				}
				return null;

			}
		}
	
	

		public COMNTourData COMNloadFeed(String... params) {
			// TODO Auto-generated method stub
			try {
				Log.d(GPSInfoMain.DEBUG, "#5");
				FeedParser3 parser = COMNFeedParserFactory.getParser(params);
				return  parser.parse3();
			} catch (Throwable t) {
				Log.d(GPSInfoMain.DEBUG, t.getMessage(), t);
			}
			return null;
		}
		


		
	

}
