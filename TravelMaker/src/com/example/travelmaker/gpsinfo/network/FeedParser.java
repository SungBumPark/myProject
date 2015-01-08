package com.example.travelmaker.gpsinfo.network;

import java.util.Map;

public interface FeedParser {
    /**
     * 파싱을 실행한 후, 데이터를 반환한다.
     * 
     * @return - 파싱된 데이터
     * @throws Exception
     */
    Map<String, Object> parse() throws Exception;
    
}
