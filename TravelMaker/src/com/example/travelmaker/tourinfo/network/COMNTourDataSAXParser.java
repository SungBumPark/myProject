package com.example.travelmaker.tourinfo.network;

import java.util.*;

import android.sax.*;
import android.util.*;

import com.example.travelmaker.info.data.*;
import com.example.travelmaker.main.*;


public class COMNTourDataSAXParser extends BaseFeedParser3 {

	private static final String TITLE = "title";
	private static final String FIRST_IMAGE = "firstimage";
	private static final String ZIPCODE = "zipcode";
	private static final String ADDRESS1 = "addr1";
	private static final String ADDRESS2 = "addr2";
	private static final String MAPX = "mapx";
	private static final String MAPY = "mapy";
	private static final String HOMEPAGE = "homepage";
	private static final String TEL = "tel";
	private static final String RESPONSE = "response";
	private static final String BODY = "body";
	private static final String ITEMS = "items";
	private static final String ITEM = "item";
	private static final String OVERVIEW = "overview";
	
	
	protected COMNTourDataSAXParser(String feedUrl) {
		
		super(feedUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public COMNTourData parse3() throws Exception {
		// TODO Auto-generated method stub
		Log.d(GPSInfoMain.DEBUG, 
				"#9");
		final COMNTourData result = new COMNTourData();
		//final List<COMNTourData> dataList = new ArrayList<COMNTourData>();
		//final Map<String, Object> result = new HashMap<String, Object>();
		
		RootElement root = new RootElement(RESPONSE);
		Element body = root.getChild(BODY);
		Element items = body.getChild(ITEMS);
		Element item = items.getChild(ITEM);
		
		item.setEndElementListener(new EndElementListener() {

			@Override
			public void end() {
				// TODO Auto-generated method stub
				Log.d(GPSInfoMain.DEBUG, 
						"#10");
			}
		});
		
		item.getChild(TITLE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setTitle(body);
						Log.d(GPSInfoMain.DEBUG, "#11  "  
								+ result.getTitle());
					}
				});
		item.getChild(FIRST_IMAGE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setImageUrl(body);
						Log.d(GPSInfoMain.DEBUG, "#12  "  
								+ result.getImageUrl());
					}
				});
		item.getChild(OVERVIEW).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setOverView(body);
						Log.d(GPSInfoMain.DEBUG, "#13  "  
								+ result.getOverView());
					}
				});
		item.getChild(ZIPCODE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setZipCode(body);
						Log.d(GPSInfoMain.DEBUG, "#14  "  
								+ result.getZipCode());
					}
				});
		item.getChild(ADDRESS1).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setAddress1(body);
						Log.d(GPSInfoMain.DEBUG, "#15  " 
								+ result.getAddress1());
					}
				});
		item.getChild(ADDRESS2).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setAddress2(body);
						Log.d(GPSInfoMain.DEBUG, "#16  "  
								+ result.getAddress2());
					}
				});
		item.getChild(MAPX).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setMapX(body);
						Log.d(GPSInfoMain.DEBUG, "#17  "  
								+ result.getMapX());
					}
				});
		item.getChild(MAPY).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setMapY(body);
						Log.d(GPSInfoMain.DEBUG, "#18  "  
								+ result.getMapY());
					}
				});
		item.getChild(HOMEPAGE).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setHomepage(body);
						Log.d(GPSInfoMain.DEBUG, "#19  "  
								+ result.getHomepage());
					}
				});
		item.getChild(TEL).setEndTextElementListener(
				new EndTextElementListener() {

					@Override
					public void end(String body) {
						// TODO Auto-generated method stub
						result.setTel(body);
						Log.d(GPSInfoMain.DEBUG, "#20  "  
								+ result.getTel());
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
