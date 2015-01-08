package com.example.travelmaker.areainfo.network;

import android.util.*;

import com.example.travelmaker.main.*;

public class FeedParserFactory2 {

	static String myKey = "MJkp3LoVgTkustkN/LPkOFCJ7b1lOaZiQTlN4PeoLS2lRwvBzKKqXsLy0uP5ltAvk9aiXSAx5nQE7FqshJaReg==";
	static String ServiceKey;
    static String feedUrl ; 
    //&numOfRows=10&listYN=Y&arrange=A&MobileOS=AND&MobileApp=KTSample
    //dZSY4J8atDXUPgljqgKgff%2BBpa%2BvdLvFivU%2BBl8YTIbtAk8JVUVVyLyO%2BGMY%2FEBaQa2EiGnTnw6Ol5zphVRstA%3D%3D 올레투어맵 인증키
    //MJkp3LoVgTkustkN/LPkOFCJ7b1lOaZiQTlN4PeoLS2lRwvBzKKqXsLy0uP5ltAvk9aiXSAx5nQE7FqshJaReg== 관광정보 인증키
    /**
     * 지정된 URL로부터 데이터를 읽어들일 파서를 생성하여 반환한다.
     * 
     * @param params
     *            - URL을 만드는데 사용될 파라미터
     * @return 생성된 파서
     */
    public static FeedParser2 getParser(String... params) {
    	Log.d(GPSInfoMain.DEBUG,
			    "&6");
    	return new TourListSAXParser2(createUrl(params));
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
    	feedUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="
    	+ myKey + "&contentTypeId=" + params[0] + "&areaCode=" + params[1]
    			+ "&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=AND&MobileApp=TravelMaker&arrange=B&numOfRows=10"
    			+ "&pageNo=" + params[2];
    	Log.d(GPSInfoMain.DEBUG,
			    feedUrl);
    	
    	
    	return feedUrl;
    }
    
   
}
