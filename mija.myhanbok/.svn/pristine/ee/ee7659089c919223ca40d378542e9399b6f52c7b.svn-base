package myhanbok.belowmenu;

import mija.myhanbok.R;
import myhanbok.activity.IntromainActivity;
import myhanbok.activity.PreviewActivity;
import myhanbok.activity.WebviewActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class BelowMenu extends LinearLayout {

	ImageButton introBtn;
	ImageButton coordBtn;
	ImageButton reserveBtn;
	ImageButton boardBtn;
	ImageButton askBtn;

	public BelowMenu(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		init();
	}
	public BelowMenu(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
		init();
	}

	private void init() {
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				infService);
		li.inflate(R.layout.belowmenu, this, true);

		introBtn = (ImageButton) findViewById(R.id.introBtn);
		coordBtn = (ImageButton) findViewById(R.id.coordBtn);
		reserveBtn = (ImageButton) findViewById(R.id.reserveBtn);
		boardBtn = (ImageButton) findViewById(R.id.boardBtn);
		askBtn = (ImageButton) findViewById(R.id.askBtn);
		clickButton();
	}

	private void clickButton(){
		
		introBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent intro = new Intent(getContext(), IntromainActivity.class);
				final String page = "-1";
				intro.putExtra("page", page);				
				getContext().startActivity(intro);
			}
		});
		
		coordBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getContext(), PreviewActivity.class);				
				getContext().startActivity(intent);
			}
		});
		
		reserveBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getContext(), WebviewActivity.class);
				final String page = "1";
				intent.putExtra("page", page);	
				getContext().startActivity(intent);
			}
		});
		
		boardBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getContext(), WebviewActivity.class);
				final String page = "2";
				intent.putExtra("page", page);	
				getContext().startActivity(intent);
			}
		});
		
		askBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//initImage();
				//memberInfoBtn.setImageDrawable(getResources().getDrawable(R.drawable.mypage_click));
				//memberInfoBtn.setBackgroundResource(R.drawable.selector_mypage);
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"027223020"));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				getContext().startActivity(intent);
			}
		});
	}

}

