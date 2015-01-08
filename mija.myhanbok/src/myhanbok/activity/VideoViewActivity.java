package myhanbok.activity;

import mija.myhanbok.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {

	// Declare variables
	ProgressDialog pDialog;
	private String videoURL;
	private String videoNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the layout from video_main.xml
		setContentView(R.layout.videoview_activity);
		// Find your VideoView in your video_main.xml layout
		final VideoView videoview = (VideoView) findViewById(R.id.VideoView);
		// Execute StreamVideo AsyncTask

		// 비디오 번호를 받는다
		Intent intent = getIntent();
		videoNo = intent.getStringExtra("videoNo");

		if (videoNo.equals("1") || videoNo.equals("0"))
			videoURL = "http://kookmin.sumcnd.com/video/vid_1.mp4";
		else if (videoNo.equals("2"))
			videoURL = "http://kookmin.sumcnd.com/video/vid_2.mp4";

		// Create a progressbar
		pDialog = new ProgressDialog(VideoViewActivity.this);
		// Set progressbar title
		pDialog.setTitle("비디오 정보 가져오는 중...");
		// Set progressbar message
		pDialog.setMessage("잠시 기다려 주세요");
		pDialog.setIndeterminate(true);
		pDialog.setCancelable(true);
		// Show progressbar
		pDialog.show();

		try {
			// Start the MediaController
			MediaController mediacontroller = new MediaController(
					VideoViewActivity.this);
			mediacontroller.setAnchorView(videoview);
			// Get the URL from String VideoURL
			Uri video = Uri.parse(videoURL);
			videoview.setMediaController(mediacontroller);
			videoview.setVideoURI(video);

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			pDialog.dismiss();
			e.printStackTrace();
			finish();
		}

		videoview.requestFocus();
		videoview.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				pDialog.dismiss();
				videoview.start();

			}
		});
		MediaPlayer.OnCompletionListener mComplete = new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				finish();
			}
		};
		videoview.setOnCompletionListener(mComplete);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (videoNo.equals("0")) {
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {
			String page = "2";
			Intent intent = new Intent(getApplicationContext(),
					IntromainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("page", page);
			startActivity(intent);
		}
	}
}
