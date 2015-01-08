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

		/* �̹����� ���� ��� �⺻ �̹����� �ٿ�ε� �Ͽ� ǥ�õǵ��� �Ѵ�.
		if (tourData.getImageUrl() == null)
			// holder.image.setVisibility(View.INVISIBLE);
			imageMgr.displayImage(
					"http://api.visitkorea.or.kr/TourAPI2_Guide/data/img/noimg.gif",
					holder.image);
		// �̹����� �ִ� ��� �������� �ٿ�ε� �Ͽ� ǥ�õǵ��� �Ѵ�.
		else {
			// holder.image.setVisibility(View.VISIBLE);
			imageMgr.displayImage(tourData.getImageUrl(), holder.image);
		}*/
		return view;
	}
}
