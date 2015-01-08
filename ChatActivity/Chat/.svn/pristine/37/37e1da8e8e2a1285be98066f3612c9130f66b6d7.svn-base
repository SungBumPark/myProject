package com.example.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class UserListActivity extends Activity {
	private Button refreshbtn;
	private String user, ipaddr, myIp;
	private Socket soc;
	private ListView listView1;
	private int port = 9190;
	private PrintWriter cpw;
	private BufferedReader cbr;
	private SocketHandler sh;
	ArrayList<String> userList;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userlist);

		Intent i = getIntent();
		ipaddr = i.getStringExtra("ipaddress");
		user = i.getStringExtra("userid");
		userList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, userList);
		listView1 = (ListView) findViewById(R.id.userListView);
		listView1.setAdapter(adapter);
		refreshbtn = (Button) findViewById(R.id.refreshbtn);
		refreshbtn.setOnClickListener(new ClickHandler());
		myIp = getLocalIpAddress();
		sh = new SocketHandler();
		sh.start();

		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String clientInfo = (String) parent.getItemAtPosition(position);
				Intent i = new Intent(getApplicationContext(),
						VideoActivity.class);
				i.putExtra("clientInfo", clientInfo);
				i.putExtra("server_ipaddress", ipaddr);
				i.putExtra("userid", user);
				startActivity(i);
			}
		});
	}

	class ClickHandler implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.refreshbtn) {
				cpw.println("_refresh");
				userList.removeAll(userList);
				adapter.notifyDataSetChanged();
				listView1.smoothScrollToPosition(userList.size());
			}
		}
	}

	@SuppressLint("DefaultLocale")
	public String getLocalIpAddress() {
		WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		int ipAddress = wifiInf.getIpAddress();
		String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
				(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
				(ipAddress >> 24 & 0xff));
		Log.e("xxx", ip.toString());
		return ip;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cpw.println("_delete=" + user + ":" + getLocalIpAddress() + ":" + port);
		try {
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class SocketHandler extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				soc = new Socket(ipaddr, port);
				cpw = new PrintWriter(soc.getOutputStream(), true);
				cbr = new BufferedReader(new InputStreamReader(
						soc.getInputStream()));
				cpw.println("_add=" + user + ":" + getLocalIpAddress() + ":"
						+ port);
				cpw.println("_refresh");
				String msg;
				while ((msg = cbr.readLine()) != null) {
					if(msg.equals(" "))
						continue;
					String token[] = msg.split(":");
					if (!myIp.equals(token[1]))
						userList.add(msg);
					runOnUiThread(new Runnable() {
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}