package org.upgrad.capstone.domain;

public class ZipCode {
	private double lat;
	private double lon;
	private String city;
	private String state_name;
	private String postId;

	public ZipCode(double lat, double lon, String city, String state_name, String postId) {
		this.lat = lat;
		this.lon = lon;
		this.city = city;
		this.state_name = state_name;
		this.postId = postId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
}