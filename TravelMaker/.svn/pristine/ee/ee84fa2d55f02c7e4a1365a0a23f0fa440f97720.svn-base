package com.example.travelmaker.plan;

import com.example.travelmaker.tour.gpsinfomain.R;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.MediaStore.*;
import android.util.*;
import android.widget.*;




public class AlarmReceiver extends BroadcastReceiver {




	private int YOURAPP_NOTIFICATION_ID;  

    

    @Override  

    public void onReceive(Context context, Intent intent) {  

        Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT).show();  

          

        showNotification(context, R.drawable.app_icon,   

                 "알람!!", "지금 이러고 있을 시간 없다."); 

    }  

      

    private void showNotification(Context context, int statusBarIconID,   

                      String statusBarTextID, String detailedTextID) {  

        Intent contentIntent = new Intent(context, TestPageWrite.class);  

        PendingIntent theappIntent =   

                PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        

        

        CharSequence from = "알람";  

        CharSequence message = "여행을 떠나는 날이 다가오고 있어요!";  

  

        Notification notif = new Notification(statusBarIconID, null, System.currentTimeMillis());

        notif.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");//ringURI;

        notif.flags = Notification.FLAG_AUTO_CANCEL;

        notif.setLatestEventInfo(context, from, message, theappIntent);  

        notif.ledARGB = Color.GREEN;

        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        

        nm.notify(1234, notif);  

        

    }  

} 
