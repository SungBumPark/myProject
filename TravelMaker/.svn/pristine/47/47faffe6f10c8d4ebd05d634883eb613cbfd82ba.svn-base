/**
 * @author Kim Woo Hyeon
 * InsertActivity.java
 */

package com.example.travelmaker.timetable;

import java.util.ArrayList;

import com.example.travelmaker.calendar.DbHandler;
import com.example.travelmaker.main.GPSInfoMain;
import com.example.travelmaker.tour.gpsinfomain.R;
import com.example.travelmaker.tour.gpsinfomain.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {
	
	private static String mInsertedContent;
	private SeekBar mRedSeekBar, mGreenSeekBar, mBlueSeekBar, mAlphaSeekBar;
	private static int mRedValue, mGreenValue, mBlueValue, mAlphaValue;
	
	DbHandler dbHandler;
	Cursor cursor = null;
	int mSelectedItem = -1;
	String[] mListItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert);
		
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
		mInsertedContent = "";
		
		((RelativeLayout) findViewById(R.id.insertBackground)).setBackgroundResource(R.drawable.register_bg);
		((TextView) findViewById(R.id.insertTitleText)).setText(getIntent().getExtras().getString( define.INTENT_KEY_INSERTTITLE ));
		((TextView) findViewById(R.id.insertContentText)).setText("찜한 여행지로 일정 등록(Click)");
		((TextView) findViewById(R.id.insertRedColorText)).setText("RED");
		((TextView) findViewById(R.id.insertGreenColorText)).setText("GREEN");
		((TextView) findViewById(R.id.insertBlueColorText)).setText("BLUE");
		((TextView) findViewById(R.id.insertAlphaColorText)).setText("투명도");
		
		mRedSeekBar = (SeekBar) findViewById(R.id.insertRedSeekBar);
		mGreenSeekBar = (SeekBar) findViewById(R.id.insertGreenSeekBar);
		mBlueSeekBar = (SeekBar) findViewById(R.id.insertBlueSeekBar);
		mAlphaSeekBar = (SeekBar) findViewById(R.id.insertAlphaSeekBar);
		
		mRedSeekBar.setMax(0xFF);
		mGreenSeekBar.setMax(0xFF);
		mBlueSeekBar.setMax(0xFF);
		mAlphaSeekBar.setMax(0xFF);
		
		mRedSeekBar.setProgress(0);
		mGreenSeekBar.setProgress(0);
		mBlueSeekBar.setProgress(0);
		mAlphaSeekBar.setProgress(0);
		
		mRedValue = mGreenValue = mBlueValue = mAlphaValue = 0;
	}

	private void SetListener() {
		// TODO Auto-generated method stub
		((Button) findViewById(R.id.insertOKBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.insertCancelBtn)).setOnClickListener(this);
		((TextView) findViewById(R.id.insertContentText)).setOnClickListener(this);
		mRedSeekBar.setOnSeekBarChangeListener(this);
		mGreenSeekBar.setOnSeekBarChangeListener(this);
		mBlueSeekBar.setOnSeekBarChangeListener(this);
		mAlphaSeekBar.setOnSeekBarChangeListener(this);
	}
	
	public static String GetInsertedContent() {
		// TODO Auto-generated method stub
		return mInsertedContent;
	}
	
	public static int[] GetInsertedARGB() {
		// TODO Auto-generated method stub
		int retVal[] = new int[4];
		retVal[0] = mRedValue;
		retVal[1] = mGreenValue;
		retVal[2] = mBlueValue;
		retVal[3] = mAlphaValue;
		return retVal;
	}
	
	public static void ShowPopup( String title, String msg, Context context ) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(context)
		.setIcon(R.drawable.ic_launcher)
		.setTitle(title)
		.setMessage(msg)
		.setPositiveButton(string.common_use_ok_btn, null)
		.show();
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int position, boolean arg2) {
		// TODO Auto-generated method stub
		switch( seekBar.getId() )
		{
		case R.id.insertRedSeekBar:
			mRedValue = position;
			break;
		case R.id.insertGreenSeekBar:
			mGreenValue = position;
			break;
		case R.id.insertBlueSeekBar:
			mBlueValue = position;
			break;
		case R.id.insertAlphaSeekBar:
			mAlphaValue = position;
			break;
		default:
			break;
		}
		((TextView) findViewById(R.id.insertColorTestText)).setBackgroundColor(Color.argb(mAlphaValue, mRedValue, mGreenValue, mBlueValue));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch( v.getId() )
		{
		case R.id.insertContentText:
			
			ArrayList<String> items = new ArrayList<String>();
			dbHandler = DbHandler.open(this);
			cursor = dbHandler.selectAll("scrap");
			while( cursor.moveToNext() )
				items.add( cursor.getString(0) );
			
			if( items.size() == 0 )
			{
				Toast.makeText(this, "스크랩 총 0건 입니다", Toast.LENGTH_SHORT).show();
				break;
			}
			mListItems = new String[items.size()];
			for( int idx = 0; idx < mListItems.length; ++idx )
				mListItems[idx] = items.get(idx);
			
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("여행지 스크랩목록")
			.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if( mSelectedItem == -1 )
						InsertActivity.ShowPopup("일정 등록", "여행지를 선택해주세요.", InsertActivity.this);
					
					else
					{
						mInsertedContent = mListItems[mSelectedItem];
						//InsertActivity.ShowPopup("일정등록", mInsertedContent, InsertActivity.this);
						((EditText) findViewById(R.id.insertContentEdit)).setText(mInsertedContent);
					}
				}
			})
			.setPositiveButton(string.common_use_cancel_btn, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			})
			.setSingleChoiceItems( mListItems, -1, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mSelectedItem = which;
				}
			})
			.show();
			break;
			
		case R.id.insertOKBtn:
			mInsertedContent = ((EditText) findViewById(R.id.insertContentEdit)).getText().toString();
			if( mInsertedContent.equals("") )
				mInsertedContent = " ";
			onClose( define.INTENT_RESULT_SUCCESS );
			break;
		case R.id.insertCancelBtn:
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
