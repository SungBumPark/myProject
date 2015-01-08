package myhanbok.slide;

import myhanbok.activity.MainActivity;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;

/**
 * Helper for examples with a HSV that should be scrolled by a menu View's
 * width.
 */
public class ClickListenerForScrolling extends Application implements
		OnClickListener {

	SlideView scrollView;
	private View menu;
	private View app;
	private RelativeLayout over;
	private boolean menuOut = false;
	MainActivity main;
	Context context;

	/**
	 * Menu must NOT be out/shown to start with.
	 */

	public ClickListenerForScrolling() {
		// TODO Auto-generated constructor stub
	}

	public ClickListenerForScrolling(Context context, SlideView scrollView,
			View menu, boolean canAppTouch, boolean menuOut, View app,
			RelativeLayout over) {
		super();
		this.context = context;
		this.scrollView = scrollView;
		this.menu = menu;
		this.menuOut = menuOut;
		this.app = app;
		this.over = over;
	}
	
	public ClickListenerForScrolling(Context context, SlideView scrollView,
			View menu, boolean canAppTouch, boolean menuOut, WebView app,
			RelativeLayout over) {
		super();
		this.context = context;
		this.scrollView = scrollView;
		this.menu = menu;
		this.menuOut = menuOut;
		this.app = app;
		this.over = over;
	}


	public void closeMenu(int menuWidth) {
		context = menu.getContext();
		Log.d("===closing slide==", "Scroll to left");
		Log.d("===clicked==", "clicked");
		over.setVisibility(View.GONE);
		int left = menuWidth;
		scrollView.smoothScrollTo(left, 0);
		System.out.println("@22 Hide Tab");
		menuOut = !menuOut;
	}

	
	@Override
	public void onClick(View v) {
		context = menu.getContext();

		int menuWidth = menu.getMeasuredWidth();

		// Ensure menu is visible
		menu.setVisibility(View.VISIBLE);
		if (!menuOut) {
			// Scroll to 0 to reveal menu
			Log.d("===opening slide==", "Scroll to right");
			Log.d("===clicked==", "clicked");
			over.setVisibility(View.VISIBLE);
			scrollView.smoothScrollTo(getScreenWidth(context) / 10, 0);
		} else {
			// Scroll to menuWidth so menu isn't on screen.
			Log.d("===closing slide==", "Scroll to left");
			Log.d("===clicked==", "clicked");
			over.setVisibility(View.GONE);
			int left = menuWidth;
			scrollView.smoothScrollTo(left, 0);

		}
		setMenuOut(!menuOut);
		System.out.println("menuOut : " + menuOut);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	public boolean isMenuOut() {
		return menuOut;
	}

	public void setMenuOut(boolean menuOut) {
		this.menuOut = menuOut;
	}

	public static int getScreenWidth(Activity activity) {
		int width = 0;
		width = activity.getWindowManager().getDefaultDisplay().getWidth();
		return width;
	}

	public static int getScreenWidth(Context context) {
		Display dis = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = dis.getWidth();
		return width;
	}

}
