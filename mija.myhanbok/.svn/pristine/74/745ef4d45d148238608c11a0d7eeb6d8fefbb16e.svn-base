package myhanbok.activity;


import mija.myhanbok.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class StartApp extends Activity{

	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main_loading);
        
        SharedPreferences prefs = getSharedPreferences("mija.hanbok", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        editor.putString("NAME" , null);
        editor.putString("MB_1" , null);
        editor.putString("MB_2" , null);
        editor.putString("PROFILE_IMG", null);
        editor.putString("LOGIN_STATE", "FALSE");
        editor.putString("ID", null);
        editor.commit();
        
        String temp = prefs.getString("LOGIN_STATE", "");
        System.out.println(temp);
        intent = new Intent(this, MainActivity.class);
        
        startActivityWithDelay();

	}

	private void startActivityWithDelay() {
		// TODO Auto-generated method stub
		 Handler handler = new Handler();
	        handler.postDelayed(new Runnable() {

	            public void run() {
	            	
	                startActivity(intent);
	                
	                finish();
	            }
	        //Do Something 1000 = 1s
	        }, 3000);



	}
}
