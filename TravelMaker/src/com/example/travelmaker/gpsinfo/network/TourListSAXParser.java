package com.example.travelmaker.gpsinfo.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.*;

/**
 * 리스트뷰에 보여질 데이터 파싱 클래스
 */
public class TourListSAXParser extends BaseFeedParser {

	private static final String RESPONSE = "response";
	private static final String BODY = "body";
	private static final String ITEMS = "items";
	private static final String ITEM = "item";
	private static final String CONTENTID = "contentid";
	private static final String ADDRESS = "addr1";
	private static final String XCOORD = "mapx";
	private static final String YCOORD = "mapy";
	private static final String TOTALCOUNT = "totalCount";
	private static final String TITLE = "title";
	private static final String FIRST_IMAGE = "firstimage";

	protected TourListSAXParser(String feedUrl) {
		super(feedUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> parse() throws Exception {
		// TODO Auto-generated method stub
		final TourData currentData = new TourData();
		final List<TourData> dataList = new ArrayList<TourData>();
		final Map<String, Object> result = new HashMap<String, Object>();

		RootElement root = new RootElement(RESPONSE);
		Element body = root.getChild(BODY);
		Element items = body.getChild(ITEMS);
		Element item = items.getChild(ITEM);

		item.setEndElementListener(new EndElementListener() {

			@Override
			public void end() {
				// TODO Auto-generated method stub
				dataList.add(currentData.copy());
				currentData.setImageUrl(null);
			}

		});

		item.getChild(ADDRESS).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setAddress(body);
					}
				});
		item.getChild(CONTENTID).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						Log.d(GPSInfoMain.DEBUG, "e : " + body);
						currentData.setContentId(body);
					}
				});

		item.getChild(FIRST_IMAGE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setImageUrl(body);
					}
				});

		item.getChild(XCOORD).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setxCoord(body);
					}
				});

		item.getChild(YCOORD).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setyCoord(body);
					}
				});

		item.getChild(TITLE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						currentData.setTitle(body);
					}
				});

		body.getChild(TOTALCOUNT).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.put("count", Integer.valueOf(body));
					}
				});

		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			Log.d(GPSInfoMain.DEBUG, "e : " + e);
			throw new RuntimeException(e);
		}

		// 관광 데이터 리스트 반환
		result.put("list", dataList);
		return result;
	}
}
