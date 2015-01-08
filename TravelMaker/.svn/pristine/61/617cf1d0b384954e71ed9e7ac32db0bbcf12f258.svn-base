package com.example.travelmaker.tourinfo.network;

import com.example.travelmaker.main.*;

import android.util.*;


public class ImgFeedParserFactory{

	static String myKey = "MJkp3LoVgTkustkN/LPkOFCJ7b1lOaZiQTlN4PeoLS2lRwvBzKKqXsLy0uP5ltAvk9aiXSAx5nQE7FqshJaReg==";
	static String ServiceKey;
    static String feedUrl ; 
    
    
    /**
     * 지정된 URL로부터 데이터를 읽어들일 파서를 생성하여 반환한다.
     * 
     * @param params
     *            - URL을 만드는데 사용될 파라미터
     * @return 생성된 파서
     */
    public static FeedParser4 getParser(String... params) {
    	Log.d(GPSInfoMain.DEBUG, "#7");
    	return new ImgTourDataSAXParser(createUrl(params));
    }
    
    /**
     * 파라미터를 이용하여 URL을 만들어 반환한다.
     * 
     * @param params
     *            URL을 만드는데 사용될 파라미터
     * @return 생성된 URL String
     */
    
 
    @SuppressWarnings("deprecation")
	public static String createUrl(String... params) {
    	//ServiceKey = URLEncoder.encode(myKey);
    	//ServiceKey = "dZSY4J8atDXUPgljqgKgff%2BBpa%2BvdLvFivU%2BBl8YTIbtAk8JVUVVyLyO%2BGMY%2FEBaQa2EiGnTnw6Ol5zphVRstA%3D%3D";
    	
    	
    	feedUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey="
    	+ myKey  + "&contentTypeId=" + params[0] +"&contentId=" + params[1]
    			+ "&MobileOS=AND&MobileApp=TravelMaker&imageYN=Y";
    	Log.d(GPSInfoMain.DEBUG,
			    "#8   " + feedUrl);    	
    	return feedUrl;
    }
}
