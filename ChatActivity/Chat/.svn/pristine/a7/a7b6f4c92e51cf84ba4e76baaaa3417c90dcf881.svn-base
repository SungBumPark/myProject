package com.example.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText mEdit1;
	private EditText mEdit2;
	private Button btn1;
	private String ipaddr, id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new ClickHandler());
		mEdit1 = (EditText) findViewById(R.id.editText1);
		mEdit2 = (EditText) findViewById(R.id.editText2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class ClickHandler implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.button1) {
				ipaddr = mEdit1.getText().toString();
				id = mEdit2.getText().toString();
				if (ipaddr.equals("") && id.equals("")) {
					Toast.makeText(getApplicationContext(), "IP와 ID를 입력해 주세요",
							Toast.LENGTH_LONG).show();
					return;
				} else if (ipaddr.equals("")) {
					Toast.makeText(getApplicationContext(), "IP주소를 입력해 주세요",
							Toast.LENGTH_LONG).show();
					return;
				} else if (id.equals("")) {
					Toast.makeText(getApplicationContext(), "ID를 입력해 주세요",
							Toast.LENGTH_LONG).show();
					return;
				}

				Intent i = new Intent(getApplicationContext(),
						UserListActivity.class);
				i.putExtra("ipaddress", ipaddr);
				i.putExtra("userid", id);
				startActivity(i);
			}
		}
	}
}
