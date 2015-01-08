package com.example.travelmaker.plan;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.travelmaker.tour.gpsinfomain.R;
import com.example.travelmaker.tour.gpsinfomain.R.string;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

public class RegisterPlanActivity extends Activity  {


	
	int titleID;
	int day;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cal_layout_regist);		
		
		/*PlanListActivity.class로 부터 데이터(planlist TABEL의 _travle과 day) 받음*/
		Intent intent = getIntent();
		titleID = intent.getExtras().getInt("_travel");
		day = intent.getExtras().getInt("day");
	
		
	}
}
