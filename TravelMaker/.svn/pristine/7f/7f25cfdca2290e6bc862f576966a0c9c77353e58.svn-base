package com.example.travelmaker.info.gps;

import java.util.*;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.util.Log;
import android.location.LocationProvider;
import android.location.LocationManager;

import com.example.travelmaker.main.*;

/**
 * 사용자 위치 정보를 수신하는 클래스
 */
//kjhughg
public class GPSTracker implements LocationListener{

    private final Context mContext ;
    boolean isGPSEnabled = false;
    Location location = null;
    double latitude;
    double longitude;
    LocationListener locationListener;


    // private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10; // 10m 이상
    // 차이날때 위치
    // 정보 업데이트
    // private static final long MIN_TIME_BW_UPDATES = 1000 * 5; // 10초 주기로 위치
    // 정보
    // 업데이트
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
		mContext = context;
		getLocation();
    }

    /**
     * GPS,네트워크를 통하여 사용자 위치 정보 수신하다록 요청 및 최근 사용자 위치 정보를 반환한다.
     * 
     * @return 가장 최근의 사용자 위치 정보
     */
    public Location getLocation() {
	try {
	    locationManager = (LocationManager) mContext
		    .getSystemService(Context.LOCATION_SERVICE);

	    // GPS 이용 가능 상태
	    isGPSEnabled = locationManager
		    .isProviderEnabled(LocationManager.GPS_PROVIDER);
	    Log.i(GPSInfoMain.DEBUG, "isGPSEnabled : " + isGPSEnabled);
	    if (!isGPSEnabled) {
		((GPSInfoMain) mContext)
			.onPopupShow(PopupFragment.REQUEST_GPS_UNABLE);
	    } else {
		// GPS 이용 가능한지 파악
	    	
	    	String bestProvider;
	    	// List all providers:
    		List<String> providers = locationManager.getAllProviders();
	    	for (String provider : providers) {
	    	Log.i(GPSInfoMain.DEBUG, "provider : " + provider);
	    	}

    		Criteria criteria = new Criteria();
    		criteria.setAccuracy(Criteria.ACCURACY_FINE);
    		bestProvider = locationManager.getBestProvider(criteria, true);
	    	Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    	//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	    	Log.i(GPSInfoMain.DEBUG, "bestProvider " + bestProvider);
    
    		
	    	if (location != null) {
	    		//location = locationManager.getLastKnownLocation(bestProvider);
	    		//onLocationChanged(location);
	    		latitude = location.getLatitude();
	    		longitude = location.getLongitude();
	    	}

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
		return location;
    }

  
    
	/**
     * 사용자 위치 정보 수신을 중지한다.
     */
    public void stopUsingGPS() {
	if (locationManager != null) {
	    locationManager.removeUpdates(GPSTracker.this);
	}
    }

    public double getLatitude() {
	if (location != null) {
	    latitude = location.getLatitude();
	}
	return latitude;
    }

    public double getLongitude() {
	if (location != null) {
	    longitude = location.getLongitude();
	}
	return longitude;
    }

    public boolean isGpsEnable() {
	return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
		this.location = location;
		latitude = location.getLatitude();
		longitude = location.getLongitude();

		Log.i(GPSInfoMain.DEBUG, "onLocationChanged latitude : " + latitude
			+ " longitude : " + longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
    }
}
