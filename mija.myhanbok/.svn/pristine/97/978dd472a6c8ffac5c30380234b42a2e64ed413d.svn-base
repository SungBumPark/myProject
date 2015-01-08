package myhanbok.activity;

import java.io.File;
import java.util.ArrayList;

import mija.myhanbok.R;
import myhanbok.adapter.MenuAdapter;
import myhanbok.model.TabMenu;
import myhanbok.server.cFTPClient;
import myhanbok.slide.ClickListenerForScrolling;
import myhanbok.slide.SizeCallbackForMenu;
import myhanbok.slide.SlideView;
import myhanbok.utilities.Config;
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
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyDressRoomActivity extends Activity implements
		OnLongClickListener {
	private View progressDialog;
	int menuWidth;
	private SlideView scrollView;
	private MenuAdapter menuAdapter;
	private View menu;
	private View app;
	private View pager;
	private RelativeLayout over;
	private ImageButton btnSlide;
	private ImageButton btnProfile;
	private ImageView addProfile;
	private static boolean menuOut = false;
	public static boolean canAppTouch = false;
	private ImageView flipCoordFail, flipCoordOk;
	private ArrayList<TabMenu> tabMenu = new ArrayList<TabMenu>();
	private ClickListenerForScrolling scrollContext;
	private TextView slide_name;
	private RelativeLayout r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
	private ImageView i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
	private cFTPClient ftp;
	private Handler m_handler;
	private int num;
	private Bitmap bm;
	private int imageIdx;
	private ImageView mypageProfile, profileImage, mypageText;
	private boolean imageCheck = false;
	String[] stringPath;
	private static String fileName;
	private static int Rid;

	/**
	 * @param args
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		init();

		
		// 프로그레스 바 설정
		progressDialog = findViewById(R.id.progressDialog);
		progressDialog.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		showProgressDialog();
		mypageProfile = (ImageView) app.findViewById(R.id.mypage_profile);
		profileImage = (ImageView) app.findViewById(R.id.mypage_profile_image);
		mypageText = (ImageView) app.findViewById(R.id.mypage_infomsg);
		if (imageCheck == false) {
			mypageProfile.setVisibility(View.VISIBLE);
			profileImage.setVisibility(View.GONE);
			mypageText.setVisibility(View.VISIBLE);
		}

		ImageView reserveOk = (ImageView) app.findViewById(R.id.reservation_ok);
		reserveOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		ImageView reserveFail = (ImageView) app
				.findViewById(R.id.reservation_fail);
		reserveFail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						WebviewActivity.class);
				final String page = "1";
				intent.putExtra("page", page);
				getApplicationContext().startActivity(intent);
			}
		});

		ImageView logout = (ImageView) app.findViewById(R.id.logout_button);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();

				editor.putString("LOGIN_STATE", "FALSE");
				editor.putString("ID", null);
				editor.putString("NAME", null);
				editor.putString("MB_1", null);
				editor.putString("MB_2", null);
				editor.putString("MB_PROFILE_IMG", null);
				editor.commit();

				Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", 500)
						.show();
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		r1 = (RelativeLayout) app.findViewById(R.id.scroll_layout1);
		r2 = (RelativeLayout) app.findViewById(R.id.scroll_layout2);
		r3 = (RelativeLayout) app.findViewById(R.id.scroll_layout3);
		r4 = (RelativeLayout) app.findViewById(R.id.scroll_layout4);
		r5 = (RelativeLayout) app.findViewById(R.id.scroll_layout5);
		r6 = (RelativeLayout) app.findViewById(R.id.scroll_layout6);
		r7 = (RelativeLayout) app.findViewById(R.id.scroll_layout7);
		r8 = (RelativeLayout) app.findViewById(R.id.scroll_layout8);
		r9 = (RelativeLayout) app.findViewById(R.id.scroll_layout9);
		r10 = (RelativeLayout) app.findViewById(R.id.scroll_layout10);

		i1 = (ImageView) r1.findViewById(R.id.coordimg1);
		i2 = (ImageView) r2.findViewById(R.id.coordimg2);
		i3 = (ImageView) r3.findViewById(R.id.coordimg3);
		i4 = (ImageView) r4.findViewById(R.id.coordimg4);
		i5 = (ImageView) r5.findViewById(R.id.coordimg5);
		i6 = (ImageView) r6.findViewById(R.id.coordimg6);
		i7 = (ImageView) r7.findViewById(R.id.coordimg7);
		i8 = (ImageView) r8.findViewById(R.id.coordimg8);
		i9 = (ImageView) r9.findViewById(R.id.coordimg9);
		i10 = (ImageView) r10.findViewById(R.id.coordimg10);

		r1.setOnLongClickListener(this);
		r2.setOnLongClickListener(this);
		r3.setOnLongClickListener(this);
		r4.setOnLongClickListener(this);
		r5.setOnLongClickListener(this);
		r6.setOnLongClickListener(this);
		r7.setOnLongClickListener(this);
		r8.setOnLongClickListener(this);
		r9.setOnLongClickListener(this);
		r10.setOnLongClickListener(this);
		
		flipCoordFail = (ImageView) this.findViewById(R.id.reservation_fail);
		flipCoordFail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
				final String page = "1";
				intent.putExtra("page", page);	
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(intent);
			}
		});
		
		flipCoordOk = (ImageView) this.findViewById(R.id.reservation_ok);
		flipCoordOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
				final String page = "1";
				intent.putExtra("page", page);	
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(intent);
			}
		});


	}

	class ftpDownloadThread extends Thread {
		String Path;

		ftpDownloadThread(String path) {
			Path = path;
		}

		public void run() {
			if (ftp.ftpConnect("sumcnd.com", "kookmin", "kookmin", 21)) {
				Log.d("TAG", "okokokok");
				ftp.ftpMakeDirectory("/public_html/patternimg/" + Path);
				ftp.ftpChangeDirectory("/public_html/patternimg/" + Path);

				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/mija");
				file.mkdir();
				ftp.ftpDownload("/public_html/patternimg/" + Path, Environment
						.getExternalStorageDirectory().getAbsolutePath()
						+ "/mija/");
				ftp.ftpDisconnect();

			}
		}
	}

	class ftpRemoveFile extends Thread {
		String fileName;

		public ftpRemoveFile(String fileName) {
			this.fileName = fileName;
		}

		public void run() {
			if (ftp.ftpConnect("sumcnd.com", "kookmin", "kookmin", 21)) {
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				String id = prefs.getString("ID", "");

				ftp.ftpRemoveFile("/public_html/patternimg/" + id + "/"
						+ fileName);
				ftp.ftpDisconnect();
			}
		}
	}

	class getFileNum extends Thread {

		@Override
		public void run() {
			SharedPreferences prefs = getSharedPreferences("mija.hanbok",
					MODE_PRIVATE);
			String id = prefs.getString("ID", "");
			ftp.ftpConnect("sumcnd.com", "kookmin", "kookmin", 21);
			ftp.ftpMakeDirectory("/public_html/patternimg/" + id);
			ftp.ftpChangeDirectory("/public_html/patternimg/" + id);
			Message m = new Message();
			m_handler.obtainMessage();
			m.what = 1;
			m.arg1 = ftp.getFileNum("/public_html/patternimg/" + id);
			ftp.ftpDisconnect();
			m_handler.sendMessage(m);

		}
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (SlideView) inflater.inflate(
				R.layout.screen_scroll_with_list_menu, null);
		setContentView(scrollView);

		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		app = inflater.inflate(R.layout.mypage, null);

		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.actionbar);

		pager = (View) findViewById(R.id.pager);
		ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar);
		tabMenu = Config.createTab();
		slide_name = (TextView) menu.findViewById(R.id.slide_name);
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
		btnProfile.setVisibility(View.GONE);

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
				case 2:
					intent = new Intent(getApplicationContext(),
							PreviewActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		fileName = null;

		Rid = 0;
		switch (v.getId()) {
		case R.id.scroll_layout1:
			fileName = stringPath[1];
			Rid = R.id.scroll_layout1;
			break;
		case R.id.scroll_layout2:
			fileName = stringPath[2];
			Rid = R.id.scroll_layout2;
			break;
		case R.id.scroll_layout3:
			fileName = stringPath[3];
			Rid = R.id.scroll_layout3;
			break;
		case R.id.scroll_layout4:
			fileName = stringPath[4];
			Rid = R.id.scroll_layout4;
			break;
		case R.id.scroll_layout5:
			fileName = stringPath[5];
			Rid = R.id.scroll_layout5;
			break;
		case R.id.scroll_layout6:
			fileName = stringPath[6];
			Rid = R.id.scroll_layout6;
			break;
		case R.id.scroll_layout7:
			fileName = stringPath[7];
			Rid = R.id.scroll_layout7;
			break;
		case R.id.scroll_layout8:
			fileName = stringPath[8];
			Rid = R.id.scroll_layout8;
			break;
		case R.id.scroll_layout9:
			fileName = stringPath[9];
			Rid = R.id.scroll_layout9;
			break;
		case R.id.scroll_layout10:
			fileName = stringPath[10];
			Rid = R.id.scroll_layout10;
			break;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("파일 삭제");

		builder.setMessage("파일을 삭제 하시겠습니까?");
		builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Thread removeFile = new Thread(new ftpRemoveFile(fileName));
				removeFile.start();
				while (removeFile.isAlive())
					showProgressDialog();

				hideProgressDialog();
				RelativeLayout rv = (RelativeLayout) findViewById(Rid);
				rv.setVisibility(View.GONE);
				onResume();

			}
		});
		builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();

		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		imageIdx = 1;
		r1.setVisibility(View.GONE);
		r2.setVisibility(View.GONE);
		r3.setVisibility(View.GONE);
		r4.setVisibility(View.GONE);
		r5.setVisibility(View.GONE);
		r6.setVisibility(View.GONE);
		r7.setVisibility(View.GONE);
		r8.setVisibility(View.GONE);
		r9.setVisibility(View.GONE);
		r10.setVisibility(View.GONE);
		ftp = new cFTPClient();

		m_handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					num = msg.arg1;
				}
			}
		};

		Thread thread2 = new Thread(new getFileNum());
		thread2.start();
		while (thread2.isAlive())
			;

		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/mija/");
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}

		SharedPreferences prefs = getSharedPreferences("mija.hanbok",
				MODE_PRIVATE);
		String id = prefs.getString("ID", "");
		Thread downloadThread1 = new Thread(new ftpDownloadThread(id));
		downloadThread1.start();
		while (downloadThread1.isAlive())
			;
		stringPath = new String[11];
		files = file.listFiles();
		for (int i = 0; i < files.length && i <= 11; i++) {

			if (files[i].getName().equals("profile.jpg")) {

				bm = BitmapFactory.decodeFile(files[i].getPath());
				bm = getRoundedCornerBitmap(bm, 185);
				profileImage.setImageBitmap(bm);
				profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
				profileImage.setVisibility(View.VISIBLE);
				mypageProfile.setVisibility(View.GONE);
				mypageText.setVisibility(View.GONE);
				imageCheck = true;
			} else {
				bm = BitmapFactory.decodeFile(files[i].getPath());
				switch (imageIdx) {
				case 1:
					i1.setImageBitmap(bm);
					i1.setScaleType(ImageView.ScaleType.FIT_XY);
					stringPath[imageIdx] = files[i].getName();
					r1.setVisibility(View.VISIBLE);
					break;
				case 2:
					i2.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i2.setScaleType(ImageView.ScaleType.FIT_XY);
					r2.setVisibility(View.VISIBLE);
					break;
				case 3:
					i3.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i3.setScaleType(ImageView.ScaleType.FIT_XY);
					r3.setVisibility(View.VISIBLE);
					break;
				case 4:
					i4.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i4.setScaleType(ImageView.ScaleType.FIT_XY);
					r4.setVisibility(View.VISIBLE);
					break;
				case 5:
					i5.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i5.setScaleType(ImageView.ScaleType.FIT_XY);
					r5.setVisibility(View.VISIBLE);
					break;
				case 6:
					i6.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i6.setScaleType(ImageView.ScaleType.FIT_XY);
					r6.setVisibility(View.VISIBLE);
					break;
				case 7:
					i7.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i7.setScaleType(ImageView.ScaleType.FIT_XY);
					r7.setVisibility(View.VISIBLE);
					break;
				case 8:
					i8.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i8.setScaleType(ImageView.ScaleType.FIT_XY);
					r8.setVisibility(View.VISIBLE);
					break;
				case 9:
					i9.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i9.setScaleType(ImageView.ScaleType.FIT_XY);
					r9.setVisibility(View.VISIBLE);
					break;
				case 10:
					i10.setImageBitmap(bm);
					stringPath[imageIdx] = files[i].getName();
					i10.setScaleType(ImageView.ScaleType.FIT_XY);
					r10.setVisibility(View.VISIBLE);
					break;
				}
				imageIdx++;
			}

		}
		hideProgressDialog();

		String state = prefs.getString("LOGIN_STATE", "");
		String name = prefs.getString("NAME", "") + " 님";
		if (state.equals("FALSE"))
			slide_name.setText("로그인을 해주세요");
		else if (state.equals("TRUE"))
			slide_name.setText(name);
	}

	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}

	public void hideProgressDialog() {
		progressDialog.setVisibility(View.GONE);
	}

}
