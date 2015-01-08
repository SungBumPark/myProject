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
	String[] setText;			//(DB) travel���� ������ ��������(title�ʵ�)
	int[] setID;				//(DB) travel���� ������ ID(_id�ʵ�)
	int cnt;					//(DB) travel���� ������ �����Ͱ���
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
		test();					//���� ICON �ѷ��ִ� �Լ�
	}

	/*���� ��� -> ���༱�ÿ� ����� ������� ���� ICON���� ����*/
	private void test() {

		OverallLayout.setVisibility(View.VISIBLE);
		//linear���� ICON 4������
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);

		/*(DB) travel�� ��ϵ� _id����ŭ ICON(BUTTON)����*/
		dbHandler = DbHandler.open(this);
		cursor = dbHandler.selectAll("travel");
		cnt = cursor.getCount();

		setText = new String[cnt];
		setID = new int[cnt];
		btnWord = new Button[cnt];


		int cnt = 0;						//DB���� �޾ƿ� ���� ������ �迭�� INDEX
		while (cursor.moveToNext()) {

			int id = cursor.getInt(0);
			String title = cursor.getString(1);

			setText[cnt] = title;			//DB���� �޾ƿ� title�ʵ��� ���� ����
			setID[cnt] = id;				//DB���� �޾ƿ� _id�ʵ��� ���� ����
			cnt++;
		}

		/* �����ϰ� ���� ���̾ƿ��� �Ķ���� ���� ���� �� */
		LinearLayout.LayoutParams parambtn = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		parambtn.rightMargin = 15;

		int count = 0;
		for (int i = 0; i <= btnWord.length - 1  ; i++) {

			btnWord[i] = new Button(this);
			btnWord[i].setText(setText[i]);			//ICON���������� ǥ��
			btnWord[i].setId(setID[i]);				//ICON�� ���������� ID�� �ο�(DB�� ����� _id)
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

	/*ICON Ŭ���� �������� �������� ���� �����Լ�*/
	OnClickListener btnClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {

			/*�ش� ICON�� ID ����(DB _id)*/
			Intent intent1 = 
					new Intent(PlanMain.this, PlanListActivity.class);
			intent1.putExtra("id", v.getId());
			intent1.putExtra("delList", delList);
			startActivity(intent1);

		}
	};

	/*ICON ��� Ŭ���� ���� �����Լ�*/
	OnLongClickListener btnLongClicked = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {

			final int ID = v.getId();
			AlertDialog.Builder builder = new AlertDialog.Builder(
					v.getContext());

			builder.setTitle("TRAVELMAKER");

			builder.setMessage("������ �����ұ��?");

			/*�˾�â���� Ȯ�ι�ư ������ ���� ����*/
			builder.setNegativeButton("Ȯ��",
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {


					delList.add(delTitle);
					/*DB�� �ش� ICON������ �����ϴ� query�� ���� */
					dbHandler.delete("travel", ID);
					/*ȭ�� �ʱ�ȭ*/
					resetLlinear();
					/*������ DB���� �ٽ� �����͸� �ް� ICON����*/ 
					test();

				}

			});

			builder.setPositiveButton("���",
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}

			});

			builder.show();
			return true;
		}

	};

	/*��ư ���� �� ȭ�� �ʱ�ȭ*/
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
