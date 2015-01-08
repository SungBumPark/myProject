package com.example.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CusVideoView extends VideoView {
	public CusVideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CusVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CusVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
}
