/**
 * @author Kim Woo Hyeon
 * DataBase.java
 */

package com.example.travelmaker.timetable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper{

	public DataBase(Context context) {
		super(context, define.DB_NAME, null, define.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + define.DB_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, save_name TEXT, time_count TEXT, time TEXT, content_count TEXT, content_locate TEXT, content_red TEXT, content_green TEXT, content_blue TEXT, content_alpha TEXT, content TEXT )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + define.DB_TABLE_NAME);
		onCreate(db);  
	}
	
	public void dropTable(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try
		{
			db.execSQL("DROP TABLE IF EXISTS " + define.DB_TABLE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Cursor selectAll(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Cursor cursor = null;
		try
		{
			cursor = db.rawQuery("SELECT * FROM " + define.DB_TABLE_NAME, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}
	
	public Cursor selectName(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Cursor cursor = null;
		try
		{
			cursor = db.rawQuery("SELECT save_name FROM " + define.DB_TABLE_NAME, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}
	
	public Cursor selectInfoForName(SQLiteDatabase db, String save_name) {
		// TODO Auto-generated method stub
		Cursor cursor = null;
		try
		{
			cursor = db.rawQuery("SELECT * FROM " + define.DB_TABLE_NAME + " WHERE save_name='" + save_name + "'", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}
	
	public void deleteName(SQLiteDatabase db, String save_name) {
		// TODO Auto-generated method stub
		Log.i("dbb", "deletecall");
		try
		{
			db.execSQL("DELETE FROM  " + define.DB_TABLE_NAME + " WHERE save_name='" + save_name + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertValue(SQLiteDatabase db, String save_name, String time_count, String time, String content_count, String content_locate, String content_red, String content_green, String content_blue, String content_alpha, String content) {
		// TODO Auto-generated method stub
		try
		{
			db.execSQL("INSERT INTO " + define.DB_TABLE_NAME + " VALUES(null,'" + save_name + "','" + time_count + "','" + time + "','" + content_count + "','" + content_locate + "','" + content_red + "','" + content_green + "','" + content_blue + "','" + content_alpha + "','" + content + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
