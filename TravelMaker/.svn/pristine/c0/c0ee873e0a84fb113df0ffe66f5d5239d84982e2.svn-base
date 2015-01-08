package com.example.travelmaker.info.data;

public class TourData2 {

	String contentId; // 콘텐트 id
	String title; // 제목
	String imageUrl = null; // 이미지 URL

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public TourData2 copy() {
		TourData2 copy = new TourData2();
		copy.contentId = contentId;
		copy.title = title;
		copy.imageUrl = imageUrl;
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
		TourData2 other = (TourData2) obj;

		if (contentId == null) {
			if (other.contentId != null)
				return false;
		} else if (!contentId.equals(other.contentId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contentId == null) ? 0 : contentId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((imageUrl == null) ? 0 : imageUrl.hashCode());

		return result;
	}

	@Override
	public String toString() {

		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("contentId : ");
		sb.append(contentId);
		sb.append("\n");
		sb.append("title : ");
		sb.append(title);
		sb.append("\n");
		sb.append("imageUrl : ");
		sb.append(imageUrl);
		sb.append("\n");
		return sb.toString();
	}

}
