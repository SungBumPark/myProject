package com.example.travelmaker.tourinfo.network;

import java.util.*;

import com.example.travelmaker.main.*;

import android.R.string;
import android.sax.*;
import android.util.*;

public class ImgTourDataSAXParser extends BaseFeedParser4 {

	private static final String RESPONSE = "response";
	private static final String BODY = "body";
	private static final String ITEMS = "items";
	private static final String ITEM = "item";
	private static final String ORIGINIMGURL = "originimgurl";
	private static int count = 0;

	protected ImgTourDataSAXParser(String feedUrl) {

		super(feedUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> parse4() throws Exception {
		// TODO Auto-generated method stub
		Log.d(GPSInfoMain.DEBUG, "#10");
		final ArrayList<String> result = new ArrayList();
		Log.d(GPSInfoMain.DEBUG, "#10+");
		RootElement root = new RootElement(RESPONSE);
		Element body = root.getChild(BODY);
		Element items = body.getChild(ITEMS);
		Element item = items.getChild(ITEM);

		item.setEndElementListener(new EndElementListener() {

			@Override
			public void end() {
				// TODO Auto-generated method stub
				Log.d(GPSInfoMain.DEBUG, "#10");
			}
		});

		item.getChild(ORIGINIMGURL).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						Log.d(GPSInfoMain.DEBUG, "#11  " + body);
						result.add(body);
						
					}
				});

		try {
			Log.d(GPSInfoMain.DEBUG, "#21");
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			Log.d(GPSInfoMain.DEBUG, "e : " + e);
			throw new RuntimeException(e);
		}
		return result;
	}

}
