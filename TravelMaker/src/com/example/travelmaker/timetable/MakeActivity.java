/**
 * @author Kim Woo Hyeon
 * MakeActivity.java
 */

package com.example.travelmaker.timetable;

import com.example.travelmaker.tour.gpsinfomain.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MakeActivity extends Activity{
								//implements OnClickListene
	private NumberPicker mTimeCountNumberPicker, mTimeSpaceNumberPicker, mBreakTimeNumberPicker;
	private TimePicker mStartTimePicker;
	
	/*intent�� �� ����*/
	boolean pFlag = true;;
	int pTravelID, pDayID, pDay;
	String pTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make);
		
		// Initialize;
		Init();
		// Enroll listener;
		SetIntent();
		finish();
	}

	@SuppressWarnings("deprecation")
	private void Init() {
		// TODO Auto-generated method stub
		//((ScrollView) findViewById(R.id.makeBackground)).setBackgroundDrawable( MainActivity.GetBackgroundImage() );
		Intent planTotime = getIntent();
		pFlag = planTotime.getExtras().getBoolean("pFlag");
		
		/*��ü��������*/
		if(pFlag){
			
			pTravelID = planTotime.getExtras().getInt("pTravelID");	//id
			pTitle = planTotime.getExtras().getString("pTitle");	//��������
			pDay = planTotime.getExtras().getInt("pDay");			//�����ϼ�
		}
		
		/*�Ϸ���������*/
		else{
			
			pTravelID = planTotime.getExtras().getInt("pTravelID");	//id
			pDayID = planTotime.getExtras().getInt("pDayID");		//�����ϼ� ID
			pTitle = planTotime.getExtras().getString("pTitle");	//��������
		}
		
		mTimeCountNumberPicker = (NumberPicker) findViewById(R.id.timeCountNumberPicker);
		mTimeCountNumberPicker.setMinValue(1);
		mTimeCountNumberPicker.setMaxValue(20);
		mTimeCountNumberPicker.setValue(19);
		mStartTimePicker = (TimePicker) findViewById(R.id.startTimePicker);
		mStartTimePicker.setIs24HourView( true );
		mStartTimePicker.setCurrentHour(6);
		mStartTimePicker.setCurrentMinute(0);
		mTimeSpaceNumberPicker = (NumberPicker) findViewById(R.id.timeSpaceNumberPicker);
		mTimeSpaceNumberPicker.setMinValue(1);
		mTimeSpaceNumberPicker.setMaxValue(80);
		mTimeSpaceNumberPicker.setValue(60);
		mBreakTimeNumberPicker = (NumberPicker) findViewById(R.id.breakTimeNumberPicker);
		mBreakTimeNumberPicker.setMinValue(0);
		mBreakTimeNumberPicker.setMaxValue(40);
		mBreakTimeNumberPicker.setValue(0);
	}
	
	private void SetIntent(){
	
		Intent intent = new Intent( MakeActivity.this, TableActivity.class );
		
		if(pFlag){
			pFlag = true;
			intent.putExtra("pFlag", pFlag);
			intent.putExtra("pTravelID", pTravelID);
			intent.putExtra("pTitle", pTitle);
			intent.putExtra("pDay", pDay);
			
		}
		
		else{
			pFlag = false;
			intent.putExtra("pFlag", pFlag);
			intent.putExtra("pTravelID", pTravelID);
			intent.putExtra("pDayID", pDayID);
			intent.putExtra("pTitle", pTitle);
			intent.putExtra("pDay", pDay);
		}
		pFlag = true;
		intent.putExtra( define.INTENT_KEY_TIMECOUNT, mTimeCountNumberPicker.getValue() );
		intent.putExtra( define.INTENT_KEY_STARTTIMEHOUR, mStartTimePicker.getCurrentHour() );
		intent.putExtra( define.INTENT_KEY_STARTTIMEMIN, mStartTimePicker.getCurrentMinute() );
		intent.putExtra( define.INTENT_KEY_TIMESPACE, mTimeSpaceNumberPicker.getValue() );
		intent.putExtra( define.INTENT_KEY_BREAKTIME, mBreakTimeNumberPicker.getValue() );
		startActivity(intent);
	}
	/*private void SetListener() {
		// TODO Auto-generated method stub
		((Button) findViewById(R.id.makeTableBtn)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v.getId() == R.id.makeTableBtn )
		{
			String msg =	"���� ���� : " + mTimeCountNumberPicker.getValue() + "����\n" +
							"���� �ð� : " + mStartTimePicker.getCurrentHour() + "�� " + mStartTimePicker.getCurrentMinute() + "��\n" +
							"�ð� ���� : " + mTimeSpaceNumberPicker.getValue() + "��\n" +
							"���� �ð� : " + mBreakTimeNumberPicker.getValue() + "��\n" +
							"\n���� ������ �ð�ǥ�� ������?";
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("�����")
		    .setMessage(msg)
			.setNegativeButton(com.example.travelmaker.tour.gpsinfomain.R.string.common_use_ok_btn, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
					Intent intent = new Intent( MakeActivity.this, TableActivity.class );
					intent.putExtra("travelID", travelID);
					intent.putExtra("travelDAY", travelDAY);
					intent.putExtra( define.INTENT_KEY_TIMECOUNT, mTimeCountNumberPicker.getValue() );
					intent.putExtra( define.INTENT_KEY_STARTTIMEHOUR, mStartTimePicker.getCurrentHour() );
					intent.putExtra( define.INTENT_KEY_STARTTIMEMIN, mStartTimePicker.getCurrentMinute() );
					intent.putExtra( define.INTENT_KEY_TIMESPACE, mTimeSpaceNumberPicker.getValue() );
					intent.putExtra( define.INTENT_KEY_BREAKTIME, mBreakTimeNumberPicker.getValue() );
					startActivity(intent);
				}
			})
			.setPositiveButton(com.example.travelmaker.tour.gpsinfomain.R.string.common_use_cancel_btn, null)
			.show();
		}
	}*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK )
			finish();
		return super.onKeyDown(keyCode, event);
	}
	
}
