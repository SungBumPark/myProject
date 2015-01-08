/**
 * @author Kim Woo Hyeon
 * TableActivity.java
 */

package com.example.travelmaker.timetable;


import com.example.travelmaker.calendar.DbHandler;
import com.example.travelmaker.info.data.ScrapTourData;
import com.example.travelmaker.main.GPSInfoMain;
import com.example.travelmaker.scrap.ScrapInfoView;
import com.example.travelmaker.tour.gpsinfomain.R;
import com.example.travelmaker.tour.gpsinfomain.R.string;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class TableActivity extends Activity implements OnItemClickListener {

	Intent intent;
	DbHandler dbHandler;
	Cursor cursor = null;
	String[] scrapfield = new String[] {"title","contentId","homepage","imageUrl", 
			"contentTypeId", "addr1", "addr2", 
			"overview", "tel", "zipcode", "EX", "EY"};
	String title, homepage, imageUrl, contentTypeId,
	addr1, addr2, overview, tel, zipcode, EX, EY;
	int contentId;
	/*intent한 값 저장*/
	boolean pFlag;
	int pTravelID, pDayID, pDay;
	String pTitle;

	String save_name;

	private int COLUMN_COUNT;
	private int mTimeCount;
	private int mStartTimeHour;
	private int mStartTimeMin;
	private int mTimeSpace;
	private int mBreakTime;
	private String mUpdateTimeSendString, mInsertTitleSendString, mUpdateContentSendString;
	private int mUpdateTimePosition, mInsertedItemPosition, mUpdatedItemPosition;
	private GridView mTimeTable;
	private ListAdapter mAdapter;
	private String mItemTextArray[];
	private int mRedColorArray[], mGreenColorArray[], mBlueColorArray[], mAlphaColorArray[];

	class ScheduleAdapter extends BaseAdapter {

		Context mContext;
		int mCount = (mTimeCount + 1) * COLUMN_COUNT;
		String mStrText[];
		int mDisplayWidth, mDisplayHeight;
		TextPaint mPaint;

		ScheduleAdapter(Context context, String str[]){
			mStrText = str;
			mContext = context;

			DisplayMetrics displayMetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			mDisplayWidth = displayMetrics.widthPixels;
			mDisplayHeight = displayMetrics.heightPixels;

			mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
			mPaint.density = displayMetrics.density;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCount;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View oldView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView items = new TextView(mContext);
			items.setHeight(mDisplayHeight/10);	// 각 아이템 높이
			items.setGravity(Gravity.CENTER);
			if( mStrText[position] == null )
				items.setText(mStrText[position]);
			else
			{
				String ellipsizeStr = 
						TextUtils.ellipsize(mStrText[position], 
								mPaint,
								mDisplayWidth / COLUMN_COUNT, 
								TruncateAt.START).toString(); 
				items.setText(ellipsizeStr);
			}
			if( (position < COLUMN_COUNT) )				items.setTextSize(20);	//요일 글씨크기
			else if( (position % COLUMN_COUNT == 0) )	items.setTextSize(16);	//시간 글씨크기
			else										items.setTextSize(15);	//내용 글씨크기

			items.setBackgroundColor(Color.argb(mAlphaColorArray[position], 
					mRedColorArray[position], 
					mGreenColorArray[position], 
					mBlueColorArray[position]));

			return items;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);

		intent = getIntent();
		pFlag = intent.getExtras().getBoolean("pFlag");

		/*전체일정보기*/
		if(pFlag){

			pTravelID = intent.getExtras().getInt("pTravelID");	//id
			pTitle = intent.getExtras().getString("pTitle");		//여행제목
			pDay = intent.getExtras().getInt("pDay");			//여행일수
			COLUMN_COUNT = pDay + 1;
			save_name = pTitle;
			Toast.makeText(this, save_name, Toast.LENGTH_SHORT).show();
		}

		/*하루일정보기*/
		else{

			pDay = 1;
			pTravelID = intent.getExtras().getInt("pTravelID");	//id
			pDayID = intent.getExtras().getInt("pDayID");		//여행일수 ID
			pTitle = intent.getExtras().getString("pTitle");		//여행제목
			COLUMN_COUNT = pDay + 1;
			save_name = pTitle+pDayID+"일차";
			Toast.makeText(this, save_name, Toast.LENGTH_SHORT).show();
			//Toast.makeText(this, pTitle+"	"+pDayID+"일차", Toast.LENGTH_SHORT).show();
		}
		// Received Intent Value;
		GetIntentValue();
		// Initialize;
		Init();
	}

	private void GetIntentValue() {
		// TODO Auto-generated method stub

		mTimeCount = intent.getExtras().getInt( define.INTENT_KEY_TIMECOUNT );
		//mTimeCount = initTime.mTimeCountNumberPicker.getValue();
		mRedColorArray = new int[(mTimeCount + 1) * COLUMN_COUNT];
		mGreenColorArray = new int[(mTimeCount + 1) * COLUMN_COUNT];
		mBlueColorArray = new int[(mTimeCount + 1) * COLUMN_COUNT];
		mAlphaColorArray = new int[(mTimeCount + 1) * COLUMN_COUNT];
		mItemTextArray =  new String[(mTimeCount + 1) * COLUMN_COUNT];

		if( intent.getExtras().getBoolean( define.INTENT_KEY_LOAD_START ) )
		{

			String getTime = intent.getExtras().getString( define.INTENT_KEY_LOAD_TIME );
			String getContent_count = intent.getExtras().getString( define.INTENT_KEY_CONTENT_COUNT );
			String getContent_locate = intent.getExtras().getString( define.INTENT_KEY_CONTENT_LOCATE );
			String getContent_red = intent.getExtras().getString( define.INTENT_KEY_CONTENT_RED );
			String getContent_green = intent.getExtras().getString( define.INTENT_KEY_CONTENT_GREEN );
			String getContent_blue = intent.getExtras().getString( define.INTENT_KEY_CONTENT_BLUE );
			String getContent_alpha = intent.getExtras().getString( define.INTENT_KEY_CONTENT_ALPHA );
			String getContent = intent.getExtras().getString( define.INTENT_KEY_CONTENT );

			int content_count = Integer.parseInt( getContent_count );
			// Get time array & token;
			int time_count_temp = 0;
			String time[] = new String[mTimeCount];
			StringTokenizer token_time = new StringTokenizer(getTime, ",");
			for( int idx = 0; idx < mTimeCount; ++idx )
				time[idx] = token_time.nextToken();
			// Get content_locate array & token;
			String content_locate[] = new String[content_count];
			StringTokenizer token_content_locate = new StringTokenizer(getContent_locate, ",");
			for( int idx = 0; idx < content_count; ++idx )
				content_locate[idx] = token_content_locate.nextToken();
			// Get content_red array & token;
			int content_red_count_temp = 0;
			String content_red[] = new String[content_count];
			StringTokenizer token_content_red = new StringTokenizer(getContent_red, ",");
			for( int idx = 0; idx < content_count; ++idx )
				content_red[idx] = token_content_red.nextToken();
			// Get content_green array & token;
			int content_green_count_temp = 0;
			String content_green[] = new String[content_count];
			StringTokenizer token_content_green = new StringTokenizer(getContent_green, ",");
			for( int idx = 0; idx < content_count; ++idx )
				content_green[idx] = token_content_green.nextToken();
			// Get content_blue array & token;
			int content_blue_count_temp = 0;
			String content_blue[] = new String[content_count];
			StringTokenizer token_content_blue = new StringTokenizer(getContent_blue, ",");
			for( int idx = 0; idx < content_count; ++idx )
				content_blue[idx] = token_content_blue.nextToken();
			// Get content_alpha array & token;
			int content_alpha_count_temp = 0;
			String content_alpha[] = new String[content_count];
			StringTokenizer token_content_alpha = new StringTokenizer(getContent_alpha, ",");
			for( int idx = 0; idx < content_count; ++idx )
				content_alpha[idx] = token_content_alpha.nextToken();
			// Get content array & token;
			int content_count_temp = 0;
			String content[] = new String[content_count];
			StringTokenizer token_content = new StringTokenizer(getContent, ",");
			for( int idx = 0; idx < content_count; ++idx )
				content[idx] = token_content.nextToken();

			for( int idx1 = COLUMN_COUNT; idx1 < (mTimeCount + 1) * COLUMN_COUNT; ++idx1 )
				if( idx1 % COLUMN_COUNT == 0 )
					mItemTextArray[idx1] = time[time_count_temp++];
				else
					for( int idx2 = 0; idx2 < content_count; ++idx2 )
						if( idx1 == Integer.parseInt(content_locate[idx2]) )
						{
							mRedColorArray[idx1] = Integer.parseInt(content_red[content_red_count_temp++]);
							mGreenColorArray[idx1] = Integer.parseInt(content_green[content_green_count_temp++]);
							mBlueColorArray[idx1] = Integer.parseInt(content_blue[content_blue_count_temp++]);
							mAlphaColorArray[idx1] = Integer.parseInt(content_alpha[content_alpha_count_temp++]);
							mItemTextArray[idx1] = content[content_count_temp++];
							break;
						}
		}
		else
		{
			mStartTimeHour = intent.getExtras().getInt( define.INTENT_KEY_STARTTIMEHOUR );
			mStartTimeMin = intent.getExtras().getInt( define.INTENT_KEY_STARTTIMEMIN );
			mTimeSpace = intent.getExtras().getInt( define.INTENT_KEY_TIMESPACE );
			mBreakTime = intent.getExtras().getInt( define.INTENT_KEY_BREAKTIME );
		}
	}

	@SuppressWarnings("deprecation")
	private void Init() {
		// TODO Auto-generated method stub
		((RelativeLayout) findViewById(R.id.tableBackground)).setBackgroundResource(R.drawable.register_bg);
		// Default Text;
		if(pFlag)
			for(int i = 1; i<COLUMN_COUNT; i++)
				mItemTextArray[i] = i+"일차";
		else
			mItemTextArray[1] = pDayID+"일차";			

		for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
		{
			if( idx % COLUMN_COUNT == 0 && mItemTextArray[idx] == null )
			{
				String str = GetTimeString(mStartTimeHour) + ":" + GetTimeString(mStartTimeMin) + "~";
				mItemTextArray[idx] = str;
				mStartTimeMin += mTimeSpace;
				if( mStartTimeMin >= 60 )
				{
					mStartTimeMin -= 60;
					mStartTimeHour += 1;
					if( mStartTimeHour >= 24 )
						mStartTimeHour = 0;
				}
				str = GetTimeString(mStartTimeHour) + ":" + GetTimeString(mStartTimeMin);
				mItemTextArray[idx] += str;
				mStartTimeMin += mBreakTime;
				if( mStartTimeMin >= 60 )
				{
					mStartTimeMin -= 60;
					mStartTimeHour += 1;
					if( mStartTimeHour >= 24 )
						mStartTimeHour = 0;
				}
			}
		}

		String strArray[] = new String[mItemTextArray.length];
		for( int idx = 0; idx < mItemTextArray.length; ++idx )
			strArray[idx] = ( (idx >= COLUMN_COUNT) && (idx % COLUMN_COUNT == 0) ) ? GetTimeString((idx/COLUMN_COUNT)+5) + "시" : mItemTextArray[idx];

		mTimeTable =    (GridView) findViewById(R.id.timeTable);
		mTimeTable.setNumColumns(COLUMN_COUNT);
		mAdapter = new ScheduleAdapter(this, strArray);
		mTimeTable.setAdapter( mAdapter );
		mTimeTable.setOnItemClickListener(this);

	}

	private String GetTimeString( int nValue ) {
		// TODO Auto-generated method stub
		String str = " ";
		if( nValue < 10 )
			str = "0" + nValue;
		else
			str = "" + nValue;
		return str;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long ID) {
		// TODO Auto-generated method stub
		if( position < COLUMN_COUNT )
		{
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle(save_name)
			.setMessage(mItemTextArray[position] + " 여행입니다.")
			.setPositiveButton(string.common_use_ok_btn, null)
			.show();
		}
		else if( position % COLUMN_COUNT == 0 )
		{
			mUpdateTimeSendString = mItemTextArray[position];
			mUpdateTimePosition = position;

			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle(save_name)
			.setMessage("선택 시간은 "+mItemTextArray[position]+"입니다.")
			/*.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					StringTokenizer token = new StringTokenizer(mUpdateTimeSendString, ":~");
					int tokenedValue[] = new int[]{0,0,0,0};
					for( int idx = 0; idx < tokenedValue.length; ++idx )
						tokenedValue[idx] = Integer.parseInt(token.nextToken());
					Intent intent = new Intent(TableActivity.this, UpdateTimeActivity.class);
					intent.putExtra(define.INTENT_KEY_STARTTIMEHOUR, tokenedValue[0]);
					intent.putExtra(define.INTENT_KEY_STARTTIMEMIN, tokenedValue[1]);
					intent.putExtra(define.INTENT_KEY_ENDTIMEHOUR, tokenedValue[2]);
					intent.putExtra(define.INTENT_KEY_ENDTIMEMIN, tokenedValue[3]);
					startActivityForResult(intent, define.INTENT_REQUEST_UPDATETIME);
				}
			})*/
			.setNegativeButton(string.common_use_cancel_btn, null)
			.show();
		}
		else
		{
			if( mItemTextArray[position] == null )
			{
				mInsertTitleSendString = mItemTextArray[position % COLUMN_COUNT] + " " + GetTimeString((position / COLUMN_COUNT)+5) + "시\n" +
						"(" + mItemTextArray[COLUMN_COUNT * (position / COLUMN_COUNT)] + ")";
				mInsertedItemPosition = position;

				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("일정 등록")
				.setMessage(mItemTextArray[position % COLUMN_COUNT] + " " + GetTimeString((position / COLUMN_COUNT)+5) + "시에 일정을 등록할까요?")
				.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(TableActivity.this, InsertActivity.class);
						intent.putExtra(define.INTENT_KEY_INSERTTITLE, mInsertTitleSendString);
						startActivityForResult(intent, define.INTENT_REQUEST_INSERTCONTENT);
					}
				})
				.setPositiveButton(string.common_use_cancel_btn, null)
				.show();
			}
			else
			{
				mInsertTitleSendString = mItemTextArray[position % COLUMN_COUNT] + " " + GetTimeString((position / COLUMN_COUNT)+5) + "시\n" +
						"(" + mItemTextArray[COLUMN_COUNT * (position / COLUMN_COUNT)] + ")";
				mUpdateContentSendString = mItemTextArray[position];
				mUpdatedItemPosition = position;
				final String titleflag= mItemTextArray[position];
				dbHandler = DbHandler.open(this);
				cursor = dbHandler.selectTitle("scrap", mItemTextArray[position], scrapfield);

				while (cursor.moveToNext()) {

					title = cursor.getString(0);
					contentId = cursor.getInt(1);
					homepage = cursor.getString(2);
					imageUrl = cursor.getString(3);
					contentTypeId = cursor.getString(4);
					addr1 = cursor.getString(5);
					addr2 = cursor.getString(6);
					overview = cursor.getString(7);
					tel = cursor.getString(8);
					zipcode = cursor.getString(9);
					EX = cursor.getString(10);
					EY = cursor.getString(11);
				}
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("일정 수정")
				.setMessage(mItemTextArray[position % COLUMN_COUNT] + " " + GetTimeString((position / COLUMN_COUNT)+5) + "시의\n" + 
						"'" + mItemTextArray[position] + "'" + "일정을 수정하시겠습니까?")
						.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(TableActivity.this, UpdateActivity.class);
								intent.putExtra(define.INTENT_KEY_UPDATETITLE, mInsertTitleSendString);
								intent.putExtra(define.INTENT_KEY_UPDATECONTENT, mUpdateContentSendString);
								intent.putExtra(define.INTENT_KEY_UPDATE_RED, mRedColorArray[mUpdatedItemPosition]);
								intent.putExtra(define.INTENT_KEY_UPDATE_GREEN, mGreenColorArray[mUpdatedItemPosition]);
								intent.putExtra(define.INTENT_KEY_UPDATE_BLUE, mBlueColorArray[mUpdatedItemPosition]);
								intent.putExtra(define.INTENT_KEY_UPDATE_ALPHA, mAlphaColorArray[mUpdatedItemPosition]);
								startActivityForResult(intent, define.INTENT_REQUEST_UPDATECONTENT);
							}
						})
						.setPositiveButton(string.common_use_cancel_btn, null)
						.setNeutralButton("정보", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								if(titleflag.equals(title)){
									Intent intent = new Intent(TableActivity.this, 
											com.example.travelmaker.scrap.ScrapInfoView.class);

									intent.putExtra("title", title);
									intent.putExtra("contentId", Integer.toString(contentId));
									intent.putExtra("homepage", homepage);
									intent.putExtra("imageUrl", imageUrl);
									intent.putExtra("contentTypeId", contentTypeId);
									intent.putExtra("addr1", addr1);
									intent.putExtra("addr2", addr2);
									intent.putExtra("overview", overview);
									intent.putExtra("tel", tel);
									intent.putExtra("zipcode", zipcode);
									intent.putExtra("EX", EX);
									intent.putExtra("EY", EY);
									startActivity(intent);
								}


							}
						}).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch( requestCode )
		{
		case define.INTENT_REQUEST_UPDATETIME:
			if( resultCode == define.INTENT_RESULT_SUCCESS )
			{
				mItemTextArray[mUpdateTimePosition] = UpdateTimeActivity.GetUpdatedTime();
			}
			break;
		case define.INTENT_REQUEST_INSERTCONTENT:
			if( resultCode == define.INTENT_RESULT_SUCCESS )
			{
				mItemTextArray[mInsertedItemPosition] = InsertActivity.GetInsertedContent();
				// Get ARGB Value;
				int colorValue[] = InsertActivity.GetInsertedARGB();
				mRedColorArray[mInsertedItemPosition] = colorValue[0];
				mGreenColorArray[mInsertedItemPosition] = colorValue[1];
				mBlueColorArray[mInsertedItemPosition] = colorValue[2];
				mAlphaColorArray[mInsertedItemPosition] = colorValue[3];
				// Invalidate;
				String strArray[] = new String[mItemTextArray.length];
				for( int idx = 0; idx < mItemTextArray.length; ++idx )
					strArray[idx] = ( (idx >= COLUMN_COUNT) && (idx % COLUMN_COUNT == 0) ) ? GetTimeString((idx / COLUMN_COUNT)+5) + "시" : mItemTextArray[idx];
				mAdapter = new ScheduleAdapter(this, strArray);
				mTimeTable.setAdapter( mAdapter );
			}
			break;
		case define.INTENT_REQUEST_UPDATECONTENT:
			if( resultCode == define.INTENT_RESULT_SUCCESS )
			{
				mItemTextArray[mUpdatedItemPosition] = UpdateActivity.GetUpdatedContent();
				// Get ARGB Value;
				int colorValue[] = UpdateActivity.GetUpdatedARGB();
				mRedColorArray[mUpdatedItemPosition] = colorValue[0];
				mGreenColorArray[mUpdatedItemPosition] = colorValue[1];
				mBlueColorArray[mUpdatedItemPosition] = colorValue[2];
				mAlphaColorArray[mUpdatedItemPosition] = colorValue[3];
				// Invalidate;

				String strArray[] = new String[mItemTextArray.length];
				for( int idx = 0; idx < mItemTextArray.length; ++idx )
					strArray[idx] = ( (idx >= COLUMN_COUNT) && (idx % COLUMN_COUNT == 0) ) ? GetTimeString((idx / COLUMN_COUNT)+5) + "시" : mItemTextArray[idx];
				mAdapter = new ScheduleAdapter(this, strArray);
				mTimeTable.setAdapter( mAdapter );
			}
			break;
		default:
			break;
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 0, "저장");
		menu.add(0, 2, 0, "종료");
		return true;
	}*/

	//	옵션 메뉴 호출
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if( item.getItemId() == 1 ) {
			Context mContext = getApplicationContext();
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.dialog_save,(ViewGroup) findViewById(R.id.saveDialog));
			((TextView) layout.findViewById(R.id.saveDialogText)).setText("'"+save_name+"'을 저장합니다.");
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("일정 저장")
			.setView(layout)
			.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {


				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//((TextView) layout.findViewById(R.id.saveDialogText)).setText(save_name+"을 저장합니다.");
					//save_name = ((EditText) layout.findViewById(R.id.saveDialogEdit)).getText().toString();
					ArrayList<String> items = new ArrayList<String>();
					Cursor cursor = TableMainActivity.GetDatabase().selectName(TableMainActivity.GetSQLiteDatabase());
					while( cursor.moveToNext() )
						items.add( cursor.getString(0) );
					boolean bOverlap = false;
					for( int idx = 0; idx < items.size(); ++idx )
						if( save_name.equals(items.get(idx)) ) 
							bOverlap = true;
					if( bOverlap )
					{
						new AlertDialog.Builder(TableActivity.this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle("일정 저장")
						.setMessage( "'" + save_name + "'와 중복된 이름이 있습니다.\n" +
								"그래도 저장하시겠습니까?")
								.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										TableMainActivity.GetDatabase().deleteName(TableMainActivity.GetSQLiteDatabase(), save_name);
										String time_count = "" + mTimeCount;
										String time = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx == COLUMN_COUNT )
												time = mItemTextArray[idx];
											else if( idx % COLUMN_COUNT == 0 )
												time += "," + mItemTextArray[idx];
										String content_count = "";
										int count = 0;
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													++count;
										content_count = "" + count;
										String content_locate = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_locate.equals("") )
														content_locate = "" + idx;
													else
														content_locate += "," + idx;
										String content_red = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_red.equals("") )
														content_red = "" + mRedColorArray[idx];
													else
														content_red += "," + mRedColorArray[idx];
										String content_green = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_green.equals("") )
														content_green = "" + mGreenColorArray[idx];
													else
														content_green += "," + mGreenColorArray[idx];
										String content_blue = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_blue.equals("") )
														content_blue = "" + mBlueColorArray[idx];
													else
														content_blue += "," + mBlueColorArray[idx];
										String content_alpha = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_alpha.equals("") )
														content_alpha = "" + mAlphaColorArray[idx];
													else
														content_alpha += "," + mAlphaColorArray[idx];
										String content = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content.equals("") )
														content = "" + mItemTextArray[idx];
													else
														content += "," + mItemTextArray[idx];
										TableMainActivity.GetDatabase().insertValue(TableMainActivity.GetSQLiteDatabase(), save_name, time_count, time, content_count, content_locate, content_red, content_green, content_blue, content_alpha, content);
										TableMainActivity.ShowPopup("일정 수정", "수정되었습니다.", TableActivity.this);
									}
								})
								.setPositiveButton(string.common_use_cancel_btn, null)
								.show();
					}
					else
					{
						String time_count = "" + mTimeCount;
						String time = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx == COLUMN_COUNT )
								time = mItemTextArray[idx];
							else if( idx % COLUMN_COUNT == 0 )
								time += "," + mItemTextArray[idx];
						String content_count = "";
						int count = 0;
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									++count;
						content_count = "" + count;
						String content_locate = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_locate.equals("") )
										content_locate = "" + idx;
									else
										content_locate += "," + idx;
						String content_red = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_red.equals("") )
										content_red = "" + mRedColorArray[idx];
									else
										content_red += "," + mRedColorArray[idx];
						String content_green = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_green.equals("") )
										content_green = "" + mGreenColorArray[idx];
									else
										content_green += "," + mGreenColorArray[idx];
						String content_blue = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_blue.equals("") )
										content_blue = "" + mBlueColorArray[idx];
									else
										content_blue += "," + mBlueColorArray[idx];
						String content_alpha = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_alpha.equals("") )
										content_alpha = "" + mAlphaColorArray[idx];
									else
										content_alpha += "," + mAlphaColorArray[idx];
						String content = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content.equals("") )
										content = "" + mItemTextArray[idx];
									else
										content += "," + mItemTextArray[idx];
						TableMainActivity.GetDatabase().insertValue(TableMainActivity.GetSQLiteDatabase(), save_name, time_count, time, content_count, content_locate, content_red, content_green, content_blue, content_alpha, content);
						TableMainActivity.ShowPopup("일정 저장", "저장되었습니다.", TableActivity.this);
					}
				}
			})
			.setPositiveButton(string.common_use_cancel_btn, null)
			.show();
		}
		else if( item.getItemId() == 2 ) {
			finish();
		}
		return true;
	}
*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK ){
			Context mContext = getApplicationContext();
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.dialog_save,(ViewGroup) findViewById(R.id.saveDialog));
			((TextView) layout.findViewById(R.id.saveDialogText)).setText("'"+save_name+"'을 저장할까요?");
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("나가기")
			.setView(layout)
			.setNegativeButton("네", new DialogInterface.OnClickListener() {


				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//((TextView) layout.findViewById(R.id.saveDialogText)).setText(save_name+"을 저장합니다.");
					//save_name = ((EditText) layout.findViewById(R.id.saveDialogEdit)).getText().toString();
					ArrayList<String> items = new ArrayList<String>();
					Cursor cursor = TableMainActivity.GetDatabase().selectName(TableMainActivity.GetSQLiteDatabase());
					while( cursor.moveToNext() )
						items.add( cursor.getString(0) );
					boolean bOverlap = false;
					for( int idx = 0; idx < items.size(); ++idx )
						if( save_name.equals(items.get(idx)) ) 
							bOverlap = true;
					if( bOverlap )
					{
						new AlertDialog.Builder(TableActivity.this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle("나가기")
						.setMessage( "'" + save_name + "'의 일정이 수정되었습니다.\n" +
								"그래도 저장하시겠습니까?")
								.setNegativeButton("네", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										TableMainActivity.GetDatabase().deleteName(TableMainActivity.GetSQLiteDatabase(), save_name);
										String time_count = "" + mTimeCount;
										String time = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx == COLUMN_COUNT )
												time = mItemTextArray[idx];
											else if( idx % COLUMN_COUNT == 0 )
												time += "," + mItemTextArray[idx];
										String content_count = "";
										int count = 0;
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													++count;
										content_count = "" + count;
										String content_locate = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_locate.equals("") )
														content_locate = "" + idx;
													else
														content_locate += "," + idx;
										String content_red = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_red.equals("") )
														content_red = "" + mRedColorArray[idx];
													else
														content_red += "," + mRedColorArray[idx];
										String content_green = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_green.equals("") )
														content_green = "" + mGreenColorArray[idx];
													else
														content_green += "," + mGreenColorArray[idx];
										String content_blue = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_blue.equals("") )
														content_blue = "" + mBlueColorArray[idx];
													else
														content_blue += "," + mBlueColorArray[idx];
										String content_alpha = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content_alpha.equals("") )
														content_alpha = "" + mAlphaColorArray[idx];
													else
														content_alpha += "," + mAlphaColorArray[idx];
										String content = "";
										for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
											if( idx % COLUMN_COUNT != 0 )
												if( mItemTextArray[idx] != null )
													if( content.equals("") )
														content = "" + mItemTextArray[idx];
													else
														content += "," + mItemTextArray[idx];
										TableMainActivity.GetDatabase().insertValue(TableMainActivity.GetSQLiteDatabase(), save_name, time_count, time, content_count, content_locate, content_red, content_green, content_blue, content_alpha, content);
										//TableMainActivity.ShowPopup("일정 수정", "수정되었습니다.", TableActivity.this);
										finish();
									}
								})
								.setPositiveButton("아니오", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										finish();
									}})
									.show();
					}
					else
					{
						String time_count = "" + mTimeCount;
						String time = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx == COLUMN_COUNT )
								time = mItemTextArray[idx];
							else if( idx % COLUMN_COUNT == 0 )
								time += "," + mItemTextArray[idx];
						String content_count = "";
						int count = 0;
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									++count;
						content_count = "" + count;
						String content_locate = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_locate.equals("") )
										content_locate = "" + idx;
									else
										content_locate += "," + idx;
						String content_red = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_red.equals("") )
										content_red = "" + mRedColorArray[idx];
									else
										content_red += "," + mRedColorArray[idx];
						String content_green = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_green.equals("") )
										content_green = "" + mGreenColorArray[idx];
									else
										content_green += "," + mGreenColorArray[idx];
						String content_blue = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_blue.equals("") )
										content_blue = "" + mBlueColorArray[idx];
									else
										content_blue += "," + mBlueColorArray[idx];
						String content_alpha = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content_alpha.equals("") )
										content_alpha = "" + mAlphaColorArray[idx];
									else
										content_alpha += "," + mAlphaColorArray[idx];
						String content = "";
						for( int idx = COLUMN_COUNT; idx < mItemTextArray.length; ++idx )
							if( idx % COLUMN_COUNT != 0 )
								if( mItemTextArray[idx] != null )
									if( content.equals("") )
										content = "" + mItemTextArray[idx];
									else
										content += "," + mItemTextArray[idx];
						TableMainActivity.GetDatabase().insertValue(TableMainActivity.GetSQLiteDatabase(), save_name, time_count, time, content_count, content_locate, content_red, content_green, content_blue, content_alpha, content);
						//TableMainActivity.ShowPopup("일정 저장", "저장되었습니다.", TableActivity.this);
						finish();
					}
				}
			})
			.setPositiveButton("아니오", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}})
			.show();

		}

		return super.onKeyDown(keyCode, event);
	}

}
