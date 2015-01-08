package com.example.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * A straightforward example of how to use the RTSP server included in
 * libstreaming.
 */
public class VideoActivity extends Activity implements SurfaceHolder.Callback,
		Session.Callback {
	private SurfaceView mSurfaceView;
	private Button yBtn;
	private String target, user, serverIp;
	@SuppressWarnings("unused")
	private Session mSession;
	private CusVideoView mVideoView;
	private Button sndbtn;
	private ListView listView1;
	private EditText mEdit1;

	private Socket soc;
	private int port = 9999;
	private PrintWriter cpw;
	private BufferedReader cbr;
	private SocketHandler sh;

	ArrayList<String> list;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		Intent i = getIntent();
		target = i.getStringExtra("clientInfo");
		user = i.getStringExtra("userid");
		serverIp = i.getStringExtra("server_ipaddress");

		yBtn = (Button) findViewById(R.id.VideoBtn);
		mSurfaceView = (SurfaceView) findViewById(R.id.my_preview);
		mVideoView = (CusVideoView) findViewById(R.id.your_preview);

		mEdit1 = (EditText) findViewById(R.id.message);
		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		listView1 = (ListView) findViewById(R.id.listView1);
		listView1.setAdapter(adapter);
		sndbtn = (Button) findViewById(R.id.sndbtn);
		sndbtn.setOnClickListener(new ClickHandler());

		sh = new SocketHandler();
		sh.start();

		yBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (yBtn.getText().equals("Start")) {
					String[] token = target.split(":");
					Uri video = Uri.parse("rtsp://" + token[1] + ":8086/");
					Log.e("xxx", video.toString());
					mVideoView.setVideoURI(video);
					mVideoView.requestFocus();
					mVideoView.start();
					yBtn.setText("Stop");
				} else if (yBtn.getText().equals("Stop")) {
					yBtn.setText("Start");
					mVideoView.stopPlayback();
				}
			}
		});
		// Sets the port of the RTSP server to 8086
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this)
				.edit();
		editor.putString(RtspServer.KEY_PORT, String.valueOf(8086));
		editor.commit();

		// Configures the SessionBuilder
		mSession = SessionBuilder.getInstance().setCallback(this)
				.setSurfaceView(mSurfaceView)
				.setContext(getApplicationContext())
				.setAudioEncoder(SessionBuilder.AUDIO_NONE)
				.setAudioQuality(new AudioQuality(16000, 32000))
				.setVideoEncoder(SessionBuilder.VIDEO_H264)
				.setVideoQuality(new VideoQuality(320, 240, 20, 500000))
				.build();
		mSurfaceView.getHolder().addCallback(this);

		// Starts the RTSP server
		this.startService(new Intent(this, RtspServer.class));
	}

	@Override
	public void onBitrareUpdate(long bitrate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionError(int reason, int streamType, Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPreviewStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionConfigured() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionStopped() {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		this.stopService(new Intent(this, RtspServer.class));
	}

	class ClickHandler implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.sndbtn) {
				String sndmsg = user + " : " + mEdit1.getText().toString();
				list.add(sndmsg);
				cpw.println(sndmsg);
				mEdit1.setText("");
				adapter.notifyDataSetChanged();
				listView1.smoothScrollToPosition(list.size());
			}
		}
	}

	class SocketHandler extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				soc = new Socket(serverIp, port);
				cpw = new PrintWriter(soc.getOutputStream(), true);
				cbr = new BufferedReader(new InputStreamReader(
						soc.getInputStream()));
				String msg;
				while ((msg = cbr.readLine()) != null) {
					list.add(msg);
					runOnUiThread(new Runnable() {
						public void run() {
							adapter.notifyDataSetChanged();
							listView1.smoothScrollToPosition(list.size());
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
