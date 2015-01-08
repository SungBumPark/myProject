/**
 * @author Kim Woo Hyeon
 * define.java
 */

package com.example.travelmaker.timetable;

import android.util.Log;

public class define {
	public final static String ROOT_PATH					=	"/sdcard/";
	// Debug param;
	public static final int DEBUG_SHOW						=	0;
	public static final String DEBUG 						=	"Debug Message";
	
	// DataBase param;
	public static final int DB_VERSION						=	1;
	public static final String DB_NAME 						=	"mycontacts.db";
	public static final String DB_TABLE_NAME				=	"SCHEDULE";
	public static final String DB_DEFALUT_DATA_NAME			=	"Default";
	
	// Intent Key param;
	public static final String INTENT_KEY_LOAD_START		=	"LoadStart";
	public static final String INTENT_KEY_LOAD_TIME			=	"LoadTime";
	public static final String INTENT_KEY_CONTENT_COUNT		=	"LoadContentCount";
	public static final String INTENT_KEY_CONTENT_LOCATE	=	"LoadContentLocate";
	public static final String INTENT_KEY_CONTENT_RED		=	"LoadContentRed";
	public static final String INTENT_KEY_CONTENT_GREEN		=	"LoadContentGreen";
	public static final String INTENT_KEY_CONTENT_BLUE		=	"LoadContentBlue";
	public static final String INTENT_KEY_CONTENT_ALPHA		=	"LoadContentAlpha";
	public static final String INTENT_KEY_CONTENT			=	"LoadContent";
	public static final String INTENT_KEY_TIMECOUNT			=	"TimeCount";
	public static final String INTENT_KEY_STARTTIMEHOUR		=	"StartTimeHour";
	public static final String INTENT_KEY_STARTTIMEMIN		=	"StartTimeMin";
	public static final String INTENT_KEY_ENDTIMEHOUR		=	"EndTimeHour";
	public static final String INTENT_KEY_ENDTIMEMIN		=	"EndTimeMin";
	public static final String INTENT_KEY_TIMESPACE			=	"TimeSpace";
	public static final String INTENT_KEY_BREAKTIME			=	"BreakTime";
	public static final String INTENT_KEY_INSERTTITLE		=	"InsertTitle";
	public static final String INTENT_KEY_UPDATETITLE		=	"UpdateTitle";
	public static final String INTENT_KEY_UPDATECONTENT		=	"UpdateContent";
	public static final String INTENT_KEY_UPDATE_RED		=	"UpdateRed";
	public static final String INTENT_KEY_UPDATE_GREEN		=	"UpdateGreen";
	public static final String INTENT_KEY_UPDATE_BLUE		=	"UpdateBlue";
	public static final String INTENT_KEY_UPDATE_ALPHA		=	"UpdateAlpha";
	
	// Intent Request code param;
	public static final int INTENT_RESULT_FAIL				=	-1;
	public static final int INTENT_RESULT_SUCCESS			=	0;
	public static final int INTENT_REQUEST_UPDATETIME		=	1000;
	public static final int INTENT_REQUEST_INSERTCONTENT	=	1001;
	public static final int INTENT_REQUEST_UPDATECONTENT	=	1002;
	public static final int INTENT_REQUEST_EXCHANGEBG		=	1003;
    
	public static void debug( String msg ) {
		// TODO Auto-generated method stub
		if( DEBUG_SHOW == 1 )
		{
			Log.i( DEBUG, DEBUG + " : " + msg );
		}
	}
}
