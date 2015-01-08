package com.example.travelmaker.post;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.travelmaker.tour.gpsinfomain.R;

public class PostMainActivity extends Activity{

	private WebView mWebView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main);

		setLayout();

		// 웹뷰에서 자바스크립트실행가능
		mWebView.getSettings().setJavaScriptEnabled(true); 
		// 여행 후기 게시판
		mWebView.loadUrl("http://melo07.dothome.co.kr/xe/board_DIARY");
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
//웹뷰 클라이언트를 만져주어야 새로운 부라우져에서 url이 실행되는것을 막는다. 
	private class WebViewClientClass extends WebViewClient { 
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) { 
//			if(url.startsWith("kakaolink:")){
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//				view.getContext().startActivity(intent);		//이렇게 해결하면 페이지 찾을수없음나온다네 ㅡㅡ 
//			}
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

