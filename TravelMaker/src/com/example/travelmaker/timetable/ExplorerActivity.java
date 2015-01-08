/**
 * @author Kim Woo Hyeon
 * ExplorerActivity.java
 */

package com.example.travelmaker.timetable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.travelmaker.tour.gpsinfomain.R;
import com.example.travelmaker.tour.gpsinfomain.R.string;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExplorerActivity extends ListActivity {

	private List<String> mItems = null;
	private List<String> mPaths = null;
	private List<String> mSavePath = null;
	private TextView mNowLocation;
	private static String mSelectedFilePath = "";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explorer);

		((LinearLayout) findViewById(R.id.explorerBackground)).setBackgroundDrawable(com.example.travelmaker.timetable.TableMainActivity.GetBackgroundImage());

		mSelectedFilePath = "";
		mNowLocation = (TextView)findViewById(R.id.explorerPath);
		mSavePath = new ArrayList<String>();

		mSavePath.add( define.ROOT_PATH );
		getDir( define.ROOT_PATH );
	}

	private void getDir(String dirPath) {
		// TODO Auto-generated method stub
		mNowLocation.setText("Location: " + dirPath);

		mItems = new ArrayList<String>();
		mPaths = new ArrayList<String>();

		File f = new File(dirPath);
		File[] files = f.listFiles();
		String strTemp = mSavePath.get( mSavePath.size() - 1 );
		if( strTemp != dirPath )
			mSavePath.add( dirPath );

		for( int idx = 0; idx < files.length; ++idx )
		{
			File file = files[idx];
			mPaths.add(file.getPath());

			if(file.isDirectory())
				mItems.add(file.getName() + "/");
			else
				mItems.add(file.getName());
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
		setListAdapter(fileList); 
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(mPaths.get(position));

		if (file.isDirectory()) {
			if(file.canRead())
				getDir(mPaths.get(position));
			else{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("[" + file.getName() + "]" + "을 열 수 없습니다")
				.setPositiveButton(string.common_use_ok_btn, null)
				.show();
			}
		}
		else {
			int pos = file.getName().lastIndexOf(".");
			String fileExtension = file.getName().substring( pos + 1 );
			// Check extension;
			if( fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("bmp") || fileExtension.equals("png") )
			{
				mSelectedFilePath = file.getPath();
				
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("파일 선택")
				.setMessage("[" + file.getName() + "]" + "를 선택하시겠습니까?")
				.setNegativeButton(string.common_use_ok_btn, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						setResult(define.INTENT_RESULT_SUCCESS);
						finish();
					}
				})
				.setPositiveButton(string.common_use_cancel_btn, null)
				.show();
			}
			else
				Toast.makeText(this, "선택할 수 없는 확장자 입니다.", Toast.LENGTH_SHORT).show();
		}
	}

	public static String GetSelectedFilePath() {
		// TODO Auto-generated method stub
		return mSelectedFilePath;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			if( mSavePath.size() > 1 )
			{
				mSavePath.remove(mSavePath.size() - 1);
				String lastPath = mSavePath.get(mSavePath.size() - 1);
				getDir( lastPath );
				return true;
			}
			else
			{
				setResult(define.INTENT_RESULT_FAIL);
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}

