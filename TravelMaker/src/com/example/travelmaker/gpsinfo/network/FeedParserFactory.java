package com.example.travelmaker.gpsinfo.network;

import java.net.*;

public class FeedParserFactory {
    // ������ �ٿ�ε� ���� URL
	static String myKey = "MJkp3LoVgTkustkN/LPkOFCJ7b1lOaZiQTlN4PeoLS2lRwvBzKKqXsLy0uP5ltAvk9aiXSAx5nQE7FqshJaReg==";
	static String ServiceKey;
    static String feedUrl; 
    //&numOfRows=10&listYN=Y&arrange=A&MobileOS=AND&MobileApp=KTSample
    //dZSY4J8atDXUPgljqgKgff%2BBpa%2BvdLvFivU%2BBl8YTIbtAk8JVUVVyLyO%2BGMY%2FEBaQa2EiGnTnw6Ol5zphVRstA%3D%3D �÷������ ����Ű
    //MJkp3LoVgTkustkN/LPkOFCJ7b1lOaZiQTlN4PeoLS2lRwvBzKKqXsLy0uP5ltAvk9aiXSAx5nQE7FqshJaReg== �������� ����Ű
    /**
     * ������ URL�κ��� �����͸� �о���� �ļ��� �����Ͽ� ��ȯ�Ѵ�.
     * 
     * @param params
     *            - URL�� ����µ� ���� �Ķ����
     * @return ������ �ļ�
     */
    public static FeedParser getParser(String... params) {
	return new TourListSAXParser(createUrl(params));
    }

    /**
     * �Ķ���͸� �̿��Ͽ� URL�� ����� ��ȯ�Ѵ�.
     * 
     * @param params
     *            URL�� ����µ� ���� �Ķ����
     * @return ������ URL String
     */
    @SuppressWarnings("deprecation")
	public static String createUrl(String... params) {
    	//ServiceKey = URLEncoder.encode(myKey);
    	ServiceKey = "dZSY4J8atDXUPgljqgKgff%2BBpa%2BvdLvFivU%2BBl8YTIbtAk8JVUVVyLyO%2BGMY%2FEBaQa2EiGnTnw6Ol5zphVRstA%3D%3D";
    	String feedUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey=" 
    	+ ServiceKey +"&numOfRows=10&listYN=Y&MobileOS=AND&MobileApp=TravelMaker";
    	if(params[0] == "15")
    	{
    		return feedUrl + "&contentTypeId=" + params[0] + "&mapX=" + params[1]
    				+ "&mapY=" + params[2] + "&radius=" + params[3] + "&pageNo="
    				+ params[4] + "&arrange=C";
    	}
	return feedUrl + "&contentTypeId=" + params[0] + "&mapX=" + params[1]
		+ "&mapY=" + params[2] + "&radius=" + params[3] + "&pageNo="
		+ params[4] + "&arrange=B";
    }
}
