package com.example.travelmaker.info.data;

import android.util.*;

import com.example.travelmaker.main.*;

/**
 * 여행 정보 데이터 저장 클래스
 */
public class TourData {
	String address; // 주소
	String title; // 이름
	String xCoord; // x 좌표
	String yCoord; // y 좌표
	String imageUrl; // 이미지 URL
	String contentId;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getxCoord() {
		return xCoord;
	}

	public void setxCoord(String xCoord) {
		this.xCoord = xCoord;
	}

	public String getyCoord() {
		return yCoord;
	}

	public void setyCoord(String yCoord) {
		this.yCoord = yCoord;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public TourData copy() {
		TourData copy = new TourData();
		copy.title = title;
		copy.address = address;
		copy.xCoord = xCoord;
		copy.yCoord = yCoord;
		copy.imageUrl = imageUrl;
		copy.contentId = contentId;
		Log.d(GPSInfoMain.DEBUG, "c : " + contentId);
		return copy;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TourData other = (TourData) obj;

		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;

		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;

		if (xCoord == null) {
			if (other.xCoord != null)
				return false;
		} else if (!xCoord.equals(other.xCoord))
			return false;

		if (yCoord == null) {
			if (other.yCoord != null)
				return false;
		} else if (!yCoord.equals(other.yCoord))
			return false;

		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (contentId == null) {
			if (other.contentId != null)
				return false;
		} else if (!contentId.equals(other.contentId))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((xCoord == null) ? 0 : xCoord.hashCode());
		result = prime * result + ((yCoord == null) ? 0 : yCoord.hashCode());
		result = prime * result
				+ ((contentId == null) ? 0 : contentId.hashCode());
		result = prime * result
				+ ((imageUrl == null) ? 0 : imageUrl.hashCode());

		return result;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("title : ");
		sb.append(title);
		sb.append('\n');
		sb.append("address : ");
		sb.append(address);
		sb.append('\n');
		sb.append("xCoord : ");
		sb.append(xCoord);
		sb.append('\n');
		sb.append("yCoord : ");
		sb.append(yCoord);
		sb.append('\n');
		sb.append("imageUrl : ");
		sb.append(imageUrl);
		sb.append('\n');
		sb.append("contentId : ");
		sb.append(contentId);
		sb.append('\n');

		return sb.toString();
	}

}
