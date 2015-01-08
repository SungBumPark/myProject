package com.example.travelmaker.plan;


import java.util.ArrayList;





import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.travelmaker.calendar.DbHandler;
import com.example.travelmaker.calendar.MainActivity;
import com.example.travelmaker.tour.gpsinfomain.*;

public class PlanMain extends Activity {
	public static LinearLayout OverallLayout;
	LinearLayout linear1, linear2, linear3, linear4, linear5;

	DbHandler dbHandler;
	Cursor cursor = null;
	String[] setText;			//(DB) travel에서 가져온 여행제목(title필드)
	int[] setID;				//(DB) travel에서 가져온 ID(_id필드)
	int cnt;					//(DB) travel에서 가져온 데이터갯수
	Button[] btnWord;
	Button btn_before;
	//ArrayList<Integer> delList = new ArrayList<Integer>();
	ArrayList<String> delList = new ArrayList<String>();
	String delTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_travel);
		OverallLayout = (LinearLayout) findViewById(R.id.overAllLayout);
		btn_before = (Button) findViewById(R.id.managed_before);
		btn_before.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		}); 
		test();					//여행 ICON 뿌려주는 함수
	}

	/*여행 등록 -> 여행선택에 등록한 여행들이 각각 ICON으로 생성*/
	private void test() {

		OverallLayout.setVisibility(View.VISIBLE);
		//linear마다 ICON 4개담음
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);

		/*(DB) travel에 등록된 _id수만큼 ICON(BUTTON)생성*/
		dbHandler = DbHandler.open(this);
		cursor = dbHandler.selectAll("travel");
		cnt = cursor.getCount();

		setText = new String[cnt];
		setID = new int[cnt];
		btnWord = new Button[cnt];


		int cnt = 0;						//DB에서 받아온 값을 저장한 배열의 INDEX
		while (cursor.moveToNext()) {

			int id = cursor.getInt(0);
			String title = cursor.getString(1);

			setText[cnt] = title;			//DB에서 받아온 title필드의 값들 저장
			setID[cnt] = id;				//DB에서 받아온 _id필드의 값들 저장
			cnt++;
		}

		/* 변경하고 싶은 레이아웃의 파라미터 값을 가져 옴 */
		LinearLayout.LayoutParams parambtn = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		parambtn.rightMargin = 15;

		int count = 0;
		for (int i = 0; i <= btnWord.length - 1  ; i++) {

			btnWord[i] = new Button(this);
			btnWord[i].setText(setText[i]);			//ICON에여행제목 표시
			btnWord[i].setId(setID[i]);				//ICON에 여행제목의 ID를 부여(DB에 저장된 _id)
			btnWord[i].setPadding(0, 180, 0, 0);
			btnWord[i].setTag(i);
			btnWord[i].setBackgroundResource(R.drawable.btn1);
			btnWord[i].setOnClickListener(btnClicked);
			delTitle = setText[i];
			btnWord[i].setOnLongClickListener(btnLongClicked);
			btnWord[i].setLayoutParams(parambtn);

			if (0 <= count && count <= 3) {
				linear1.addView(btnWord[i]);
			} else if (4 <= count && count <= 7)
				linear2.addView(btnWord[i]);
			else if (8 <= count && count <= 11)
				linear3.addView(btnWord[i]);
			else if (12 <= count && count <= 15)
				linear4.addView(btnWord[i]);
			else if (16 <= count && count <= 19)
				linear5.addView(btnWord[i]);

			count++;
		}
	}

	/*ICON 클릭시 여행일정 페이지로 연결 구현함수*/
	OnClickListener btnClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {

			/*해당 ICON의 ID 전달(DB _id)*/
			Intent intent1 = 
					new Intent(PlanMain.this, PlanListActivity.class);
			intent1.putExtra("id", v.getId());
			intent1.putExtra("delList", delList);
			startActivity(intent1);

		}
	};

	/*ICON 길게 클릭시 삭제 구현함수*/
	OnLongClickListener btnLongClicked = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {

			final int ID = v.getId();
			AlertDialog.Builder builder = new AlertDialog.Builder(
					v.getContext());

			builder.setTitle("TRAVELMAKER");

			builder.setMessage("여행을 삭제할까요?");

			/*팝업창에서 확인버튼 누르면 삭제 실행*/
			builder.setNegativeButton("확인",
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {


					delList.add(delTitle);
					/*DB에 해당 ICON정보를 삭제하는 query문 날림 */
					dbHandler.delete("travel", ID);
					/*화면 초기화*/
					resetLlinear();
					/*수정된 DB에서 다시 데이터를 받고 ICON생성*/ 
					test();

				}

			});

			builder.setPositiveButton("취소",
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}

			});

			builder.show();
			return true;
		}

	};

	/*버튼 삭제 후 화면 초기화*/
	private void resetLlinear(){
		OverallLayout.setVisibility(View.INVISIBLE);
		linear1.removeAllViews();
		linear2.removeAllViews();
		linear3.removeAllViews();
		linear4.removeAllViews();
		linear5.removeAllViews();
	}

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
