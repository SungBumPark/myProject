package com.example.travelmaker.calendar;

import java.util.Calendar;
import java.util.Date;

import com.example.travelmaker.plan.RegisterPlanActivity;
import com.example.travelmaker.tour.gpsinfomain.R;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends ListActivity implements OnClickListener{
	private View progressDialog;

	/*DB변수*/
	DbHandler dbHandler;
	Cursor cursor = null;

	/*LAYOUT변수*/
	EditText edtName; 												
			// 여행제목
	Button btnIns,btnResult,btnDpday,btnDptime,btnRtday,btnRttime;	
			//등록, 		보기, 	시작일, 	 시작시간, 	      종료일,    종료시간

	/*날짜&시간 변수*/
	Calendar c;
	protected int mYear;
	protected int mMonth;
	protected int mDay;
	protected int mHour;
	protected int mMinute;
	Date dpday;
	Date rtday;	
	StringBuilder Dpday;					//시작일Date to String
	StringBuilder Rtday;					//종료일Date to String
	Button btn_alarm;						//알람버튼
	/*시작일&시간 종료일&시간 구분변수*/
	int btn_date=0;
	int btn_time=0;
	boolean time = false;

	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}
	public void hideProgressDialog() {
		progressDialog.setVisibility(View.GONE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cal_layout_regist);
		progressDialog = findViewById(R.id.progressDialog);

		/*등록 버튼 활성화*/
		btnIns = (Button) findViewById(R.id.btnIns);		
		btnIns.setOnClickListener(this);		

		/*보기 버튼 활성화*/
		btnResult = (Button) findViewById(R.id.btnResult);
		btnResult.setOnClickListener(this);		

		/*여행제목 버튼*/
		edtName = (EditText) findViewById(R.id.edtName);

		/*시작일 버튼 활성화*/
		btnDpday = (Button) findViewById(R.id.btnDpday);
		btnDpday.setOnClickListener(this);				

		/*시작시간 버튼 활성화*/
		btnDptime = (Button) findViewById(R.id.btnDptime);	
		btnDptime.setOnClickListener(this);	

		/*종료일 버튼 활성화*/
		btnRtday = (Button) findViewById(R.id.btnRtday);	
		btnRtday.setOnClickListener(this);

		/*종료시간 버튼 활성화*/
		btnRttime = (Button) findViewById(R.id.btnRttime);	
		btnRttime.setOnClickListener(this);			

		/*날짜&시간 변수에 현재시각 저장 */
		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		
		/* 알람 클릭 */
		btn_alarm = (Button) findViewById(R.id.Alarm);
		btn_alarm.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
			    Intent Alarm = new Intent(MainActivity.this, com.example.travelmaker.plan.Alarm.class);
			    startActivity(Alarm);
			   }
			  }); 
		
		
		
	}

	@Override
	public void onClick(View v) {
		/*DB OPEN*/
		dbHandler = DbHandler.open(this);

		try{

			/*시작일 버튼 */
			if(v.getId()==R.id.btnDpday){

				btn_date = 1;				//시작일과 종료일 구분 변수(시작일  = 1, 종료일  = -1)

				/*날짜선택 창 띄움*/
				new DatePickerDialog(this, mDateSetListener, 
						c.get(Calendar.YEAR),
						c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH)).show();
			}

			/*시작시간 버튼 */
			else if(v.getId()==R.id.btnDptime){

				time = true;				//날짜인지 시간인지 구분 변수
				btn_time = 1;				//시작시간과 종료시간 구분 변수(시작시간  = 1, 종료시간  = -1)
				
				
				/*시간선택 창 띄움*/
				new TimePickerDialog(this, mTimeSetListener,
						c.get(Calendar.HOUR_OF_DAY),
						c.get(Calendar.MINUTE),
						true).show();
			}

			/*종료일 버튼 */
			else if(v.getId()==R.id.btnRtday){

				btn_date = -1;				//시작일과 종료일 구분 변수(시작일  = 1, 종료일  = -1)

				/*날짜선택 창 띄움*/
				new DatePickerDialog(this, 
						mDateSetListener, 
						c.get(Calendar.YEAR),
						c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH)).show();
			}

			/*종료시간 버튼 */
			else if(v.getId()==R.id.btnRttime){

				time = true;				//날짜인지 시간인지 구분 변수
				btn_time = -1;				//시작시간과 종료시간 구분 변수(시작시간  = 1, 종료시간  = -1)

				/*시간선택 창 띄움*/
				new TimePickerDialog(this,
						mTimeSetListener,
						c.get(Calendar.HOUR_OF_DAY),
						c.get(Calendar.MINUTE),
						true).show();
			}

			/*등록 버튼 */
			if(v.getId()==R.id.btnIns){
				//showProgressDialog();

				/*시작일과 종료일로부터 여행일수 저장*/
				long days = numOfDays()+1;

				/*DB에 저장정보 INSERT하는 query문*/
				dbHandler.travelInsert(
						edtName.getText().toString(), 
						Dpday.toString(),
						btnDptime.getText().toString(),
						Rtday.toString(),
						btnRttime.getText().toString(),
						days);

				String sleep = Long.toString(days-1);
				String day = Long.toString(days);
				//Thread.sleep(2000);
				//hideProgressDialog();

				Toast.makeText(this, sleep+"박"+day+"일여행 등록완료", 
						Toast.LENGTH_SHORT).show();
			}

			/*보기 버튼 */
			else if(v.getId()==R.id.btnResult){
				
				finish();
				
				/*(DB) travel TABLE 모든 필드 선택
				cursor = dbHandler.selectAll("travel");
				
				travel의 레코드 갯수만큼 데이터저장 배열 생성
				String[] DBdata = new String[cursor.getCount()];

				int count = 0;	//배열 INDEX

				배열에 레코드 저장
				while(cursor.moveToNext()){

					int id = cursor.getInt(0);				//(DB) travel의 _id 필드
					String title = cursor.getString(1);		//(DB) travel의 title 필드
					String dpday = cursor.getString(2);		//(DB) travel의 dpday 필드
					String dptime = cursor.getString(3);	//(DB) travel의 dptime 필드
					String rtday = cursor.getString(4);		//(DB) travel의 rtday 필드
					String rttime = cursor.getString(5);	//(DB) travel의 rttime 필드
					long day = cursor.getLong(6);			//(DB) travel의 days 필드
					String days = Long.toString(day);		//배열에 담기위해 Long to String 형변환

					DBdata[count] = id + " " + title + " " + dpday + " " +
							dptime + " " + rtday + " " + rttime + " " + days;

					count++;
				}

				cursor.close();

				
				배열을 화면에 출력
				ArrayAdapter adapter = 
						new ArrayAdapter(this, android.R.layout.simple_list_item_1, DBdata);
				
				
				setListAdapter(adapter);
				setListAdapter(adapter);*/
			

				/* 성범오빠꺼 scrap TABEL 확인용
				 * cursor = dbHandler.selectAll("scrap");
				int test = cursor.getCount();

				Toast.makeText(this, "scrap" + test, Toast.LENGTH_SHORT).show();
				cursor.close();*/
			}
		}catch (Exception e){
			Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
		}

	}

	/*날짜선택 팝업*/
	DatePickerDialog.OnDateSetListener mDateSetListener = 
			new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			/*사용자가 선택한 날짜를 날짜변수에 저장*/
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			/*날짜 또는 시간을 버튼위에 표시(setText)*/
			updateDisplay();
		}
	};

	/*시간선택 팝업*/
	TimePickerDialog.OnTimeSetListener mTimeSetListener = 
			new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			/*사용자가 선택한 시간을 날짜변수에 저장*/
			mHour = hourOfDay;
			mMinute = minute;

			/*날짜 또는 시간을 버튼위에 표시(setText)*/
			updateDisplay();
		}
	};

	/* 여행일 수 계산
	 * 기준일(19700101)로부터 변수값인 날짜까지의 시간(milliseconds)로 변환하여
	 * 연산 후 시간을 날짜단위로 출력하는 함수*/
	public long numOfDays(){
		//도착일 - 시작일 
		long diff = rtday.getTime() - dpday.getTime();
		long diffDays = diff / (24 * 60 * 60* 1000);
		return diffDays;
	}

	/*날짜&시간 선택 팝업창에서 사용자가 선택한 날짜&시간을 버튼위에 보여주는 함수*/
	void updateDisplay(){

		int year = mYear;
		int month = mMonth;
		int day = mDay;
		int hour = mHour;
		int minute = mMinute;

		/*시작시간 또는 종료시간인 경우 */
		if(time){

			/*시간을 hh : mm 형식으로 출력*/
			StringBuilder Time = new StringBuilder().append(Timepad(hour)).append(" : ")
					.append(pad(minute));

			/*시작시간일 때*/
			if(btn_time > 0){
				btnDptime.setText(Time);
				btn_time = 0;}					//구분 변수(시작시간인지 종료시간인지) 초기화

			/*종료시간일 때*/
			else if(btn_time < 0){
				btnRttime.setText(Time);
				btn_time = 0;}					//구분 변수(시작시간인지 종료시간인지) 초기화

			time = false;						//구분 변수(시간인지 날짜인지) 초기화
		}

		/*시작일 또는 종료일인 경우 */

		/*날짜를 yyyy년 mm월 dd일 형식으로 출력*/
		StringBuilder Date = new StringBuilder().append(year).append("년 ")
				.append(pad(month+1)).append("월 ").append(pad(day)).append("일");

		/*시작일 */
		if(btn_date > 0){
			/*(DB) travel의 dpday필드에 저장 될 시작일 변수(Date) yyyymmdd형식*/
			Dpday = new StringBuilder().append(year)
					.append(pad(month+1)).append(pad(day));

			dpday = new Date(mYear, mMonth, mDay);
			btnDpday.setText(Date);
			btn_date = 0;}					//구분 변수(시작일인지 종료일인지) 초기화

		/*종료일 */
		else if(btn_date < 0){
			/*(DB) travel의 rtday필드에 저장 될 종료일 변수(Date) yyyymmdd형식*/
			Rtday = new StringBuilder().append(year)
					.append(pad(month+1)).append(pad(day));

			rtday = new Date(mYear, mMonth, mDay);
			btnRtday.setText(Date);
			btn_date = 0;					//구분 변수(시작일인지 종료일인지) 초기화
		}
	}

	/* mm월 dd일 또는 mm분형식으로 출력하는 함수 */
	protected Object pad(int i) {
		if(i>=10)										//2자리 경우
			return String.valueOf(i);					//그대로 표시

		else											//1자리 경우
			return "0" + String.valueOf(i);				//앞에 0을 붙여 2자리로 표시 
	}

	/* 오전|오후 출력함수 */
	private Object Timepad(int i) {

		if(i>=10)										
		{	
			if(i>=13)									//13~23시 경우
				return "오후 " + String.valueOf(i-12);	//오후 1~12로 표시

			else if(i==12)								//12시 경우
				return "오후" + String.valueOf(i);		//오후 12시로 표시

			else										//10~11시 경우
				return "오전 " + String.valueOf(i);		//오전 10~11시로 표시
		}
		else											//0~9시인 경우 
			return "오전 " + String.valueOf(i);			//오전 0~9시로 표시
	}

}