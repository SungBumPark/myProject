package myhanbok.activity;

import java.util.ArrayList;

import mija.myhanbok.R;
import myhanbok.adapter.MenuAdapter;
import myhanbok.model.TabMenu;
import myhanbok.slide.ClickListenerForScrolling;
import myhanbok.slide.SizeCallbackForMenu;
import myhanbok.slide.SlideView;
import myhanbok.utilities.Config;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener,
		OnTouchListener, OnLongClickListener {

	private View progressDialog;
	static String loginResult;
	int menuWidth;
	private String id, pwd;
	private SlideView scrollView;
	private MenuAdapter menuAdapter;
	private View menu;
	private View app;
	private View pager;
	private RelativeLayout over;
	private ImageButton btnSlide;
	private ImageButton btnProfile;
	private static RelativeLayout belowMenu;
	private ImageView addProfile;
	private static boolean menuOut = false;
	public static boolean canAppTouch = false;
	private ArrayList<TabMenu> tabMenu = new ArrayList<TabMenu>();
	private ClickListenerForScrolling scrollContext;
	private String result;

	private float xAtDown;
	private float xAtUp;
	private View flipCoord;

	private Button VideoPlayButton;
	private ViewFlipper mFlipper;
	private TextView slide_name;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}
		init();

		// 프로그레스 바 설정
		progressDialog = findViewById(R.id.progressDialog);
//		showProgressDialog();
		 hideProgressDialog();

		/** 여기까지 공통부분 */

		menuWidth = menu.getMeasuredWidth();
		belowMenu = (RelativeLayout) app.findViewById(R.id.belowMenu);
		VideoPlayButton = (Button) belowMenu
				.findViewById(R.id.video_play_button);
		VideoPlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), "비디오 재생" ,
				// 500).show();
				Intent myIntent = new Intent(getApplicationContext(),
						VideoViewActivity.class);

				final String mainVideo = "0";
				myIntent.putExtra("videoNo", mainVideo);

				startActivityForResult(myIntent, 1);
			}
		});

		mFlipper = ((ViewFlipper) this.findViewById(R.id.flipper));
		mFlipper.setFlipInterval(4000);
		mFlipper.startFlipping();

		mFlipper.setOnTouchListener(this);
		mFlipper.setOnLongClickListener(this);
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_left_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_left_out));

		flipCoord = (View) this.findViewById(R.id.flip_coord_btn);
		flipCoord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);		
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(intent);
			}
		});
		System.out.println("End of onCreate");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {

			Log.d("result Activity", "finish");
			btnSlide.performClick();
			btnSlide.performClick();
			hideProgressDialog();
		} else if (requestCode == 2)
			hideProgressDialog();
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if (v == mFlipper) {
			return false;
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Context context = menu.getContext();
		int menuWidth = menu.getMeasuredWidth();

		if (scrollContext.isMenuOut()) {
			if (v == pager)
				System.out.println("@2TOUCH WHEN MENUOUT");
		}
		if (v == mFlipper) {
			if (event.getAction() == MotionEvent.ACTION_DOWN)
				xAtDown = event.getX(); // 터치 시작지점 x좌표 저장
			else if (event.getAction() == MotionEvent.ACTION_UP) {
				xAtUp = event.getX(); // 터치 끝난지점 x좌표 저장
				float diff = Math.abs(xAtDown - xAtUp);
				if (xAtUp < xAtDown && diff > 20) {
					mFlipper.stopFlipping();
					// 왼쪽 방향 에니메이션 지정
					mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
							R.anim.push_left_in));
					mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
							R.anim.push_left_out));
					mFlipper.startFlipping();
					// 다음 view 보여줌
					mFlipper.showNext();
				} else if (xAtUp > xAtDown && diff > 20) {
					mFlipper.stopFlipping();
					// 오른쪽 방향 에니메이션 지정
					mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
							R.anim.push_right_in));
					mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
							R.anim.push_right_out));
					// 전 view 보여줌
					mFlipper.showPrevious();
					mFlipper.startFlipping();
				}

				// TODO Auto-generated method stub
				if (scrollContext.isMenuOut()) {
					scrollContext.closeMenu(menuWidth);
				}
			}

		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int menuWidth = menu.getMeasuredWidth();
		/** 코디 창으로 이동 */
		if (v == flipCoord) {
			if (scrollContext.isMenuOut()) {
				System.out.println("touch when closemenu");
				scrollContext.closeMenu(menuWidth);
			} else {
			}
		}
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (SlideView) inflater.inflate(
				R.layout.screen_scroll_with_list_menu, null);
		setContentView(scrollView);

		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		app = inflater.inflate(R.layout.activity_screen_slide, null);

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
		slide_name = (TextView) menu.findViewById(R.id.slide_name);
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
					startActivityForResult(intent, 2);
				} else if (state.equals("TRUE")) {
					Intent intent = new Intent(getApplication(),
							MyDressRoomActivity.class);
					showProgressDialog();
					startActivityForResult(intent, 2);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// return super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("방미자 한복")
					.setMessage("종료 하시겠습니까??")
					.setNegativeButton("종료",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									moveTaskToBack(true);
									finish();
								}
							}).setPositiveButton("취소", null).show();

		}
		return true;
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
		hideProgressDialog();
	}

	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}

	public void hideProgressDialog() {
		progressDialog.setVisibility(View.GONE);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		showProgressDialog();
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		showProgressDialog();
		super.onStop();
	}
	


}
