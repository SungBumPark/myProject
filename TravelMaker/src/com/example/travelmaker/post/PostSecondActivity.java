package com.example.travelmaker.post;
import com.example.travelmaker.tour.gpsinfomain.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PostSecondActivity extends Activity{
	private WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_second);
		setLayout();
		// 웹뷰에서 자바스크립트실행가능
		mWebView.getSettings().setJavaScriptEnabled(true); 
		// Q & A 게시판 
		mWebView.loadUrl("http://melo07.dothome.co.kr/xe/board_QNA");
		// WebViewClient 지정
		mWebView.setWebViewClient(new WebViewClientClass());  
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) { 
			mWebView.goBack(); 
			return true; 
		} 
		return super.onKeyDown(keyCode, event);
	}

	private class WebViewClientClass extends WebViewClient { 
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) { 
			if(url.startsWith("kakaolink:")){
				loadTime(url);
			}
			else
				view.loadUrl(url); 
			return true; 
		} 
	}
	private void setLayout(){
		mWebView = (WebView) findViewById(R.id.webview);
	}
	public void loadTime(String url)		//로드 타임 함수 추가 브라우저블 카테고리 추가, 외부  어플 실행 put추가 
	{
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		intent.addCategory(intent.CATEGORY_BROWSABLE);
		intent.putExtra(Browser.EXTRA_APPLICATION_ID,getPackageName());

		startActivity(intent);
	}
}

