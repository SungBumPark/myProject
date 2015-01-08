/**
 * @author Kim Woo Hyeon
 * MainActivity.java
 */

package com.example.travelmaker.timetable;

import java.util.ArrayList;

import com.example.travelmaker.tour.gpsinfomain.R;
import com.example.travelmaker.tour.gpsinfomain.R.string;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TableMainActivity extends Activity implements OnClickListener {

	private static DataBase mHelper;
	private static SQLiteDatabase mDb;
	private int mSelectedItem = -1;
	private String mListItems[] = null;
	private static Drawable mBackgroundImg;

	/*intent한 값 저장*/
	boolean pFlag = true;
	int pTravelID, pDayID, pDay;
	String pTitle, pTitleDayID;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize;
		Init();
		// Enroll listener;
		SetListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void onClose() {
		// TODO Auto-generated method stub
		finish();
		android.os.Process.killProcess( android.os.Process.myPid() );
	}

	@SuppressWarnings("deprecation")
	private void Init() {
		// TODO Auto-generated method stub
		mBackgroundImg = getResources().getDrawable(R.drawable.table_m_bg);
		((RelativeLayout) findViewById(R.id.mainBackground)).setBackgroundDrawable( mBackgroundImg );
		mHelper = new DataBase(this);
		try {
			mDb = mHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			mDb = mHelper.getReadableDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( mHelper.selectAll( mDb ) == null )
		{
			mHelper.onUpgrade( mDb, 0, 0 );
		}

		Intent planTotime = getIntent();

		pFlag = planTotime.getExtras().getBoolean("flag");
		/*전체일정보기*/
		if(pFlag){

			pTravelID = planTotime.getExtras().getInt("_travel");	//id
			pTitle = planTotime.getExtras().getString("title");		//여행제목
			pDay = planTotime.getExtras().getInt("day");			//여행일수
			//Toast.makeText(this, pTitle, Toast.LENGTH_SHORT).show();
			loadTable();
		}
		/*하루일정보기*/
		else{

			pTravelID = planTotime.getExtras().getInt("_travel");	//id
			pDayID = planTotime.getExtras().getInt("dayID");		//여행일수 ID
			pTitle = planTotime.getExtras().getString("title");		//여행제목
			pTitleDayID = pTitle+pDayID+"일차";

			loadTable();
		}
	}

	private void SetListener() {
		// TODO Auto-generated method stub
		((Button) findViewById(R.id.makeBtn)).setOnClickListener(this);
		//((Button) findViewById(R.id.loadBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.deleteBtn)).setOnClickListener(this);
		//((Button) findViewById(R.id.bgChangeBtn)).setOnClickListener(this);
		//((Button) findViewById(R.id.helpBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.exitBtn)).setOnClickListener(this);
	}

	public static DataBase GetDatabase() {
		// TODO Auto-generated method stub
		return mHelper;
	}

	public static SQLiteDatabase GetSQLiteDatabase() {
		// TODO Auto-generated method stub
		return mDb;
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

	public static Drawable GetBackgroundImage() {
		// TODO Auto-generated method stub
		return mBackgroundImg;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch( requestCode )
		{
		case define.INTENT_REQUEST_EXCHANGEBG:
			if( resultCode == define.INTENT_RESULT_SUCCESS )
			{
				String Path = ExplorerActivity.GetSelectedFilePath();

				BitmapFactory.Options bfo = new BitmapFactory.Options();
				bfo.inSampleSize = 2;

				Bitmap bm = BitmapFactory.decodeFile(Path, bfo);

				mBackgroundImg =new BitmapDrawable(bm);
				((RelativeLayout) findViewById(R.id.mainBackground)).setBackgroundDrawable( mBackgroundImg );
			}
			break;
		default:
			break;
		}
	}

	public void loadTable(){

		Cursor cursor = mHelper.selectName(mDb);
		mListItems = new String[cursor.getCount()];
		int index = 0;
		while( cursor.moveToNext() )
			mListItems[index++] = cursor.getString(0);

		for(int i=0; i < index; i++){

			if(pFlag && mListItems[i].equals(pTitle)){
				mSelectedItem = i;
				break;}
			else if(!pFlag && mListItems[i].equals(pTitleDayID)){
				mSelectedItem = i;
				break;}

			mSelectedItem = -1;

		}

		switch(mSelectedItem){
		case -1:
			//TableMainActivity.ShowPopup("일정 불러오기", "일정이 존재하지않습니다.", TableMainActivity.this);
			Intent intent_load = new Intent( this, MakeActivity.class );

			if(pFlag){
				pFlag = true;
				intent_load.putExtra("pFlag", pFlag);
				intent_load.putExtra("pTravelID", pTravelID);
				intent_load.putExtra("pTitle", pTitle);
				intent_load.putExtra("pDay", pDay);

			}

			else{
				pFlag = false;
				intent_load.putExtra("pFlag", pFlag);
				intent_load.putExtra("pTravelID", pTravelID);
				intent_load.putExtra("pDayID", pDayID);
				intent_load.putExtra("pTitle", pTitle);
				intent_load.putExtra("pDay", pDay);
			}
			//pFlag = true;
			startActivity(intent_load);
			break;

		default:
			//TableMainActivity.ShowPopup("일정 불러오기", "일정을 불러옵니다.", TableMainActivity.this);
			ArrayList<String> itemsDB = new ArrayList<String>();
			cursor = mHelper.selectInfoForName(mDb, mListItems[mSelectedItem]);
			while( cursor.moveToNext() )
			{
				itemsDB.add( cursor.getString(0) );		// index
				itemsDB.add( cursor.getString(1) );		// save_name
				itemsDB.add( cursor.getString(2) );		// time_count
				itemsDB.add( cursor.getString(3) );		// time
				itemsDB.add( cursor.getString(4) );		// content_count
				itemsDB.add( cursor.getString(5) );		// content_locate
				itemsDB.add( cursor.getString(6) );		// content_red
				itemsDB.add( cursor.getString(7) );		// content_green
				itemsDB.add( cursor.getString(8) );		// content_blue
				itemsDB.add( cursor.getString(9) );		// content_alpha
				itemsDB.add( cursor.getString(10) );	// content
			}

			Intent intent = new Intent( TableMainActivity.this, com.example.travelmaker.timetable.TableActivity.class );
			intent.putExtra( define.INTENT_KEY_LOAD_START, true );
			intent.putExtra( define.INTENT_KEY_TIMECOUNT, Integer.parseInt(itemsDB.get(2)) );
			intent.putExtra( define.INTENT_KEY_LOAD_TIME, itemsDB.get(3) );
			intent.putExtra( define.INTENT_KEY_CONTENT_COUNT, itemsDB.get(4) );
			intent.putExtra( define.INTENT_KEY_CONTENT_LOCATE, itemsDB.get(5) );
			intent.putExtra( define.INTENT_KEY_CONTENT_RED, itemsDB.get(6) );
			intent.putExtra( define.INTENT_KEY_CONTENT_GREEN, itemsDB.get(7) );
			intent.putExtra( define.INTENT_KEY_CONTENT_BLUE, itemsDB.get(8) );
			intent.putExtra( define.INTENT_KEY_CONTENT_ALPHA, itemsDB.get(9) );
			intent.putExtra( define.INTENT_KEY_CONTENT, itemsDB.get(10) );

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
			startActivity(intent);
			break;


		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch( v.getId() )
		{
		case R.id.makeBtn:
		{
			define.debug("makeTableBtn Click!");
			loadTable();
		}
		break;

		case R.id.deleteBtn:
		{
		mDb = mHelper.getReadableDatabase();
		define.debug("deleteTableBtn Click!");
		mSelectedItem = -1;
		ArrayList<String> items = new ArrayList<String>();

		Cursor cursor = mHelper.selectName(mDb);
		while( cursor.moveToNext() )
			items.add( cursor.getString(0) );
		if( items.size() == 0 )
		{
			TableMainActivity.ShowPopup("삭제하기", "저장된 값이 없습니다.", TableMainActivity.this);
			break;
		}
		mListItems = new String[items.size()];
		for( int idx = 0; idx < mListItems.length; ++idx )
				mListItems[idx] = items.get(idx);
		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("삭제하기")
		.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if( mSelectedItem == -1 )
					TableMainActivity.ShowPopup("삭제하기", "선택하지 않았습니다.", TableMainActivity.this);
				else
				{
					mHelper.deleteName(mDb, mListItems[mSelectedItem]);
					TableMainActivity.ShowPopup("삭제하기", "삭제하였습니다.", TableMainActivity.this);
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
		}
		break;
		/*case R.id.bgChangeBtn:
		{
			Intent intent = new Intent(this, ExplorerActivity.class);
			startActivityForResult(intent, define.INTENT_REQUEST_EXCHANGEBG);
		}
		break;
		case R.id.helpBtn:
		{
			define.debug("helpBtn Click!");
			Intent intent = new Intent( this, HelpActivity.class );
			startActivity(intent);
		}
		break;*/
		case R.id.exitBtn:
		{
			define.debug("exitBtn Click!");
			onClose();
		}
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
			onClose();
		}
		return super.onKeyDown(keyCode, event);
	}


}
