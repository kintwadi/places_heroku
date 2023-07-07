package com.place.pages;

import java.util.Properties;

public class Gui {
	
	private String status;
	private String reason;
	private String area;
	private String bedroom;
	private String bathroom;
	private String dealer;
	private String description;
	private String garage;
	private String reference;
	private String location;
	private String viewMore;
	private String ads;
	private String video;
	
	
	public Gui singlePropertyGuiText (Properties resource) {
		
		Gui guiText = new Gui();
		guiText.setStatus(resource.getProperty("message.status.text"));
		guiText.setReason(resource.getProperty("message.reason.text"));
		guiText.setArea(resource.getProperty("message.area.text"));
		guiText.setGarage(resource.getProperty("message.garage.text"));
		guiText.setBathroom(resource.getProperty("message.bathroom.text"));
		guiText.setBedroom(resource.getProperty("message.bedroom.text"));
		guiText.setDescription(resource.getProperty("message.description.text"));
		guiText.setDealer(resource.getProperty("message.dealer.text"));
		guiText.setReference(resource.getProperty("message.reference.text"));
		guiText.setLocation(resource.getProperty("message.location.text"));
		guiText.setViewMore(resource.getProperty("message.button.show"));
		guiText.setAds(resource.getProperty("message.ads.text"));
		guiText.setVideo(resource.getProperty("message.video.text"));
	
		
		return guiText;
		
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBedroom() {
		return bedroom;
	}
	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}
	public String getBathroom() {
		return bathroom;
	}
	public void setBathroom(String bathroom) {
		this.bathroom = bathroom;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGarage() {
		return garage;
	}
	public void setGarage(String garage) {
		this.garage = garage;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getViewMore() {
		return viewMore;
	}
	public void setViewMore(String viewMore) {
		this.viewMore = viewMore;
	}
	public String getAds() {
		return ads;
	}
	public void setAds(String ads) {
		this.ads = ads;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	
	
	
	
	
	
	
	

}
