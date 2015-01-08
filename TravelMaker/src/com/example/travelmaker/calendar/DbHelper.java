package com.example.travelmaker.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "MYDB140523";
	private static final int DB_VERSION = 1;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String travel_sql = "CREATE TABLE travel("+
				"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"title TEXT NOT NULL,"+
				"dpday TEXT NOT NULL,"+
				"dptime TEXT NOT NULL,"+
				"rtday TEXT NOT NULL,"+
				"rttime TEXT NOT NULL," +
				"days INTEGER NOT NULL)";
				
		
		String planlist_sql = "CREATE TABLE planlist("+
								"_travel INTEGER NOT NULL,"+	//����
								"day INTERGER NOT NULL,"+		//�� ������ ����
								"schedule TEXT NOT NULL,"+	//���� ����
								"strtime TEXT NOT NULL,"+	//���� �ð�
								"endtime TEXT NOT NULL,"+	//���� �ð�
								"frndnum TEXT NOT NULL," +	//�����(travel ���̺� �ְų�)
								"memo TEXT NOT NULL," +		//�޸�
								"cost TEXT NOT NULL," +		//���
								"place TEXT NOT NULL," +	//���(����)
								"trsprt TEXT NOT NULL," +	//�̵�(����)
								"opt_trsprt TEXT NOT NULL," +//�̵�����
								"FOREIGN KEY(_travel) REFERENCES travel(_id) ON DELETE CASCADE)";
		
		String scrap_sql = "CREATE TABLE scrap("+
				"title TEXT,"+
				"contentId INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"homepage TEXT,"+
				"imageUrl TEXT," +
				"contentTypeId TEXT,"+
				"addr1 TEXT,"+
				"addr2 TEXT,"+
				"overview TEXT,"+
				"tel TEXT,"+
				"zipcode TEXT,"+
				"EX TEXT,"+
				"EY TEXT)";
				

		db.execSQL(travel_sql);					//(DB)travel TABLE ���� query��
		Log.i("db","create_table_travel");
		db.execSQL(planlist_sql);				//(DB)planlist TABLE ���� query��
		Log.i("db","create_table_planlist");
		db.execSQL(scrap_sql);					//(DB)scrap TABLE ���� query��
		Log.i("db","create_table_scrap");
	}//onCreate  ���̺����

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exist travel");
		db.execSQL("drop table if exist planlist");
		db.execSQL("drop table if exist scrap");
		onCreate(db);

	}

}
