package com.example.travelmaker.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.travelmaker.tour.gpsinfomain.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * ��Ʈ��ũ�� ���� �̹��� �ٿ�ε� �� �������� ǥ�����ִ� Ŭ���� ���� :
 * https://github.com/nostra13/Android-Universal-Image-Loader
 */

public class ImageMgr {
	private Activity mCtx;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private volatile static ImageMgr imageMgr;

	public ImageMgr(Activity ctx) {
		mCtx = ctx;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mCtx).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public static ImageMgr getInstance(Activity ctx) {
		if (imageMgr == null) {
			synchronized (ImageMgr.class) {
				if (imageMgr == null)
					imageMgr = new ImageMgr(ctx);
			}
		}
		return imageMgr;
	}

	/**
	 * �̹����� �ٿ�ε� �޾� �������� �̹����信 ����Ѵ�.
	 * 
	 * @param url
	 *            - �̹����� �ٿ�ε� ���� URL
	 * @param image
	 *            - �̹����� ǥ�õ� �̹�����
	 */
	public void displayImage(String url, ImageView image) {
		imageLoader.displayImage(url, image, options);
	}
}
