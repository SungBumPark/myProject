package com.example.travelmaker.tourinfo.network;

import java.io.*;
import java.net.*;

public abstract class BaseFeedParser3 implements FeedParser3 {

	private final URL feedUrl; // �����͸� �ٿ�ε� ���� URL

	protected BaseFeedParser3(String feedUrl) {
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
