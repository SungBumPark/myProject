package com.example.travelmaker.calendar;

import com.example.travelmaker.main.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbHandler {
	private Context ctx;
	private DbHelper helper;
	private SQLiteDatabase db;

	public DbHandler(Context ctx){
		this.ctx = ctx;
		helper = new DbHelper(ctx); 		//DB¿Í TABLE CREATE, UPDATE 
		db = helper.getWritableDatabase();
	}
	
	public static DbHandler open(Context ctx) throws SQLException{
		DbHandler handler = new DbHandler(ctx);
		return handler;
	}
	public void close(){
		helper.close();
	}
	public long travelInsert(String title, String dpday, String dptime, String rtday,
			String rttime, long days){
		
		Log.i("db", "call_traveInsert()");
		
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("dpday", dpday);
		values.put("dptime", dptime);
		values.put("rtday", rtday);
		values.put("rttime", rttime);
		values.put("days", days);

		long result= db.insert("travel", null, values);
		return result;
	}

	public long planlistInsert(int _travel, int day, String schedule,
			String strtime, String endtime, String frndnum, String memo,
			String cost, String place, String trsprt, String opt_trsprt){
		
		Log.i("db", "call_planlistInsert()");
		
		ContentValues values = new ContentValues();
		values.put("_travel", _travel);
		values.put("day", day);
		values.put("schedule", schedule);
		values.put("strtime", strtime);
		values.put("endtime", endtime);
		values.put("frndnum", frndnum);
		values.put("memo", memo);
		values.put("cost", cost);
		values.put("place", place);
		values.put("trsprt", trsprt);
		values.put("opt_trsprt", opt_trsprt);

		long result= db.insert("planlist", null, values);
		return result;
	}
	
	public long scrapInsert(String title, int contentId, 
			String homepage, String imageUrl, String contentTypeId, String addr1, String addr2, 
			String overview, String tel, String zipcode, String EX, String EY){
		
		Log.i("db","call_scrapInsert()");
		
		ContentValues values = new ContentValues();
		values.put("title", title);
		Log.d(GPSInfoMain.DEBUG, title);
		values.put("contentId", contentId);
		Log.d(GPSInfoMain.DEBUG, "! " + contentId);
		values.put("homepage", homepage);
		Log.d(GPSInfoMain.DEBUG, "! " + homepage);
		values.put("imageUrl", imageUrl);
		Log.d(GPSInfoMain.DEBUG, "! " + imageUrl);
		values.put("contentTypeId", contentTypeId);
		Log.d(GPSInfoMain.DEBUG, "! " + contentTypeId);
		values.put("addr1", addr1);
		Log.d(GPSInfoMain.DEBUG, "! " + addr1);
		values.put("addr2", addr2);
		Log.d(GPSInfoMain.DEBUG, "! " + addr2);
		values.put("overview", overview);
		Log.d(GPSInfoMain.DEBUG, "! " + overview);
		values.put("tel", tel);
		Log.d(GPSInfoMain.DEBUG, "! " + tel);
		values.put("zipcode", zipcode);
		values.put("EX", EX);
		values.put("EY", EY);

		long result= db.insert("scrap", null, values);
		return result;
	}
	
	public Cursor selectAll(String table) {
		Cursor cursor = null;

		if(table == "travel"){
			cursor = db.query(true, "travel",
					new String[] {"_id","title","dpday","dptime","rtday","rttime","days"},
					null, null, null, null, null,null);
			Log.i("db","cursor_travel");
		}

		else if(table == "planlist"){
			cursor = db.query(true, "planlist",
					new String[] {"_travel","day","schedule","strtime","endtime",
					"frndnum","memo","cost","place","trsprt","opt_trsprt"},
					null, null, null, null, null,null);
			Log.i("db","cursor_planlist");
		}

		else if(table == "scrap"){
			cursor = db.query(true, "scrap",
					new String[] {"title","contentId","homepage","imageUrl", 
					"contentTypeId", "addr1", "addr2", 
					"overview", "tel", "zipcode", "EX", "EY"},
					null, null, null, null, null,null);
			Log.i("db","cursor_scrap");
		}

		return cursor;
	}	
	
	public Cursor select(String table, int id, String[] fields){
		Cursor cursor = null;
		
		cursor = db.query(table, fields, 
                "_id LIKE "+ id, null, null, null, null);
		
		Log.i("db", table+" select()");
		return cursor;
	}
	
	public Cursor selectTitle(String table, String title, String[] fields){
		Cursor cursor = null;
		
		cursor = db.query(table, fields, 
                "title LIKE \""+ title + "\"", null, null, null, null);
		
		Log.i("db", table+" selectTitle()");
		return cursor;
	}
	public void delete(String table, int id){
		
		String sql = "DELETE FROM " + table + " WHERE _id = " + id;
		db.execSQL(sql);
		
		Log.i("db", table+" delete()");
	}
	
	public void deleteScrap(String table, int contentId){
		String sql = "DELETE FROM " + table + " WHERE contentId = " + contentId;
		db.execSQL(sql);
		
		Log.i("db", table+" delete()");
	}
}