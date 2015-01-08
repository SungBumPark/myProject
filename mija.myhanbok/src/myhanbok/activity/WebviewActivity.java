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
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WebviewActivity extends Activity {

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
	private ArrayList<TabMenu> tabMenu = new ArrayList<TabMenu>();
	private ClickListenerForScrolling scrollContext;
	private WebView mWebView;

	private int page = 0;
	private TextView slide_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		init();

		Intent intent = getIntent();
		page = Integer.parseInt(intent.getStringExtra("page"));

		mWebView = (WebView) app.findViewById(R.id.reserve_webview);
		mWebView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int menuWidth = menu.getMeasuredWidth();
				// TODO Auto-generated method stub
				if (scrollContext.isMenuOut()) {
					scrollContext.closeMenu(menuWidth);
				}
				return false;
			}
		});

		SharedPreferences prefs = getSharedPreferences("mija.hanbok",
				MODE_PRIVATE);
		String state = prefs.getString("LOGIN_STATE", "");
		String id = prefs.getString("ID", "");
		String url = null;
		if (state.equals("TRUE") || state.equals("true"))
			url = "http://kookmin.sumcnd.com/m_reser.php?mbid=" + id;
		else if (state.equals("FALSE") || state.equals("false"))
			url = "http://kookmin.sumcnd.com/m_reser.php";
		
		if (page == 1) {
			if (state.equals("TRUE") || state.equals("true"))
				mWebView.loadUrl(url);
			else if (state.equals("FALSE") || state.equals("false"))
				mWebView.loadUrl(url);
		} else
			mWebView.loadUrl("http://kookmin.sumcnd.com/m/bbs/board.php?bo_table=after");
		mWebView.setWebViewClient(new WebViewClientClass());
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (SlideView) inflater.inflate(
				R.layout.screen_scroll_with_list_menu, null);
		setContentView(scrollView);

		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		app = inflater.inflate(R.layout.reserve_webview, null);

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
							MyDressRoomActivity.class);
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
				scrollView, menu, canAppTouch, menuOut, mWebView, over);

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

	private class WebViewClientClass extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
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
