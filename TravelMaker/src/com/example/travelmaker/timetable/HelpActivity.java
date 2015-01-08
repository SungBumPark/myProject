/**
 * @author Kim Woo Hyeon
 * HelpActivity.java
 */

package com.example.travelmaker.timetable;

import com.example.travelmaker.tour.gpsinfomain.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpActivity extends Activity {
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		((RelativeLayout) findViewById(R.id.helpBackground)).setBackgroundDrawable( TableMainActivity.GetBackgroundImage() );
		((TextView) findViewById(R.id.helpText)).setText("안녕하세요. 김우현입니다. 반갑습니다~ 요청 사항 및 궁금한 사항은 cyworld.com/iam1st 또는 facebook.com/iam1sts 로 오세요~");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK )
			finish();
		return super.onKeyDown(keyCode, event);
	}
	
}
