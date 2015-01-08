package com.example.travelmaker.areainfo.network;

import java.util.*;

import android.sax.*;
import android.util.*;

import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.*;

public class TourListSAXParser2 extends BaseFeedParser2 {

	private static final String CONTENTID = "contentid";
	private static final String TITLE = "title";
	private static final String FIRST_IMAGE = "firstimage";
	private static final String RESPONSE = "response";
	private static final String BODY = "body";
	private static final String ITEMS = "items";
	private static final String ITEM = "item";
	private static final String TOTALCOUNT = "totalCount";
	//private static final String

	protected TourListSAXParser2(String feedUrl) {
		super(feedUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> parse2() throws Exception {
		Log.d(GPSInfoMain.DEBUG, "&8");
		final TourData2 currentData = new TourData2();
		final List<TourData2> dataList = new ArrayList<TourData2>();
		final Map<String, Object> result = new HashMap<String, Object>();

		RootElement root = new RootElement(RESPONSE);
		Element body = root.getChild(BODY);
		Element items = body.getChild(ITEMS);
		Element item = items.getChild(ITEM);
		Log.d(GPSInfoMain.DEBUG, "&9");
		item.setEndElementListener(new EndElementListener() {

			@Override
			public void end() {
				// TODO Auto-generated method stub
				Log.d(GPSInfoMain.DEBUG, "&10");
				dataList.add(currentData.copy());
				currentData.setImageUrl(null);
			}
		});

		item.getChild(CONTENTID).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setContentId(body);
						Log.d(GPSInfoMain.DEBUG, "&11");
					}
				});
		item.getChild(FIRST_IMAGE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setImageUrl(body);
						
						Log.d(GPSInfoMain.DEBUG, "&12");
					}
				});
		item.getChild(TITLE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setTitle(body);
						Log.d(GPSInfoMain.DEBUG, "&13");
					}
				});

		body.getChild(TOTALCOUNT).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.put("count", Integer.valueOf(body));
						Log.d(GPSInfoMain.DEBUG, "&14");
					}
				});

		try {
			Log.d(GPSInfoMain.DEBUG, "&15");
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			Log.d(GPSInfoMain.DEBUG, "e : " + e);
			throw new RuntimeException(e);
		}
		Log.d(GPSInfoMain.DEBUG, "&16");
		// 관광 데이터 리스트 반환
		result.put("list", dataList);
		Log.d(GPSInfoMain.DEBUG, "&17");
		return result;

	}
	

}
