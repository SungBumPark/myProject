package com.example.travelmaker.gpsinfo.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ktmap.android.map.Coord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.*;

import com.example.travelmaker.main.*;
import com.example.travelmaker.tour.gpsinfomain.*;

/**
 * 네트워크 관련 처리 클래스
 */
public class NetworkMgr {

	private volatile static NetworkMgr networkMgr = null;
	private NetworkListener networkListener = null;
	private Activity mCtx = null;
	private final String authValue;

	public static final int REQUEST_TOUR_LIST = 0;// 리스트뷰에 출력될 데이터 리스트 요청
	public static final int REQUEST_CURRENT_CONVERT_COORD = 1; // 현재 좌표 변환 요청
	public static final int REQUEST_TARGET_CONVERT_COORD = 2; // 도착지 좌표 변환 요청
	public static final int REQUEST_ROUTE_SEARCH = 3; // 경로 데이터 요청

	private NetworkMgr(Activity ctx) {
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

	public static NetworkMgr getInstance(Activity ctx) {
		if (networkMgr == null) {
			synchronized (NetworkMgr.class) {
				if (networkMgr == null) {
					networkMgr = new NetworkMgr(ctx);
				}
			}
		}
		return networkMgr;
	}

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
		networkMgr = null;
	}

	/**
	 * 주변 관광 데이터를 서버로부터 다운로드 받는다.
	 * 
	 * @param xCoord
	 *            - 사용자 위치 x 좌표
	 * @param yCoord
	 *            - 사용자 위치 y 좌표
	 * @param radius
	 *            - 반경
	 * @param contentTypeId
	 *            - 컨텐츠 타입
	 * @param page
	 *            - 페이지
	 * @return - 성공 여부
	 */
	public boolean downloadTourData(String xCoord, String yCoord,
			String radius, String contentTypeId, String page) {
		if (xCoord == null || yCoord == null)
			return false;
		else if (xCoord.equals("") || yCoord.equals(""))
			return false;
		else if (xCoord.equals("0") || yCoord.equals("0"))
			return false;

		new TourDownAndParsingTask(REQUEST_TOUR_LIST).execute(xCoord, yCoord,
				radius, contentTypeId, page);
		return true;
	}

	/**
	 * WGS84좌표를 UTMK좌표로 변환한다.
	 * 
	 * @param xCoord
	 *            - WGS84 x 좌표
	 * @param yCoord
	 *            - WGS84 y 좌표
	 * @return 성공 여부
	 */
	public boolean convertCurrentCoord(String xCoord, String yCoord) {
		if (xCoord == null || yCoord == null) {

			return false;
		} else if (xCoord.equals("") || yCoord.equals(""))
			return false;
		else if (xCoord.equals("0") || yCoord.equals("0"))
			return false;

		new MapDataDownAndParsingTask(REQUEST_CURRENT_CONVERT_COORD).execute(
				xCoord, yCoord);

		return true;
	}

	/**
	 * WGS84좌표를 UTMK좌표로 변환한다.
	 * 
	 * @param xCoord
	 *            - WGS84 x 좌표
	 * @param yCoord
	 *            - WGS84 y 좌표
	 * @return 성공 여부
	 */
	public boolean convertTargetCoord(String xCoord, String yCoord) {
		if (xCoord == null || yCoord == null)
			return false;
		else if (xCoord.equals("") || yCoord.equals(""))
			return false;
		else if (xCoord.equals("0") || yCoord.equals("0"))
			return false;
		Log.d(GPSInfoMain.DEBUG, "^3 ");
		new MapDataDownAndParsingTask(REQUEST_TARGET_CONVERT_COORD).execute(
				xCoord, yCoord);

		return true;
	}

	/**
	 * 출발지와 도착지 좌표값을 이용하여 경로 데이터를 서버로부터 다운로드 받는다.
	 * 
	 * @param startX
	 *            - 출발지 x 좌표
	 * @param startY
	 *            - 출발지 y 좌표
	 * @param endX
	 *            - 도착지 x 좌표
	 * @param endY
	 *            - 도착지 y 좌표
	 * @return - 성공 여부
	 */
	public boolean downloadMapInfo(String startX, String startY, String endX,
			String endY) {
		if (startX == null || startY == null || endX == null || endY == null)
			return false;
		else if (startX.equals("") || startY.equals("") || endX.equals("")
				|| endY.equals(""))
			return false;
		else if (startX.equals("0") || startY.equals("0") || endX.equals("0")
				|| endY.equals("0"))
			return false;

		new MapDataDownAndParsingTask(REQUEST_ROUTE_SEARCH).execute(startX,
				startY, endX, endY);

		return true;
	}

	/**
	 * 좌펴계 변환 및 경로 데이터 다운로드 클래스
	 */
	private class MapDataDownAndParsingTask extends
			AsyncTask<String, Void, Map<String, Object>> {

		URL url = null;
		URLConnection conn = null;
		InputStream is = null;
		BufferedReader br = null;
		String data = null;
		final int request; // 네트워크 요청 구분 변수

		public MapDataDownAndParsingTask(int request) {
			this.request = request;
		}

		@Override
		protected Map<String, Object> doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				switch (request) {
				// 좌표계 변환 요청
				case REQUEST_CURRENT_CONVERT_COORD:
				case REQUEST_TARGET_CONVERT_COORD:
					Log.d(GPSInfoMain.DEBUG, "^4 ");
					url = new URL(mCtx.getString(R.string.convert_coord_url)
							+ "?params=" + getParams(params));
					break;

				// 경로 데이터 요청
				case REQUEST_ROUTE_SEARCH:
					url = new URL(mCtx.getString(R.string.route_search_url)
							+ "?params=" + getParams(params));
					break;
				}
				Log.d(GPSInfoMain.DEBUG, "^5 ");
				conn = url.openConnection();
				conn.addRequestProperty("authorization", authValue);

				is = conn.getInputStream();
				br = new BufferedReader(new InputStreamReader(is));
				Log.d(GPSInfoMain.DEBUG, "^6 ");
				while ((data = br.readLine()) != null) {
					// 파싱 처리
					return parse(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return null;
		}

		/**
		 * url string을 만들어 반환한다.
		 * 
		 * @param params
		 *            - url을 만들 파라미터
		 * @return - url string
		 * @throws UnsupportedEncodingException
		 * @throws JSONException
		 */
		private String getParams(String... params)
				throws UnsupportedEncodingException, JSONException {
			JSONObject jsonObj = new JSONObject();
			Calendar cal = Calendar.getInstance();
			String timestamp = String.format("%04d%02d%02d%02d%02d%02d",
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH),
					cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
					cal.get(Calendar.SECOND));

			switch (request) {
			case REQUEST_TARGET_CONVERT_COORD:
			case REQUEST_CURRENT_CONVERT_COORD:
				String x = params[0];
				String y = params[1];
				int inCoordType = 7;
				int outCoordType = 0;

				jsonObj.put("x", x);
				jsonObj.put("y", y);
				jsonObj.put("inCoordType", inCoordType);
				jsonObj.put("outCoordType", outCoordType);
				jsonObj.put("timestamp", timestamp);

				break;
			case REQUEST_ROUTE_SEARCH:
				String startX = params[0];
				String startY = params[1];
				String endX = params[2];
				String endY = params[3];
				String rptype = "0"; // 자동차 길찾기
				String coordType = "7"; // UTMK
				String priority = "0"; // 자동차 - 최단 거리 우선

				jsonObj.put("SX", startX);
				jsonObj.put("SY", startY);
				jsonObj.put("EX", endX);
				jsonObj.put("EY", endY);
				jsonObj.put("RPTYPE", rptype);
				jsonObj.put("COORDTYPE", coordType);
				jsonObj.put("PRIORITY", priority);
				jsonObj.put("timestamp", timestamp);
				break;
			}

			return java.net.URLEncoder.encode(jsonObj.toString(), "utf-8");
		}

		/**
		 * 서버로부터 받은 데이터를 파싱하여 Map객체를 반환한다.
		 * 
		 * @param data
		 *            - 서버로부터 받은 데이터
		 * @return - 파싱한 Map 객체
		 * @throws UnsupportedEncodingException
		 * @throws JSONException
		 */
		private Map<String, Object> parse(String data)
				throws UnsupportedEncodingException, JSONException {
			Map<String, Object> result = new HashMap<String, Object>();

			String[] datas = data.split(",");
			String[] datas2 = datas[4].split(":");
			String payload = URLDecoder.decode(datas2[1], "utf-8");
			Log.d(GPSInfoMain.DEBUG, "^7 ");
			// payload string is wrapped by "", remove "" for making JSONObject
			JSONObject response = new JSONObject(payload.substring(1,
					payload.length() - 1));
			switch (request) {
			case REQUEST_CURRENT_CONVERT_COORD:
			case REQUEST_TARGET_CONVERT_COORD:
				Log.d(GPSInfoMain.DEBUG, "^8 ");
				response = response.getJSONObject("RESDATA");
				response = response.getJSONObject("COORD");

				result.put("COORDTYPE", response.getString("COORDTYPE"));
				result.put("Y", response.getString("Y"));
				result.put("X", response.getString("X"));

				break;
			case REQUEST_ROUTE_SEARCH:
				ArrayList<Coord> vertexList = new ArrayList<Coord>();

				// 보간점 포함 경로 탐색 좌표 파싱(link parsing)
				JSONArray subArray = response.getJSONObject("RESDATA")
						.getJSONObject("SROUTE").getJSONObject("LINKS")
						.getJSONArray("link");

				if (subArray == null)
					return null;

				JSONArray vertexArray;

				for (int i = 0; i < subArray.length(); i++) {
					vertexArray = ((JSONObject) subArray.get(i))
							.getJSONArray("vertex");
					// vertexArray.length() - 1은 보간법에 의해 서버에서 내려오는 데이터중 중복 데이터
					// 저장을
					// 피하기 위한 방법이다
					for (int j = 0; j < vertexArray.length() - 1; j++) {
						JSONObject vertexObject = (JSONObject) vertexArray
								.get(j);

						vertexList.add(new Coord(Float.parseFloat(vertexObject
								.getString("x")), Float.parseFloat(vertexObject
								.getString("y"))));
						// 마지막 vertexObject를 list에 추가
						if (i == subArray.length() - 1
								&& j == vertexArray.length() - 1 - 1)
							vertexObject = (JSONObject) vertexArray.get(j + 1);
						vertexList.add(new Coord(Float.parseFloat(vertexObject
								.getString("x")), Float.parseFloat(vertexObject
								.getString("y"))));
					}
				}

				// 경로 탐색 좌표 파싱(ROUTE parsing)
				ArrayList<Coord> rgList = new ArrayList<Coord>();
				subArray = response.getJSONObject("RESDATA")
						.getJSONObject("SROUTE").getJSONObject("ROUTE")
						.getJSONArray("rg");

				if (subArray == null)
					return null;

				for (int i = 0; i < subArray.length(); i++) {
					JSONObject vertexObject = (JSONObject) subArray.get(i);
					rgList.add(new Coord(Float.parseFloat(vertexObject
							.getString("x")), Float.parseFloat(vertexObject
							.getString("y"))));
				}

				result.put("VERTEX_LIST", vertexList);
				result.put("RG_LIST", rgList);

				break;
			}

			return result;
		}

		@Override
		protected void onPostExecute(Map<String, Object> result) {
			// TODO Auto-generated method stub
			if (networkListener != null) {
				result.put("REQUEST", request);
				Log.d(GPSInfoMain.DEBUG, "^9 ");
				networkListener.contentDownloadComplete(result);
			} else
				Log.d(GPSInfoMain.DEBUG, "mCtx is not networkListener type");
		}
	}

	/**
	 * 관광지 데이터 다운로드 클래스
	 */
	private class TourDownAndParsingTask extends
			AsyncTask<String, Void, Map<String, Object>> {
		int request;

		public TourDownAndParsingTask(int request) {
			this.request = request;
		}

		@Override
		protected Map<String, Object> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return loadFeed(params);
		}

		private Map<String, Object> loadFeed(String... params) {
			try {
				FeedParser parser = FeedParserFactory.getParser(params);
				return parser.parse();
			} catch (Throwable t) {
				Log.d(GPSInfoMain.DEBUG, t.getMessage(), t);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Map<String, Object> result) {
			// TODO Auto-generated method stub
			if (networkListener != null) {
				result.put("REQUEST", request);
				networkListener.contentDownloadComplete(result);
				Log.d(GPSInfoMain.DEBUG, "A");
			} else
				Log.d(GPSInfoMain.DEBUG, "mCtx is not networkListener type");
		}

	}
}
