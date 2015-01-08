package com.example.travelmaker.info.adapter;

import java.util.*;

import com.example.travelmaker.image.*;
import com.example.travelmaker.info.adapter.TourAdapter.*;
import com.example.travelmaker.info.data.*;
import com.example.travelmaker.tour.gpsinfomain.*;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;



public class ImgTourAdapter extends ArrayAdapter<String>{

	private List<String> dataList;
	private Context mCtx;
	private ImageMgr imageMgr;
	
	private class ViewHolder {
		public ImageView image;
	}
	
	public ImgTourAdapter(ArrayList<String> dataList, Context ctx) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.img_row, dataList);
		this.dataList = dataList;
		this.mCtx = ctx;
		imageMgr = ImageMgr.getInstance((Activity) mCtx);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		final ViewHolder holder;
		//TourData tourData = dataList.get(position);

		if (convertView == null) {
			view = ((Activity) mCtx).getLayoutInflater().inflate(R.layout.img_row,
					null);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.imageView);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();

		/* 이미지가 없는 경우 기본 이미지들 다운로드 하여 표시되도록 한다.
		if (tourData.getImageUrl() == null)
			// holder.image.setVisibility(View.INVISIBLE);
			imageMgr.displayImage(
					"http://api.visitkorea.or.kr/TourAPI2_Guide/data/img/noimg.gif",
					holder.image);
		// 이미지가 있는 경우 동적으로 다운로드 하여 표시되도록 한다.
		else {
			// holder.image.setVisibility(View.VISIBLE);
			imageMgr.displayImage(tourData.getImageUrl(), holder.image);
		}*/
		return view;
	}
}
