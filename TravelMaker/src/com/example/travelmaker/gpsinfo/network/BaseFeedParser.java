package com.example.travelmaker.gpsinfo.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * FeedParsr 구현 클래스
 */
public abstract class BaseFeedParser implements FeedParser{

    private final URL feedUrl; // 데이터를 다운로드 받을 URL

    protected BaseFeedParser(String feedUrl) {
	try {
	    this.feedUrl = new URL(feedUrl);

	} catch (MalformedURLException e) {

	    throw new RuntimeException(e);
	}
    }

    /**
     * 
     * @return URL을 통한 InputStream
     * @throws Exception
     */
    protected InputStream getInputStream() throws Exception {
	try {
	    return feedUrl.openConnection().getInputStream();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
}