package myhanbok.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import mija.myhanbok.R;
import mija.myhanbok.R.drawable;
import myhanbok.adapter.MenuAdapter;
import myhanbok.model.TabMenu;
import myhanbok.server.cFTPClient;
import myhanbok.slide.ClickListenerForScrolling;
import myhanbok.slide.SizeCallbackForMenu;
import myhanbok.slide.SlideView;
import myhanbok.utilities.Config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends Activity {
	private static final int SCROLLVIEW_MENU_NUMBER = 3;
	private static final int REQ_CODE_PREVIEW = 1;

	private HorizontalScrollView horizonscrollView[];
	private int btnscrollView_menu[];
	private ImageView imgview_upwear, imgview_downwear;

	private ImageButton imgbtn_upwear, imgbtn_downwear, imgbtn_background,
			imgbtn_save;

	int menuWidth;
	private SlideView scrollView;
	private MenuAdapter menuAdapter;
	private View menu;
	private View app;
	private View pager;
	private RelativeLayout over;
	private ImageButton btnSlide;
	private ImageButton btnProfile;

	private TextView slide_name;
	private ImageView addProfile;
	private static boolean menuOut = false;
	public static boolean canAppTouch = false;
	private ArrayList<TabMenu> tabMenu = new ArrayList<TabMenu>();
	private ClickListenerForScrolling scrollContext;

	private cFTPClient m_FtpClient;

	private phpDown task;

	private Bitmap bmupwear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}

		init();
		Configure();

		slide_name = (TextView) menu.findViewById(R.id.slide_name);
		SharedPreferences prefs = getSharedPreferences("mija.hanbok",
				MODE_PRIVATE);
		String state = prefs.getString("LOGIN_STATE", "");
		String name = prefs.getString("NAME", "") + " 님";
		if (state.equals("FALSE"))
			slide_name.setText("로그인을 해주세요");
		else if (state.equals("TRUE"))
			slide_name.setText(name);
	}

	/*
	 * @Override public void onBackPressed(){
	 * 
	 * }
	 */

	public void mOnClick(View v) {
		switch (v.getId()) {

		case R.id.imgbtn_menu_upstencil:
		case R.id.imgbtn_menu_downstencil:
		case R.id.imgbtn_menu_background:
			for (int i = 0; i < SCROLLVIEW_MENU_NUMBER; i++) {
				if (btnscrollView_menu[i] == v.getId()) {
					horizonscrollView[i].setVisibility(View.VISIBLE);
					continue;
				}
				horizonscrollView[i].setVisibility(View.INVISIBLE);

			}
			btnChange(v.getId());
			break;

		case R.id.imgbtn_upstencil1:
		case R.id.imgbtn_upstencil2:
		case R.id.imgbtn_upstencil3:
		case R.id.imgbtn_upstencil4:
		case R.id.imgbtn_upstencil5:
		case R.id.imgbtn_upstencil6:
		case R.id.imgbtn_downwear1:
		case R.id.imgbtn_downwear2:
			Intent mypatternintent = new Intent(this, MypatternActivity.class);
			mypatternintent.putExtra("stencilnum", v.getId());
			startActivityForResult(mypatternintent, REQ_CODE_PREVIEW);
			break;

		case R.id.imgbtn_background1:
		case R.id.imgbtn_background2:
		case R.id.imgbtn_background3:
		case R.id.imgbtn_background4:
		case R.id.imgbtn_background5:
		case R.id.imgbtn_background6:
		case R.id.imgbtn_background7:
			RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout3);
			layout.setBackgroundResource(selectStencil(v.getId()));
			break;

		case R.id.imgbtn_menu_save:
			SharedPreferences prefs = getSharedPreferences("mija.hanbok",
					MODE_PRIVATE);
			String state = prefs.getString("LOGIN_STATE", "");
			if (state.equals("TRUE") || state.equals("true")) {
				String strid = prefs.getString("ID", "");
				RelativeLayout mylayout = (RelativeLayout) findViewById(R.id.relativeLayout3);
				mylayout.invalidate();
				mylayout.buildDrawingCache();
				String path = saveImage(mylayout.getDrawingCache());
				m_FtpClient = new cFTPClient();
				Toast.makeText(getApplicationContext(), "저장되었습니다.", 1000)
						.show();

				Thread uploadthread = new Thread(new ftpUploadThread(path,
						strid));
				uploadthread.start();
				while (uploadthread.isAlive())
					;
				File file = new File(path);
				if (file.exists())
					file.delete();

				task = new phpDown("/public_html/patternimg/" + strid, strid);
				task.execute("http://kookmin.sumcnd.com/imginsert.php");
			} else {
				Toast.makeText(getApplicationContext(), "로그인후 이용할 수 있습니다.",
						1000).show();
			}

			break;

		}
	}

	private class phpDown extends AsyncTask<String, String, String> {
		String mpath, mbid;

		phpDown(String path, String id) {
			mpath = path;
			mbid = id;
		}

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {

				HttpPost request = new HttpPost(urls[0]);
				// 전달할 인자들
				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
				nameValue.add(new BasicNameValuePair("path", mpath));
				nameValue.add(new BasicNameValuePair("mbid", mbid));
				nameValue.add(new BasicNameValuePair("mbidx", "1"));
				// 웹 접속 - utf-8 방식으로
				HttpEntity enty = new UrlEncodedFormEntity(nameValue,
						HTTP.UTF_8);
				request.setEntity(enty);

				HttpClient client = new DefaultHttpClient();
				HttpResponse res = client.execute(request);
				// 웹 서버에서 값받기
				HttpEntity entityResponse = res.getEntity();
				InputStream im = entityResponse.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(im, HTTP.UTF_8));

				String total = "";
				String tmp = "";
				// 버퍼에있는거 전부 더해주기
				// readLine -> 파일내용을 줄 단위로 읽기
				while ((tmp = reader.readLine()) != null) {
					if (tmp != null) {
						total += tmp;
					}
				}
				im.close();
				// 결과창뿌려주기 - ui 변경시 에러
				// result.setText(total);
				return total;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Log.d("msg", jsonHtml.toString());
			return jsonHtml.toString();
		}

		protected void onPostExecute(String str) {
			super.onPostExecute(str);

		}

	}

	class ftpDownloadThread extends Thread {
		String Path;

		ftpDownloadThread(String path) {
			Path = path;
		}

		public void run() {
			if (m_FtpClient.ftpConnect("sumcnd.com", "kookmin", "kookmin", 21)) {
				Log.d("TAG", "okokokok");
				m_FtpClient.ftpChangeDirectory("/public_html/patternimg");

				m_FtpClient.ftpDownload("/public_html/patternimg/myid",
						Environment.getExternalStorageDirectory()
								.getAbsolutePath() + "/mija/");
				// task = new
				// phpDown(m_FtpClient.ftpGetCurrentWorkingDirectory());
				// task.execute("http://kookmin.sumcnd.com/imginsert.php");

				m_FtpClient.ftpDisconnect();

			}
		}
	}

	class ftpUploadThread extends Thread {
		String Path, ID;

		ftpUploadThread(String path, String id) {
			Path = path;
			ID = id;
		}

		public void run() {
			if (m_FtpClient.ftpConnect("sumcnd.com", "kookmin", "kookmin", 21)) {
				Log.d("TAG", "okokokok");
				m_FtpClient.ftpChangeDirectory("/public_html/patternimg");
				m_FtpClient.ftpMakeDirectory(ID);
				m_FtpClient.ftpChangeDirectory("/public_html/patternimg/" + ID);

				boolean a = m_FtpClient.ftpfileUpload(Path);
				// task = new
				// phpDown(m_FtpClient.ftpGetCurrentWorkingDirectory());
				// task.execute("http://kookmin.sumcnd.com/imginsert.php");

				m_FtpClient.ftpDisconnect();

			}
		}
	}

	public String saveImage(Bitmap bm) {
		int now = (int) System.currentTimeMillis();
		String strDate = Integer.toString(now);
		String strFilename = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/mija/" + strDate + ".jpg";
		try {
			FileOutputStream fos = new FileOutputStream(strFilename);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

			try {
				fos.close();
				return strFilename;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strFilename;
	}

	public void btnChange(int id) {

		if (horizonscrollView[0].getVisibility() == View.VISIBLE)
			imgbtn_upwear.setBackgroundResource(drawable.menu_upstencil_push);
		else
			imgbtn_upwear.setBackgroundResource(drawable.menu_upstencil);

		if (horizonscrollView[1].getVisibility() == View.VISIBLE)
			imgbtn_downwear
					.setBackgroundResource(drawable.menu_downstencil_push);
		else
			imgbtn_downwear.setBackgroundResource(drawable.menu_downstencil);

		if (horizonscrollView[2].getVisibility() == View.VISIBLE)
			imgbtn_background
					.setBackgroundResource(drawable.menu_background_push);
		else
			imgbtn_background.setBackgroundResource(drawable.menu_background);

		/*
		 * if(horizonscrollView[3].getVisibility()==View.VISIBLE)
		 * imgbtn_save.setBackgroundResource(drawable.menu_save_push); else
		 * imgbtn_save.setBackgroundResource(drawable.menu_save);
		 */
		/*
		 * switch(id){ case R.id.imgbtn_menu_upstencil:
		 * if(horizonscrollView[0].getVisibility()==View.VISIBLE)
		 * imgbtn_upwear.setBackgroundResource(drawable.menu_upstencil_push);
		 * else imgbtn_upwear.setBackgroundResource(drawable.menu_upstencil);
		 * break; case R.id.imgbtn_menu_downstencil:
		 * if(horizonscrollView[1].getVisibility()==View.VISIBLE)
		 * imgbtn_downwear
		 * .setBackgroundResource(drawable.menu_downstencil_push); else
		 * imgbtn_downwear.setBackgroundResource(drawable.menu_downstencil);
		 * break; case R.id.imgbtn_menu_background:
		 * if(horizonscrollView[2].getVisibility()==View.VISIBLE)
		 * imgbtn_background
		 * .setBackgroundResource(drawable.menu_background_push); else
		 * imgbtn_background.setBackgroundResource(drawable.menu_background);
		 * break; }
		 */
	}

	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQ_CODE_PREVIEW:
			if (resultCode == RESULT_OK) {
				// int width = data.getIntExtra("width", 1);
				// int height = data.getIntExtra("height", 1);
				// int nPixels[] = new int[width * height];
				int id = data.getIntExtra("stencilnumber", -1);
				// nPixels = data.getIntArrayExtra("pixels");
				// Bitmap bmRect = Bitmap.createBitmap(nPixels,width,
				// height, Bitmap.Config.ARGB_8888);
				Bitmap bmback = Bitmap.createBitmap(1000, 1500,
						Bitmap.Config.ARGB_8888);
				Bitmap bmRect = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory().getAbsolutePath()
						+ "/mija/" + "temp.png");
				// Drawable dr = new BitmapDrawable(bmRect);
				if (checkupwear(id)) {

					// imgview_upwear.setBackgroundDrawable(dr);
					// imgview_upwear.setImageResource(selectStencil(id));
					// imgview_upwear.invalidate();
					// imgview_upwear.buildDrawingCache();
					// bmupwear = imgview_upwear.getDrawingCache();
					Bitmap bmupstencil = BitmapFactory.decodeResource(
							getResources(), selectStencil(id));
					Canvas canvas = new Canvas(bmback);
					canvas.drawBitmap(bmRect, 500 - bmRect.getWidth() / 2, 150,
							null);
					canvas.drawBitmap(bmupstencil, 500 - bmRect.getWidth() / 2,
							150, null);
					imgview_upwear.setImageBitmap(bmback);
					imgview_upwear.setVisibility(View.VISIBLE);
				} else {

					Bitmap bmdownstencil = BitmapFactory.decodeResource(
							getResources(), selectStencil(id));
					int w = bmdownstencil.getWidth();
					int h = bmdownstencil.getHeight();
					Canvas canvas = new Canvas(bmback);
					canvas.drawBitmap(bmRect, 500 - bmRect.getWidth() / 2, 350,
							null);
					canvas.drawBitmap(bmdownstencil,
							500 - bmRect.getWidth() / 2, 350, null);
					// canvas.drawBitmap(bmupwear, 0, 0, null);
					// imgview_downwear.setBackgroundDrawable(dr);
					imgview_downwear.setImageBitmap(bmback);
					// imgview_downwear.setImageResource(selectStencil(id));
					imgview_downwear.setVisibility(View.VISIBLE);
					imgview_upwear.bringToFront();
				}

				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/mija/" + "temp.png");
				file.delete();
			}
			break;
		}

	}

	public boolean checkupwear(int id) {
		switch (id) {
		case R.id.imgbtn_upstencil1:
		case R.id.imgbtn_upstencil2:
		case R.id.imgbtn_upstencil3:
		case R.id.imgbtn_upstencil4:
		case R.id.imgbtn_upstencil5:
		case R.id.imgbtn_upstencil6:
			return true;
		case R.id.imgbtn_downwear1:
		case R.id.imgbtn_downwear2:
			return false;
		default:
			return false;
		}
	}

	public int selectStencil(int id) {
		switch (id) {
		case R.id.imgbtn_upstencil1:
			return drawable.upwear1_stencilview;
		case R.id.imgbtn_upstencil2:
			return drawable.upwear2_stencilview;
		case R.id.imgbtn_upstencil3:
			return drawable.upwear3_stencilview;
		case R.id.imgbtn_upstencil4:
			return drawable.upwear4_stencilview;
		case R.id.imgbtn_upstencil5:
			return drawable.upwear5_stencilview;
		case R.id.imgbtn_upstencil6:
			return drawable.upwear6_stencilview;
		case R.id.imgbtn_downwear1:
			return drawable.downwear_stencilview;
		case R.id.imgbtn_downwear2:
			return drawable.downwear2_stencilview;
		case R.id.imgbtn_background1:
			return drawable.preview_back;
		case R.id.imgbtn_background2:
			return drawable.preview_back2;
		case R.id.imgbtn_background3:
			return drawable.preview_back3;
		case R.id.imgbtn_background4:
			return drawable.preview_back4;
		case R.id.imgbtn_background5:
			return drawable.preview_back5;
		case R.id.imgbtn_background6:
			return drawable.preview_back6;
		case R.id.imgbtn_background7:
			return drawable.preview_back7;
		default:
			return -1;

		}
	}

	// �ʱⰪ ����
	public void Configure() {
		horizonscrollView = new HorizontalScrollView[SCROLLVIEW_MENU_NUMBER];
		for (int i = 0; i < SCROLLVIEW_MENU_NUMBER; i++) {
			horizonscrollView[i] = new HorizontalScrollView(this);
		}

		horizonscrollView[0] = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		horizonscrollView[1] = (HorizontalScrollView) findViewById(R.id.horizontalScrollView2);
		horizonscrollView[2] = (HorizontalScrollView) findViewById(R.id.horizontalScrollView3);

		horizonscrollView[0].setVisibility(View.VISIBLE);
		horizonscrollView[1].setVisibility(View.INVISIBLE);
		horizonscrollView[2].setVisibility(View.INVISIBLE);

		btnscrollView_menu = new int[SCROLLVIEW_MENU_NUMBER];

		btnscrollView_menu[0] = R.id.imgbtn_menu_upstencil;
		btnscrollView_menu[1] = R.id.imgbtn_menu_downstencil;
		btnscrollView_menu[2] = R.id.imgbtn_menu_background;

		imgview_upwear = (ImageView) findViewById(R.id.imgView_up);
		imgview_downwear = (ImageView) findViewById(R.id.imgView_down);

		imgbtn_upwear = (ImageButton) findViewById(R.id.imgbtn_menu_upstencil);
		imgbtn_downwear = (ImageButton) findViewById(R.id.imgbtn_menu_downstencil);
		imgbtn_background = (ImageButton) findViewById(R.id.imgbtn_menu_background);
		imgbtn_save = (ImageButton) findViewById(R.id.imgbtn_menu_save);

	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (SlideView) inflater.inflate(
				R.layout.screen_scroll_with_list_menu, null);
		setContentView(scrollView);

		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		app = inflater.inflate(R.layout.activity_preview, null);

		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.actionbar);

		pager = (View) findViewById(R.id.pager);
		ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar);
		tabMenu = Config.createTab();

		over = (RelativeLayout) app.findViewById(R.id.over);
		addProfile = (ImageView) menu.findViewById(R.id.add_profile);
		addProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				String state = prefs.getString("LOGIN_STATE", "");
				if (state.equals("FALSE")) {
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
				} else if (state.equals("TRUE")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(v
							.getContext());
					builder.setTitle("개인정보");

					builder.setMessage("정보를 입력시겠습니까?");
					builder.setNegativeButton("확인",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											getApplicationContext(),
											JoinActivity.class);
									startActivity(intent);
									btnSlide.performClick();

								}
							});
					builder.setPositiveButton("취소",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					builder.show();
				}
			}
		});
		menuAdapter = new MenuAdapter(this, R.layout.link, tabMenu);
		ListView listView = (ListView) menu.findViewById(R.id.list);
		listView.setAdapter(menuAdapter);

		scrollContext = new ClickListenerForScrolling(getApplicationContext(),
				scrollView, menu, canAppTouch, menuOut, app, over);

		btnSlide = (ImageButton) tabBar.findViewById(R.id.BtnSlide);
		btnSlide.setOnClickListener(scrollContext);

		btnProfile = (ImageButton) tabBar.findViewById(R.id.BtnProfile);
		btnProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				String state = prefs.getString("LOGIN_STATE", "");
				if (state.equals("FALSE")) {
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
				} else if (state.equals("TRUE")) {
					Intent intent = new Intent(getApplication(),
							MyDressRoomActivity.class);
					startActivity(intent);
				}
			}
		});

		final View[] children = new View[] { menu, app };

		// Scroll to app (view[1]) when layout finished.
		int scrollToViewIdx = 1;

		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnSlide));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("SetJavaScriptEnabled")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;

				switch (position) {
				case 0:
					intent = new Intent(getApplicationContext(),
							MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(getApplicationContext(),
							IntromainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					final String page = "-1";
					intent.putExtra("page", page);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(getApplicationContext(),
							WebviewActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					final String web1 = "1";
					intent.putExtra("page", web1);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(getApplicationContext(),
							WebviewActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					final String web2 = "2";
					intent.putExtra("page", web2);
					startActivity(intent);
					break;
				}

			}
		});

		app.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Context context = menu.getContext();

				int menuWidth = menu.getMeasuredWidth();
				// TODO Auto-generated method stub
				if (scrollContext.isMenuOut()) {
					scrollContext.closeMenu(menuWidth);
				}
			}
		});

	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();

		SharedPreferences prefs = getSharedPreferences("mija.hanbok",
				MODE_PRIVATE);
		String state = prefs.getString("LOGIN_STATE", "");
		String name = prefs.getString("NAME", "") + " 님";
		if (state.equals("FALSE"))
			slide_name.setText("로그인을 해주세요");
		else if (state.equals("TRUE"))
			slide_name.setText(name);
	}

}
