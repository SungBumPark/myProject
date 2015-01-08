package myhanbok.adapter;

import java.util.ArrayList;

import mija.myhanbok.R;
import myhanbok.model.TabMenu;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<TabMenu> {

	Context context;
	int layoutResourceId;
	ArrayList<TabMenu> data = new ArrayList<TabMenu>();

	public MenuAdapter(Context context, int layoutResourceId,
			ArrayList<TabMenu> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RegardingTypeHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RegardingTypeHolder();

			holder.imageView = (ImageView) row.findViewById(R.id.image);
			//holder.textName = (TextView) row.findViewById(R.id.link);
			row.setTag(holder);
		} else {
			holder = (RegardingTypeHolder) row.getTag();
		}

		//Drawable img = (Drawable) getResources().
		TabMenu tabMenu = data.get(position);
		holder.imageView.setImageResource(tabMenu.getImage());
		//holder.textName.setText(tabMenu.getName());
		return row;

	}

	static class RegardingTypeHolder {
		//TextView textName;
		ImageView imageView;
	}
}
