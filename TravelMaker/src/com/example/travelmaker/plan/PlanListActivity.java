package com.example.travelmaker.plan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.widget.Toast;

import com.example.travelmaker.calendar.DbHandler;
import com.example.travelmaker.tour.gpsinfomain.*;

public class PlanListActivity extends Activity{
	LinearLayout linear;
	DbHandler dbHandler;
	Cursor cursor = null;
	int id;									//(DB) travel에서 가져온 값
	int days;								//(DB) travel에서 가져온 값
	String title;							//(DB) travel에서 가져온 값
	String dpday;							//(DB) travel에서 가져온 값
	Date Dpday;								//dpday값을 날짜형식으로 변환한 값(String to Date)
	Date Today = new Date();				//오늘 날짜를 받아 Dpday와 같은 날짜형식으로 변환한 값

	Button[] btndays;
	Button planAll, before;
	TextView txtToday, txtDday; 
	ArrayList<String> delList;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_list);
		
		before = (Button) findViewById(R.id.planlist_before);
		before.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		}); 
		linear = (LinearLayout) findViewById(R.id.dayList);
		planAll = (Button) findViewById(R.id.planAll);
		txtToday = (TextView) findViewById(R.id.Today);
		txtDday = (TextView) findViewById(R.id.Dday);

		planAll.setOnClickListener(btnClicked);

		/*PlanMain.class로 부터 데이터(travel TABLE의 _id FIELD) 받음*/
		Intent intent2 = getIntent();
		id = intent2.getExtras().getInt("id");

		delList = (ArrayList<String>) intent2.getExtras().getSerializable("delList");
		//Toast.makeText(this, "지울갯수는?"+delList.size(), Toast.LENGTH_SHORT).show();
		/*(DB) travel TABLE 접근*/
		dbHandler = DbHandler.open(this);
		cursor = dbHandler.selectAll("travel");
		/*(DB) 필요한 필드만 선택*/
		String[] fields = new String[] {"_id", "title", "dpday", "days"}; 
		/*(DB) travel TABLE로 _id로 query문 날림*/  
		cursor = dbHandler.select("travel", id, fields);

		/*(DB) travel TABLE에서 query문 조건을 만족하는 데이터만 가져와 변수에 저장*/
		long lgdays = 0;
		while(cursor.moveToNext()){
			title = cursor.getString(1);
			dpday = cursor.getString(2);	//(DB) _id에 해당하는 출발날짜 정보 담김
			lgdays = cursor.getLong(3);		//(DB) 여행일 수 정보 담김
		}	

		/*날짜형식을 yyyy년mm월mm일로 설정*/
		SimpleDateFormat statusBar = 
				new SimpleDateFormat("yyyy년 M월 d일 E요일", Locale.KOREA);
		String bar = statusBar.format(Today)+"입니다";	//Date to String
		/*오늘 날짜 상태바에 표시*/
		txtToday.setText(bar);


		/*날짜형식을 yyyymmdd로 설정*/
		SimpleDateFormat formater = 
				new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String day = formater.format(Today);	//Date to String

		/*String to Date 형변환으로 오늘날짜와 출발날짜를 Date형식으로 변환*/
		try {
			Today = formater.parse(day);		
			Dpday = formater.parse(dpday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long dDay = dDay();						//dDay()함수 호출해서 D-DAY 계산

		String Dday;
		if(dDay > 0)							//여행 전 D-여행까지남은일수
			Dday = "D-" + Long.toString(dDay);

		else if(dDay < 0)						//여행이 시작된 경우 D+지난일수
			Dday = "D+" + Long.toString(Math.abs(dDay));

		else									//여행 출발 당일날
			Dday = "오늘입니다 D-0";

		/*String으로 D-DAY출력 Long to String*/
		txtDday.setText(title+" <"+Dday+">");
		days = (int)lgdays;					//Long to int 형변환
		btndays = new Button[days];			//여행일 수 만큼 버튼동적생성
		createBtn(days);					//동적생성된 버튼마다 layout적용



	}

	/* D-DAY 계산
	 * 기준일(19700101)로부터 변수값인 날짜까지의 시간(milliseconds)로 변환하여
	 * 연산 후 시간을 날짜단위로 출력하는 함수*/
	public long dDay(){
		long diff = Dpday.getTime() - Today.getTime();
		long diffDays = diff / (24 * 60 * 60* 1000);
		return diffDays;
	}

	/*DB travel에서 받아온 여행 일수 만큼 버튼을 동적 생성하는 함수*/
	private void createBtn(int days) {  

		for (int i = 1; i<=days; i++) {

			btndays[i-1] = new Button(this);
			btndays[i-1].setText(i+"일차 여행입니다.");
			btndays[i-1].setId(i);
			btndays[i-1].setOnClickListener(btnClicked);
			btndays[i-1].setHeight(50);
			btndays[i-1].setWidth(50);

			if( ((i-1)%7)==0 )
			{
				btndays[i-1].setBackgroundResource(R.drawable.list_bg);
			}
			else if( ((i-1)%7)==1 )
			{
				btndays[i-1].setBackgroundResource(R.drawable.list_bg1);
			}
			else if ( ((i-1)%7)==2 )
			{
				btndays[i-1].setBackgroundResource(R.drawable.list_bg2);
			}
			else if ( ((i-1)%7)==3 )
			{
				btndays[i-1].setBackgroundResource(R.drawable.list_bg3);
			}
			else if ( ((i-1)%7)==4 )
			{
				btndays[i-1].setBackgroundResource(R.drawable.list_bg4);
			}
			else if ( ((i-1)%7)==5 )
			{
				btndays[i-1].setBackgroundResource(R.drawable.list_bg5);
			}
			else if ( ((i-1)%7)==6 )
			{
				btndays[i-1].setBackgroundResource(R.drawable.list_bg6);
			}
			linear.addView(btndays[i-1]);	
		}
	}

	OnClickListener btnClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {

			boolean allFlag = true;
			Intent intent1 = 
					new Intent(PlanListActivity.this, com.example.travelmaker.timetable.TableMainActivity.class);

			if(v.getId()==R.id.planAll){
				allFlag = true;
				intent1.putExtra("flag", allFlag);
				/*해당 ICON의 ID 전달(DB _id)*/
				intent1.putExtra("_travel", id);
				intent1.putExtra("title", title);
				intent1.putExtra("day", days);
				intent1.putExtra("delList", delList);
			}

			else{

				allFlag = false;
				intent1.putExtra("flag", allFlag);
				intent1.putExtra("_travel", id);
				intent1.putExtra("dayID", v.getId());
				intent1.putExtra("title", title);
				intent1.putExtra("day", days);
				intent1.putExtra("delList", delList);
			}

			allFlag = true;
			startActivity(intent1);
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//return super.onKeyDown(keyCode, event);
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			finish();
		}

		return true;
	}



}
