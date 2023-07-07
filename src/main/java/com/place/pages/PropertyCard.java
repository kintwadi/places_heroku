package com.place.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.place.Util;
import com.place.admin.model.Property;
import com.place.admin.service.StorageManager;

public class PropertyCard {
	
	private String page; // link 
	private String image;
	private String areaText;
	private String reason; // buy, rent or change ...
	private String area;
	private String name;
	private String buttonShow;
	
	private List<PropertyCard>propertyCards;
	
	
	public List<PropertyCard> limitedProperties(HttpServletRequest request,StorageManager storageManager){
		final int  limit = 5;
		String lang = Util.getLanguage(request);
		System.out.println("loang: "+lang);
		Properties resource = Util.loadResourceFile(lang);
		List<Property> properties = storageManager.getLimitedProperties(limit);
		
		propertyCards = new ArrayList<>();
		
		
		for(Property property: properties) {
			
			PropertyCard card = new PropertyCard();
			card.setPage("find_property?id="+property.getId()+"&lang="+lang);
			card.setImage(property.getImagePath());
			card.setReason(property.getReason());
			card.setArea(property.getArea());
			card.setReason(property.getReason().toUpperCase());
			card.setName(property.getName());
			card.setButtonShow(resource.getProperty("message.button.show"));
			
			card.setAreaText(resource.getProperty("message.area.text"));
			
			propertyCards.add(card);
		}
		return propertyCards;
		
	}
	
	public List<PropertyCard> pagination(HttpServletRequest request,StorageManager storageManager){
		
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int limit = Integer.parseInt(request.getParameter("numberPerPage"));
		String lang = Util.getLanguage(request);
		Properties resource = Util.loadResourceFile(lang);
		List<Property> properties = storageManager.pagination(pageNumber,limit);
		
		propertyCards = new ArrayList<>();
		
		
		for(Property property: properties) {
			
			PropertyCard card = new PropertyCard();
			card.setPage("find_property?id="+property.getId()+"&lang="+lang);
			card.setImage(property.getImagePath());
			card.setReason(property.getReason());
			card.setArea(property.getArea());
			card.setReason(property.getReason().toUpperCase());
			card.setName(property.getName());
			card.setButtonShow(resource.getProperty("message.button.show"));
			
			card.setAreaText(resource.getProperty("message.area.text"));
			
			propertyCards.add(card);
		}
		return propertyCards;
		
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getAreaText() {
		return areaText;
	}


	public void setAreaText(String areaText) {
		this.areaText = areaText;
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


	public void setArea(String label) {
		this.area = label;
	}

	public String getName() {
		return name;
	}


	public void setName(String overview) {
		this.name = overview;
	}
	

	public String getButtonShow() {
		return buttonShow;
	}


	public void setButtonShow(String buttonShow) {
		this.buttonShow = buttonShow;
	}
	public List<PropertyCard> getPropertyCards() {
		return propertyCards;
	}


	public void setPropertyCards(List<PropertyCard> propertyCards) {
		this.propertyCards = propertyCards;
	}
	
	
	
	

}
