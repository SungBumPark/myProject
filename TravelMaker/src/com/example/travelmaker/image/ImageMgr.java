package com.example.travelmaker.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.travelmaker.tour.gpsinfomain.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 네트워크를 통한 이미지 다운로드 및 동적으로 표시해주는 클래스 참고 :
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
	 * 이미지를 다운로드 받아 동적으로 이미지뷰에 출력한다.
	 * 
	 * @param url
	 *            - 이미지를 다운로드 받을 URL
	 * @param image
	 *            - 이미지가 표시될 이미지뷰
	 */
	public void displayImage(String url, ImageView image) {
		imageLoader.displayImage(url, image, options);
	}
}
