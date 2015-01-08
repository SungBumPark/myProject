package com.example.travelmaker.info.data;

import java.io.*;

public class COMNTourData implements Serializable{

	String title; // 제목
	String imageUrl = null; // 이미지 URL
	String zipCode= null;
	String address1= null;
	String address2= null;
	String mapX= null;
	String mapY= null;
	String homepage= null;
	String tel= null;
	String overView= null;
	COMNTourData result= null;


	public COMNTourData getResult() {
		return result;
	}

	public void setResult(COMNTourData result) {
		this.result = result;
	}

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getMapX() {
		return mapX;
	}

	public void setMapX(String mapX) {
		this.mapX = mapX;
	}

	public String getMapY() {
		return mapY;
	}

	public void setMapY(String mapY) {
		this.mapY = mapY;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public COMNTourData copy() {
		COMNTourData copy = new COMNTourData();
		copy.title = title; // 제목
		copy.imageUrl = imageUrl; // 이미지 URL
		copy.zipCode = zipCode;
		copy.address1 = address1;
		copy.address2= address2;
		copy.mapX = mapX;
		copy.mapY = mapY;
		copy.homepage = homepage;
		copy.tel = tel;
		copy.overView = overView;
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
		COMNTourData other = (COMNTourData) obj;

		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (mapX == null) {
			if (other.mapX != null)
				return false;
		} else if (!mapX.equals(other.mapX))
			return false;
		if (mapY == null) {
			if (other.mapY != null)
				return false;
		} else if (!mapY.equals(other.mapY))
			return false;
		if (homepage == null) {
			if (other.homepage != null)
				return false;
		} else if (!homepage.equals(other.homepage))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (overView == null) {
			if (other.overView != null)
				return false;
		} else if (!overView.equals(other.overView))
			return false;


		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result 
				+ ((zipCode == null) ? 0 : zipCode.hashCode());
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result
				+ ((mapX == null) ? 0 : mapX.hashCode());
		result = prime * result
				+ ((mapY == null) ? 0 : mapY.hashCode());
		result = prime * result
				+ ((homepage == null) ? 0 : homepage.hashCode());
		result = prime * result
				+ ((tel == null) ? 0 : tel.hashCode());
		result = prime * result
				+ ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((overView == null) ? 0 : overView.hashCode());

		return result;
	}

	@Override
	public String toString() {

		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("title : ");
		sb.append(title);
		sb.append("\n");
		sb.append("zipCode : ");
		sb.append(zipCode);
		sb.append("\n");
		sb.append("address1 : ");
		sb.append(address1);
		sb.append("\n");
		sb.append("address2 : ");
		sb.append(address2);
		sb.append("\n");
		sb.append("mapX : ");
		sb.append(mapX);
		sb.append("\n");
		sb.append("mapY : ");
		sb.append(mapY);
		sb.append("\n");
		sb.append("homepage : ");
		sb.append(homepage);
		sb.append("\n");
		sb.append("tel : ");
		sb.append(tel);
		sb.append("\n");
		sb.append("imageUrl : ");
		sb.append(imageUrl);
		sb.append('\n');
		return sb.toString();
	}


}
