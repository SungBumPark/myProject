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
	int id;									//(DB) travel���� ������ ��
	int days;								//(DB) travel���� ������ ��
	String title;							//(DB) travel���� ������ ��
	String dpday;							//(DB) travel���� ������ ��
	Date Dpday;								//dpday���� ��¥�������� ��ȯ�� ��(String to Date)
	Date Today = new Date();				//���� ��¥�� �޾� Dpday�� ���� ��¥�������� ��ȯ�� ��

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

		/*PlanMain.class�� ���� ������(travel TABLE�� _id FIELD) ����*/
		Intent intent2 = getIntent();
		id = intent2.getExtras().getInt("id");

		delList = (ArrayList<String>) intent2.getExtras().getSerializable("delList");
		//Toast.makeText(this, "���ﰹ����?"+delList.size(), Toast.LENGTH_SHORT).show();
		/*(DB) travel TABLE ����*/
		dbHandler = DbHandler.open(this);
		cursor = dbHandler.selectAll("travel");
		/*(DB) �ʿ��� �ʵ常 ����*/
		String[] fields = new String[] {"_id", "title", "dpday", "days"}; 
		/*(DB) travel TABLE�� _id�� query�� ����*/  
		cursor = dbHandler.select("travel", id, fields);

		/*(DB) travel TABLE���� query�� ������ �����ϴ� �����͸� ������ ������ ����*/
		long lgdays = 0;
		while(cursor.moveToNext()){
			title = cursor.getString(1);
			dpday = cursor.getString(2);	//(DB) _id�� �ش��ϴ� ��߳�¥ ���� ���
			lgdays = cursor.getLong(3);		//(DB) ������ �� ���� ���
		}	

		/*��¥������ yyyy��mm��mm�Ϸ� ����*/
		SimpleDateFormat statusBar = 
				new SimpleDateFormat("yyyy�� M�� d�� E����", Locale.KOREA);
		String bar = statusBar.format(Today)+"�Դϴ�";	//Date to String
		/*���� ��¥ ���¹ٿ� ǥ��*/
		txtToday.setText(bar);


		/*��¥������ yyyymmdd�� ����*/
		SimpleDateFormat formater = 
				new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String day = formater.format(Today);	//Date to String

		/*String to Date ����ȯ���� ���ó�¥�� ��߳�¥�� Date�������� ��ȯ*/
		try {
			Today = formater.parse(day);		
			Dpday = formater.parse(dpday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long dDay = dDay();						//dDay()�Լ� ȣ���ؼ� D-DAY ���

		String Dday;
		if(dDay > 0)							//���� �� D-������������ϼ�
			Dday = "D-" + Long.toString(dDay);

		else if(dDay < 0)						//������ ���۵� ��� D+�����ϼ�
			Dday = "D+" + Long.toString(Math.abs(dDay));

		else									//���� ��� ���ϳ�
			Dday = "�����Դϴ� D-0";

		/*String���� D-DAY��� Long to String*/
		txtDday.setText(title+" <"+Dday+">");
		days = (int)lgdays;					//Long to int ����ȯ
		btndays = new Button[days];			//������ �� ��ŭ ��ư��������
		createBtn(days);					//���������� ��ư���� layout����



	}

	/* D-DAY ���
	 * ������(19700101)�κ��� �������� ��¥������ �ð�(milliseconds)�� ��ȯ�Ͽ�
	 * ���� �� �ð��� ��¥������ ����ϴ� �Լ�*/
	public long dDay(){
		long diff = Dpday.getTime() - Today.getTime();
		long diffDays = diff / (24 * 60 * 60* 1000);
		return diffDays;
	}

	/*DB travel���� �޾ƿ� ���� �ϼ� ��ŭ ��ư�� ���� �����ϴ� �Լ�*/
	private void createBtn(int days) {  

		for (int i = 1; i<=days; i++) {

			btndays[i-1] = new Button(this);
			btndays[i-1].setText(i+"���� �����Դϴ�.");
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
				/*�ش� ICON�� ID ����(DB _id)*/
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
