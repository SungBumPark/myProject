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
			holder.title.setText(tourData.getTitle() + "   (����)");
			break;
		case 14:
			holder.title.setText(tourData.getTitle() + "   (��ȭ)");
			break;
		case 25:
			holder.title.setText(tourData.getTitle() + "   (�ڽ�)");
			break;
		case 28:
			holder.title.setText(tourData.getTitle() + "   (������)");
			break;
		case 32:
			holder.title.setText(tourData.getTitle() + "   (����)");
			break;
		case 39:
			holder.title.setText(tourData.getTitle() + "   (����)");
			break;
		
		}
		

		if (tourData.getImageUrl() == null)
			Log.d(GPSInfoMain.DEBUG, "null" + "\n");
		// �̹����� ���� ��� �⺻ �̹����� �ٿ�ε� �Ͽ� ǥ�õǵ��� �Ѵ�.
		if (tourData.getImageUrl() == null) {
			// holder.image.setVisibility(View.INVISIBLE);
			Log.d(GPSInfoMain.DEBUG, tourData.getImageUrl() + "\n");
			imageMgr.displayImage("http://i.imgur.com/5x7Hv9G.png",
					holder.image);
		}
		// �̹����� �ִ� ��� �������� �ٿ�ε� �Ͽ� ǥ�õǵ��� �Ѵ�.
		else {
			// holder.image.setVisibility(View.VISIBLE);
			Log.d(GPSInfoMain.DEBUG, tourData.getImageUrl() + "\n");
			imageMgr.displayImage(tourData.getImageUrl(), holder.image);
		}
		return view;
	}

}
