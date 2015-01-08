package myhanbok.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import mija.myhanbok.R;
import mija.myhanbok.R.drawable;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MypatternActivity extends Activity{
	private static final int SCROLLVIEW_MENU_NUMBER = 2;
	private static final int REQ_CODE_PICK_IMAGE = 0;

	private HorizontalScrollView horizonscrollView[];
	private int btnscrollView_menu[];
	
	private ImageView imgView_maskstencil, imgView_mypattern;
	private ImageButton imgBtnbtnFinish;
	private int stencilnumber;
	
	private Bitmap bmmypattern;
	
	private static final float STEP = 500;
	private int mBaseDist;
	private float mRatio = 1.0f;
	private float mBaseRatio;

    private static float touchposX1, touchposX2, touchposY1, touchposY2;
    private static final int MOVERATE = 1;
    private static int touchdx = 0, touchdy = 0;
    
    private Rect tempimgRect;
	
    private Bitmap changedBitmap;
    
    private boolean bImgViewMyPattern_Visible = false;
    
    private int scaledx = 0, scaledy = 0;
    
    private String sdcard;
    
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepattern);
        stencilnumber = getIntent().getIntExtra("stencilnum", 0);
        
        makeDirectory();        
        Configure();
        touchdx = 0;
        touchdy = 0;
    }

	public String saveImage(Bitmap bm){
		int now = (int)System.currentTimeMillis();
		String strDate = Integer.toString(now);
		String strFilename = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/mija/" + strDate + ".jpg";
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
		return null;
		
	}
	
	//directory ?앹꽦
	public void makeDirectory(){
		sdcard = Environment.getExternalStorageState();
        File file = null;
        if(!sdcard.equals(Environment.MEDIA_MOUNTED)){
        	file = Environment.getRootDirectory();
        }else{
        	file = Environment.getExternalStorageDirectory();
        }
        
        String dir = file.getAbsolutePath() + "/mija";
        file = new File(dir);
        if(!file.exists()){
        	file.mkdir();
        }
	}
	
	public void resizeCalledImg(float scale){
		int bmpWidth = bmmypattern.getWidth();
		int bmpHeight = bmmypattern.getHeight();
		int newHeight = (int) (bmpHeight * scale);
		int newWidth = (int) (bmpWidth * scale);
		  
		
		Log.d("size", Integer.toString((newWidth - bmpWidth)/2));
		Log.d("size", Integer.toString((newHeight - bmpHeight)/2));

		tempimgRect.offset((newWidth - bmpWidth)/2 - scaledx,
				(newHeight - bmpHeight)/2 - scaledy);
		
		scaledx = (newWidth - bmpWidth)/2;
		scaledy = (newHeight - bmpHeight)/2;
		
		changedBitmap = Bitmap.createScaledBitmap(bmmypattern, newWidth, newHeight,
				true);
		Log.d("bitmap", Integer.toString(changedBitmap.getWidth()));

		imgView_mypattern.setImageBitmap(changedBitmap);
	
	}


	
	public boolean onTouchEvent(MotionEvent event){

		if(event.getPointerCount() == 1){
			
			if(event.getAction() == MotionEvent.ACTION_DOWN && bImgViewMyPattern_Visible == true){
				touchposX1 = event.getX();
				touchposY1 = event.getY();
				Log.d("left", Integer.toString(tempimgRect.left));
			}
			
			if(event.getAction() == MotionEvent.ACTION_MOVE && bImgViewMyPattern_Visible == true){
				touchposX2 = event.getX();
				touchposY2 = event.getY();

				touchdx += (int)(touchposX2 - touchposX1);
				touchdy += (int)(touchposY2 - touchposY1);
				Log.d("dx", Integer.toString(touchdx));
					
				imgView_mypattern.scrollTo(-1*touchdx*MOVERATE, -1*touchdy*MOVERATE);
				
				tempimgRect.offset((int)(touchposX1 - touchposX2), (int)(touchposY1 - touchposY2));
				Log.d("left", Integer.toString(tempimgRect.left));
								
				touchposX1 = touchposX2;
				touchposY1 = touchposY2;
				return true;
			}
		}
		

		if(event.getPointerCount() == 2 && bImgViewMyPattern_Visible == true){
			int action = event.getAction();
			int pureaction = action & MotionEvent.ACTION_MASK;
			if(pureaction == MotionEvent.ACTION_POINTER_DOWN){
				mBaseDist = getDistance(event);
				mBaseRatio = mRatio;
			}else{
				float delta = (getDistance(event) - mBaseDist) / STEP;
				float multi = (float)Math.pow(2, delta);
				mRatio = Math.min(20.0f, Math.max(0.1f, mBaseRatio * multi));
				resizeCalledImg(mRatio);

			}
			return true;
		}
		return false;
		
	}
	
	int getDistance(MotionEvent event){
		int dx = (int)(event.getX(0) - event.getX(1));
		int dy = (int)(event.getY(0) - event.getY(1));
		return (int)(Math.sqrt(dx * dx + dy * dy));
		
	}
	
	
	public void mOnClick(View v){
		switch(v.getId()){
		case R.id.imgbtn_mypattern:
			Intent intent = new Intent();
		    intent.setAction(Intent.ACTION_GET_CONTENT);
		    intent.setType("image/*");
		    startActivityForResult(intent, REQ_CODE_PICK_IMAGE);

			break;
			
		case R.id.imgbtn_menu_temppattern: case R.id.imgbtn_menu_mypattern:
			for(int i=0; i<SCROLLVIEW_MENU_NUMBER; i++){
				if(btnscrollView_menu[i] == v.getId()){
					horizonscrollView[i].setVisibility(View.VISIBLE);
					continue;
				}
				horizonscrollView[i].setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.imgbtn_temppattern1:case R.id.imgbtn_temppattern2:case R.id.imgbtn_temppattern3: case R.id.imgbtn_temppattern4:
		case R.id.imgbtn_temppattern5:case R.id.imgbtn_temppattern6:case R.id.imgbtn_temppattern7: case R.id.imgbtn_temppattern8:
		case R.id.imgbtn_temppattern9:case R.id.imgbtn_temppattern10:case R.id.imgbtn_temppattern11:
			

			BitmapDrawable drawable = (BitmapDrawable) getResources()
					.getDrawable(selectTempPattern(v.getId()));
			Bitmap bmSource = drawable.getBitmap();

			bmmypattern = bmSource;
			changedBitmap = bmSource;
			Log.d("debu", "3");
			BitmapDrawable drawable2 = (BitmapDrawable) getResources()
					.getDrawable(selectMask(stencilnumber));
			
			Bitmap bm = drawable2.getBitmap();
			int bitmapCenterX = bmSource.getWidth() / 2;
			int bitmapCenterY = bmSource.getHeight() / 2;

			tempimgRect.set(bitmapCenterX - bm.getWidth() / 2,
					bitmapCenterY - bm.getHeight() / 2,
					bitmapCenterX + bm.getWidth() / 2,
					bitmapCenterY + bm.getHeight() / 2);
			Log.d("rect", Integer.toString(tempimgRect.left));

			imgView_mypattern.setImageBitmap(bmSource);
			bImgViewMyPattern_Visible = true;
			imgView_mypattern.setVisibility(View.VISIBLE);
			imgBtnbtnFinish.setVisibility(View.VISIBLE);
			imgView_maskstencil.bringToFront();

			break;
		//case 
			
		case R.id.imgbtn_mypatternfinish:

			if(!checkRange()){
				Toast.makeText(getApplicationContext(), "이미지를 정확하게 맞춰주세요", 1000).show();
				return;
			}
				
			Intent backIntent = new Intent();	
			imageCut();
			//backIntent.putExtra("pixels", imageCut());
			//backIntent.putExtra("width", tempimgRect.width());
			//backIntent.putExtra("height", tempimgRect.height());
			backIntent.putExtra("stencilnumber", stencilnumber);	
			setResult(RESULT_OK, backIntent);
			finish();
			break;
		}
	}
	
	
	public boolean checkRange(){
		if(tempimgRect.left < 0 ||
				tempimgRect.right >= changedBitmap.getWidth() ||
				tempimgRect.top < 0 ||
				tempimgRect.bottom >= changedBitmap.getHeight()){
			return false;
		}
		return true;
	}
	
	public void imageCut(){
		int width = tempimgRect.width();
		int height = tempimgRect.height();

		int nPixels[] = new int[width * height];
		
		changedBitmap.getPixels(nPixels, 0, width, tempimgRect.left, tempimgRect.top, width,
				height);
		
		Bitmap bmMask = BitmapFactory.decodeResource(getResources(),
				selectMask(stencilnumber));
		
		
		Log.d("masksize", Integer.toString(tempimgRect.left));
		Log.d("masksize", Integer.toString(tempimgRect.top));

		for(int i=0; i<nPixels.length; i++){
				nPixels[i] &= bmMask.getPixel(i%width, i/width); 
			
		}
		
		Bitmap bmRect = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bmRect.setPixels(nPixels, 0, width, 0, 0, width, height);
		String strFilename = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/mija/" + "temp.png";
		try {
			FileOutputStream fos = new FileOutputStream(strFilename);
			bmRect.compress(Bitmap.CompressFormat.PNG, 100, fos);
			
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imgView_mypattern.setVisibility(View.INVISIBLE);
		bImgViewMyPattern_Visible = false;

		//return nPixels;
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	switch(requestCode){
    	case REQ_CODE_PICK_IMAGE:
    		if(resultCode == Activity.RESULT_OK){
    			if(data != null){
    			
    				
    				Bitmap bmSource;
					try {
						bmSource = Images.Media.getBitmap(getContentResolver(), data.getData());
						bmmypattern = bmSource;
	    				changedBitmap = bmSource;
	    				Log.d("debu", "3");
	    				int bitmapCenterX = bmSource.getWidth()/2;
	    				int bitmapCenterY = bmSource.getHeight()/2;
	    				
	    				BitmapDrawable drawable = (BitmapDrawable) getResources()
	    						.getDrawable(selectMask(stencilnumber));
	    				
	    				Bitmap bm = drawable.getBitmap();
	    				
	    				tempimgRect.set(bitmapCenterX - bm.getWidth()/2, 
	    						bitmapCenterY - bm.getHeight()/2, 
	    						bitmapCenterX + bm.getWidth()/2, 
	    						bitmapCenterY + bm.getHeight()/2); 
	    				//Log.d("imgView_mypattern",Integer.toString(imgView_maskstencil.getWidth()));
	    				//Log.d("rect",Integer.toString(tempimgRect.top));
	    				//Log.d("imgView_mypattern",Integer.toString(imgView_maskstencil.getHeight()));
	    				//Log.d("rect",Integer.toString(tempimgRect.bottom));
	                    imgView_mypattern.setImageBitmap(bmSource);
	    				//imgView_mypattern.setImageURI(data.getData());
	                    bImgViewMyPattern_Visible  = true;
	                    imgView_mypattern.setVisibility(View.VISIBLE);
	                    imgBtnbtnFinish.setVisibility(View.VISIBLE);
	                    imgView_maskstencil.bringToFront();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
    				

                    
    			}
    		}
    		break;
    	}
    	
    }
	
	public void Configure(){
		horizonscrollView = new HorizontalScrollView[SCROLLVIEW_MENU_NUMBER];
    	for(int i=0; i<SCROLLVIEW_MENU_NUMBER; i++){
    		horizonscrollView[i] = new HorizontalScrollView(this);
    	}
    	
    	horizonscrollView[0] = (HorizontalScrollView)findViewById(R.id.horizontalScrollView1);
    	horizonscrollView[1] = (HorizontalScrollView)findViewById(R.id.horizontalScrollView2);
    	
    	horizonscrollView[0].setVisibility(View.VISIBLE);
    	horizonscrollView[1].setVisibility(View.INVISIBLE);
    	
    	
    	btnscrollView_menu = new int[SCROLLVIEW_MENU_NUMBER];
    	btnscrollView_menu[0] = R.id.imgbtn_menu_temppattern;
    	btnscrollView_menu[1] = R.id.imgbtn_menu_mypattern;
    	
    	imgView_maskstencil = (ImageView)findViewById(R.id.imgview_maskstencil);
    	imgView_mypattern = (ImageView)findViewById(R.id.imgview_mypattern);
    	imgBtnbtnFinish = (ImageButton)findViewById(R.id.imgbtn_mypatternfinish);
    	
    	imgView_maskstencil.setBackgroundResource(selectStencil(stencilnumber));

    	tempimgRect = new Rect();
    	
	}
	
	public int selectTempPattern(int id){
		switch(id){
		case R.id.imgbtn_temppattern1: return drawable.pattern_temp1;
		case R.id.imgbtn_temppattern2: return drawable.pattern_temp2;
		case R.id.imgbtn_temppattern3: return drawable.pattern_temp3;
		case R.id.imgbtn_temppattern4: return drawable.pattern_temp4;
		case R.id.imgbtn_temppattern5: return drawable.pattern_temp5;
		case R.id.imgbtn_temppattern6: return drawable.pattern_temp6;
		case R.id.imgbtn_temppattern7: return drawable.pattern_temp7;
		case R.id.imgbtn_temppattern8: return drawable.pattern_temp8;
		case R.id.imgbtn_temppattern9: return drawable.pattern_temp9;
		case R.id.imgbtn_temppattern10: return drawable.pattern_temp10;
		case R.id.imgbtn_temppattern11:return drawable.pattern_temp11;
		
		default: return -1;
		}
	}

	public int selectStencil(int id) {
		switch (id) {
		case R.id.imgbtn_upstencil1: return drawable.upwear1_stencilview;		
		case R.id.imgbtn_upstencil2: return drawable.upwear2_stencilview;
		case R.id.imgbtn_upstencil3: return drawable.upwear3_stencilview;
		case R.id.imgbtn_upstencil4: return drawable.upwear4_stencilview;		
		case R.id.imgbtn_upstencil5: return drawable.upwear5_stencilview;
		case R.id.imgbtn_upstencil6: return drawable.upwear6_stencilview;
		case R.id.imgbtn_downwear1:  return drawable.downwear_stencilview;
		case R.id.imgbtn_downwear2:  return drawable.downwear2_stencilview;
		default: return -1;
			
		}
	}
	
	public int selectMask(int id){
		switch (id) {
		case R.id.imgbtn_upstencil1: return drawable.mask_upwear1_stencil;		
		case R.id.imgbtn_upstencil2: return drawable.mask_upwear2_stencil;
		case R.id.imgbtn_upstencil3: return drawable.mask_upwear3_stencil;
		case R.id.imgbtn_upstencil4: return drawable.mask_upwear4_stencil;		
		case R.id.imgbtn_upstencil5: return drawable.mask_upwear5_stencil;
		case R.id.imgbtn_upstencil6: return drawable.mask_upwear6_stencil;
		case R.id.imgbtn_downwear1: return drawable.mask_downwear1_stencil;
		case R.id.imgbtn_downwear2: return drawable.mask_downwear2_stencil;
		default: return -1;
			
		}
	}
	
	
}











