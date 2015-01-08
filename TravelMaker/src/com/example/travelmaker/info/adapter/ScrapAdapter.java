package com.example.travelmaker.info.adapter;

import java.util.*;

import com.example.travelmaker.image.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.*;
import com.example.travelmaker.tour.gpsinfomain.*;

import android.app.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class ScrapAdapter extends ArrayAdapter<ScrapTourData> {

	private ArrayList<ScrapTourData> datalist;
	private Context mCtx;
	private ImageMgr imageMgr;

	private class ViewHolder {
		public TextView title;
		public ImageView image;
	}

	public ScrapAdapter(ArrayList<ScrapTourData> dataList, Context ctx) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.row, dataList);
		this.datalist = dataList;
		this.mCtx = ctx;
		imageMgr = ImageMgr.getInstance((Activity) mCtx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		final ViewHolder holder;

		ScrapTourData tourData = datalist.get(position);
		if (convertView == null) {
			view = ((Activity) mCtx).getLayoutInflater().inflate(R.layout.row,
					null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.image = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();

		switch(Integer.parseInt(datalist.get(position).getContentTypeId())){
		case 12:
			holder.title.setText(tourData.getTitle() + "   (관광)");
			break;
		case 14:
			holder.title.setText(tourData.getTitle() + "   (문화)");
			break;
		case 25:
			holder.title.setText(tourData.getTitle() + "   (코스)");
			break;
		case 28:
			holder.title.setText(tourData.getTitle() + "   (레포츠)");
			break;
		case 32:
			holder.title.setText(tourData.getTitle() + "   (숙박)");
			break;
		case 39:
			holder.title.setText(tourData.getTitle() + "   (음식)");
			break;
		
		}
		

		if (tourData.getImageUrl() == null)
			Log.d(GPSInfoMain.DEBUG, "null" + "\n");
		// 이미지가 없는 경우 기본 이미지들 다운로드 하여 표시되도록 한다.
		if (tourData.getImageUrl() == null) {
			// holder.image.setVisibility(View.INVISIBLE);
			Log.d(GPSInfoMain.DEBUG, tourData.getImageUrl() + "\n");
			imageMgr.displayImage("http://i.imgur.com/5x7Hv9G.png",
					holder.image);
		}
		// 이미지가 있는 경우 동적으로 다운로드 하여 표시되도록 한다.
		else {
			// holder.image.setVisibility(View.VISIBLE);
			Log.d(GPSInfoMain.DEBUG, tourData.getImageUrl() + "\n");
			imageMgr.displayImage(tourData.getImageUrl(), holder.image);
		}
		return view;
	}

}
