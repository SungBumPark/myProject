package com.example.travelmaker.timetable;

import java.util.ArrayList;

import com.example.travelmaker.calendar.DbHandler;
import com.example.travelmaker.tour.gpsinfomain.R;
import com.example.travelmaker.tour.gpsinfomain.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class UpdateActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {

	private static String mUpdatedContent;
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
		setContentView(R.layout.activity_update);

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
		mUpdatedContent = "";
		((RelativeLayout) findViewById(R.id.updateBackground)).setBackgroundDrawable( TableMainActivity.GetBackgroundImage() );

		((TextView) findViewById(R.id.updateTitleText)).setText(getIntent().getExtras().getString( define.INTENT_KEY_UPDATETITLE ));
		((TextView) findViewById(R.id.updateContentText)).setText("일정을 등록하세요");
		mUpdatedContent = getIntent().getExtras().getString( define.INTENT_KEY_UPDATECONTENT );
		if( mUpdatedContent.equals(" ") )
			mUpdatedContent = "";
		((EditText) findViewById(R.id.updateContentEdit)).setText(mUpdatedContent);
		((TextView) findViewById(R.id.updateRedColorText)).setText("RED");
		((TextView) findViewById(R.id.updateGreenColorText)).setText("GREEN");
		((TextView) findViewById(R.id.updateBlueColorText)).setText("BLUE");
		((TextView) findViewById(R.id.updateAlphaColorText)).setText("ALPHA");

		mRedSeekBar = (SeekBar) findViewById(R.id.updateRedSeekBar);
		mGreenSeekBar = (SeekBar) findViewById(R.id.updateGreenSeekBar);
		mBlueSeekBar = (SeekBar) findViewById(R.id.updateBlueSeekBar);
		mAlphaSeekBar = (SeekBar) findViewById(R.id.updateAlphaSeekBar);

		mRedSeekBar.setMax(0xFF);
		mGreenSeekBar.setMax(0xFF);
		mBlueSeekBar.setMax(0xFF);
		mAlphaSeekBar.setMax(0xFF);

		mRedValue = getIntent().getExtras().getInt( define.INTENT_KEY_UPDATE_RED );
		mGreenValue = getIntent().getExtras().getInt( define.INTENT_KEY_UPDATE_GREEN );
		mBlueValue = getIntent().getExtras().getInt( define.INTENT_KEY_UPDATE_BLUE );
		mAlphaValue = getIntent().getExtras().getInt( define.INTENT_KEY_UPDATE_ALPHA );

		mRedSeekBar.setProgress(mRedValue);
		mGreenSeekBar.setProgress(mGreenValue);
		mBlueSeekBar.setProgress(mBlueValue);
		mAlphaSeekBar.setProgress(mAlphaValue);

		((TextView) findViewById(R.id.updateColorTestText)).setBackgroundColor(Color.argb(mAlphaValue, mRedValue, mGreenValue, mBlueValue));
	}

	private void SetListener() {
		// TODO Auto-generated method stub
		((Button) findViewById(R.id.updateOKBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.updateCancelBtn)).setOnClickListener(this);
		((TextView) findViewById(R.id.updateContentText)).setOnClickListener(this);
		mRedSeekBar.setOnSeekBarChangeListener(this);
		mGreenSeekBar.setOnSeekBarChangeListener(this);
		mBlueSeekBar.setOnSeekBarChangeListener(this);
		mAlphaSeekBar.setOnSeekBarChangeListener(this);
	}

	public static String GetUpdatedContent() {
		// TODO Auto-generated method stub
		return mUpdatedContent;
	}

	public static int[] GetUpdatedARGB() {
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
		case R.id.updateRedSeekBar:
			mRedValue = position;
			break;
		case R.id.updateGreenSeekBar:
			mGreenValue = position;
			break;
		case R.id.updateBlueSeekBar:
			mBlueValue = position;
			break;
		case R.id.updateAlphaSeekBar:
			mAlphaValue = position;
			break;
		default:
			break;
		}
		((TextView) findViewById(R.id.updateColorTestText)).setBackgroundColor(Color.argb(mAlphaValue, mRedValue, mGreenValue, mBlueValue));
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
		case R.id.updateContentText:

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
			.setTitle("일정 수정")
			.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if( mSelectedItem == -1 )
						UpdateActivity.ShowPopup("일정 수정", "선택하지 않았습니다.", UpdateActivity.this);

					else
					{
						mUpdatedContent = mListItems[mSelectedItem];
						UpdateActivity.ShowPopup("일정 수정", mUpdatedContent, UpdateActivity.this);
						((EditText) findViewById(R.id.updateContentEdit)).setText(mUpdatedContent);
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

		case R.id.updateOKBtn:
			mUpdatedContent = ((EditText) findViewById(R.id.updateContentEdit)).getText().toString();
			if( mUpdatedContent.equals("") )
				mUpdatedContent = " ";
			onClose( define.INTENT_RESULT_SUCCESS );
			break;
		case R.id.updateCancelBtn:
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
