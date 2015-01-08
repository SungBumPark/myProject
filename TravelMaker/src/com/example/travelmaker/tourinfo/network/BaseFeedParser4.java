package com.example.travelmaker.tourinfo.network;

import java.io.*;
import java.net.*;
import java.util.*;

public abstract class BaseFeedParser4 implements FeedParser4{

	private final URL feedUrl; // 데이터를 다운로드 받을 URL

	protected BaseFeedParser4(String feedUrl) {
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
