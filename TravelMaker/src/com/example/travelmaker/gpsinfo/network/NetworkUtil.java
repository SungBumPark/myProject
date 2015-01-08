package com.example.travelmaker.gpsinfo.network;

import android.content.*;
import android.net.*;
import android.net.wifi.*;

public class NetworkUtil {

	public static boolean isOnline(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		  NetworkInfo netInfo = cm.getActiveNetworkInfo();
		   if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		    return true;
		   }
		   return false;
	}

	


}
