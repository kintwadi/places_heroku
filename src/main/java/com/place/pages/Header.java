package com.place.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.io.Resource;
import com.place.Constants;
import com.place.Util;
import com.place.admin.service.StorageManager;

public class Header {

	private String page;
	private String logo;
	private String email;
	private String phone;
	private String currentLanguage;
	private List<String> languages;
	private Search search;

	public Header() {

	}

	/**
	 * 
	 * @param page link to the page after language change
	 */
	public Header(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLanguage() {
		return currentLanguage;
	}

	public void setLanguage(String language) {
		this.currentLanguage = language;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	private List<String> getLanguages(String currentLanguage) {
		List<String> languages = new ArrayList<>();
		// load configured currencies
		List<String> configuredLanguages = new ArrayList<>();
		configuredLanguages.add(Constants.DEFAULT_LANGUAGE);
		//configuredLanguages.add("PT");
		//configuredLanguages.add("DE");

		configuredLanguages.remove(currentLanguage);
		languages.add(0, currentLanguage);

		for (int i = 0; i < configuredLanguages.size(); i++) {
			languages.add(configuredLanguages.get(i));

		}

		return languages;
	}

	public Header loadHeader(HttpServletRequest request, HttpServletResponse response) {

		this.currentLanguage = Util.getLanguage(request);
		String country = "angola";
		String propertyType = "type";
		

		Header header = new Header();
		this.search = new Search();
		List<String> locations = new ArrayList<>();
		List<String> propertyTypes = new ArrayList<>();
		List<String> options = new ArrayList<>();
		String lang = currentLanguage.toLowerCase();
		Properties resource = Util.loadResourceFile(lang);
		country = (String)resource.get("message.configured.country");
		JSONArray countries = Util.loadJSON("countries", country);
		
		header.setLogo(getImagePath());
		header.setEmail(Constants.DEFAULT_EMAIL);
		header.setPhone(Constants.DEFAULT_PHONE);
		header.setLanguages(getLanguages(currentLanguage));
	
		for(int i = 0; i < countries.size(); i++) 
		{
			JSONObject angola =(JSONObject) countries.get(i);
			locations.add((String)angola.get("name"));
		}

		country = (String)resource.get("message.configured.country");
		JSONArray types = Util.loadJSON("property_types", propertyType);
		
		for(int i = 0; i < types.size(); i++) 
		{
			JSONObject angola =(JSONObject) types.get(i);
			propertyTypes.add((String)angola.get("name"));
		}
		
		search.setLocations(locations);
		search.setPropertyTypes(propertyTypes);
		
		options.add(resource.getProperty("message.swimming"));
		options.add(resource.getProperty("message.balcony"));
		options.add(resource.getProperty("message.playground"));
		options.add(resource.getProperty("message.basement"));
		options.add(resource.getProperty("message.garden"));
		options.add(resource.getProperty("message.bathrooms"));
		options.add(resource.getProperty("message.bedrooms"));
		options.add(resource.getProperty("message.furnished"));

		search.setOptions(options);

		header.setSearch(search);
		
		

		return header;
	}
	
	public String getImagePath() {

		final String link =Constants.S3_BASE_URI+"/uploads/"+Constants.DEFAULT_LOGO;

		return link;
	}

//	public String getImagePath() {
//
//		final String link = Constants.DEFAULT_LOGO;
//
//		StorageManager storageService = new StorageManager();
//		StringBuilder filename = new StringBuilder(link);
//		Resource resource = storageService.load(filename.toString());
//
//		// pick up default logo
//		if (resource == null) {
//			resource = storageService.load(Constants.DEFAULT_LOGO);
//			filename = new StringBuilder(Constants.UPLOAD_FOLDER);
//			filename.append(resource.getFilename());
//		} else// pick up stored logo
//		{
//			filename = new StringBuilder(Constants.UPLOAD_FOLDER);
//			filename.append(link);
//		}
//		return filename.toString();
//
//	}

}
