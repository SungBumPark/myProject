/**
 * @author Kim Woo Hyeon
 * UpdateTimeActivity.java
 */

package com.example.travelmaker.timetable;

import com.example.travelmaker.tour.gpsinfomain.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

public class UpdateTimeActivity extends Activity implements OnClickListener{
	
	private TimePicker mStartTimePicker, mEndTimePicker;
	private static String mUpdatedTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_time);
		
		// Initialize;
		Init();
		// Enroll listener;
		SetListener();
	}
	
	private void onClose( int mode ) {
		// TODO Auto-generated method stub
		if( mode == define.INTENT_RESULT_SUCCESS )	setResult(define.INTENT_RESULT_SUCCESS);
		else										setResult(define.INTENT_RESULT_FAIL);
		finish();
	}
	
	@SuppressWarnings("deprecation")
	private void Init() {
		// TODO Auto-generated method stub
		mUpdatedTime = "";
		((RelativeLayout) findViewById(R.id.updateTimeBackground)).setBackgroundDrawable( TableMainActivity.GetBackgroundImage() );
		
		mStartTimePicker = (TimePicker) findViewById(R.id.startUpdateTimePicker);
		mStartTimePicker.setIs24HourView( true );
		mStartTimePicker.setCurrentHour( getIntent().getExtras().getInt( define.INTENT_KEY_STARTTIMEHOUR ) );
		mStartTimePicker.setCurrentMinute( getIntent().getExtras().getInt( define.INTENT_KEY_STARTTIMEMIN ) );
		mEndTimePicker = (TimePicker) findViewById(R.id.endUpdateTimePicker);
		mEndTimePicker.setIs24HourView( true );
		mEndTimePicker.setCurrentHour( getIntent().getExtras().getInt( define.INTENT_KEY_ENDTIMEHOUR ) );
		mEndTimePicker.setCurrentMinute( getIntent().getExtras().getInt( define.INTENT_KEY_ENDTIMEMIN ) );
	}
	
	private void SetListener() {
		// TODO Auto-generated method stub
		((Button) findViewById(R.id.updateTimeOKBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.updateTimeCancelBtn)).setOnClickListener(this);
	}
	
	public static String GetUpdatedTime() {
		// TODO Auto-generated method stub
		return mUpdatedTime;
	}
	
	private String GetTimeString( int nValue ) {
		// TODO Auto-generated method stub
		String str = "";
		if( nValue < 10 )
			str = "0" + nValue;
		else
			str = "" + nValue;
		return str;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch( v.getId() )
		{
		case R.id.updateTimeOKBtn:
			mUpdatedTime = 	GetTimeString(mStartTimePicker.getCurrentHour()) + ":" + GetTimeString(mStartTimePicker.getCurrentMinute()) + "~" +
							GetTimeString(mEndTimePicker.getCurrentHour()) + ":" + GetTimeString(mEndTimePicker.getCurrentMinute());
			onClose( define.INTENT_RESULT_SUCCESS );
			break;
		case R.id.updateTimeCancelBtn:
			onClose( define.INTENT_RESULT_FAIL );
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			onClose( define.INTENT_RESULT_FAIL );
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
