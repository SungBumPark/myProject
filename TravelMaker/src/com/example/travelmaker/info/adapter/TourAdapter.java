package com.example.travelmaker.info.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelmaker.tour.gpsinfomain.R;
import com.example.travelmaker.image.*;
import com.example.travelmaker.info.data.*;

/**
 * ����Ʈ�信 ����� �����
 */
public class TourAdapter extends ArrayAdapter<TourData> {

	private List<TourData> dataList;
	private Context mCtx;
	private ImageMgr imageMgr;

	private class ViewHolder {
		public TextView title;
		public ImageView image;
	}

	public TourAdapter(List<TourData> dataList, Context ctx) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.row, dataList);
		this.dataList = dataList;
		this.mCtx = ctx;
		imageMgr = ImageMgr.getInstance((Activity) mCtx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		final ViewHolder holder;
		TourData tourData = dataList.get(position);

		if (convertView == null) {
			view = ((Activity) mCtx).getLayoutInflater().inflate(R.layout.row,
					null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.image = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();

		holder.title.setText(tourData.getTitle());

		// �̹����� ���� ��� �⺻ �̹����� �ٿ�ε� �Ͽ� ǥ�õǵ��� �Ѵ�.
		if (tourData.getImageUrl() == null)
			// holder.image.setVisibility(View.INVISIBLE);
			imageMgr.displayImage(
					"http://i.imgur.com/5x7Hv9G.png",
					holder.image);
		// �̹����� �ִ� ��� �������� �ٿ�ε� �Ͽ� ǥ�õǵ��� �Ѵ�.
		else {
			// holder.image.setVisibility(View.VISIBLE);
			imageMgr.displayImage(tourData.getImageUrl(), holder.image);
		}
		return view;
	}
}