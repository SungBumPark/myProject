package com.example.travelmaker.gpsinfo.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * FeedParsr ���� Ŭ����
 */
public abstract class BaseFeedParser implements FeedParser{

    private final URL feedUrl; // �����͸� �ٿ�ε� ���� URL

    protected BaseFeedParser(String feedUrl) {
	try {
	    this.feedUrl = new URL(feedUrl);

	} catch (MalformedURLException e) {

	    throw new RuntimeException(e);
	}
    }

    /**
     * 
     * @return URL�� ���� InputStream
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