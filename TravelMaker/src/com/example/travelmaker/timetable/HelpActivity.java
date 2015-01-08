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
		((TextView) findViewById(R.id.helpText)).setText("�ȳ��ϼ���. ������Դϴ�. �ݰ����ϴ�~ ��û ���� �� �ñ��� ������ cyworld.com/iam1st �Ǵ� facebook.com/iam1sts �� ������~");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK )
			finish();
		return super.onKeyDown(keyCode, event);
	}
	
}
