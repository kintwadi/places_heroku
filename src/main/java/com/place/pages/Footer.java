package com.place.pages;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.place.Util;

public class Footer {
	
	private String copyright;
	private String propertiesText;
	private String contactText;
	private String contactPage;
	private String propertiesPage;
	
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getContactPage() {
		return contactPage;
	}
	public void setContactPage(String contactPage) {
		this.contactPage = contactPage;
	}
	public String getPropertiesPage() {
		return propertiesPage;
	}
	public void setPropertiesPage(String propertiesPage) {
		this.propertiesPage = propertiesPage;
	}
	
	
	
	public String getPropertiesText() {
		return propertiesText;
	}
	public void setPropertiesText(String propertiesText) {
		this.propertiesText = propertiesText;
	}
	public String getContactText() {
		return contactText;
	}
	public void setContactText(String contactText) {
		this.contactText = contactText;
	}
	public Footer createFooterContent(HttpServletRequest request) {
		String lang = Util.getLanguage(request);
		Properties resource = Util.loadResourceFile(lang);
		Footer footer = new Footer();
		footer.setCopyright(resource.getProperty("message.copyright"));
		footer.setPropertiesPage("all_properties?lang="+lang);
		footer.setContactPage("contact?lang="+lang);
		footer.setContactText(resource.getProperty("message.contact.text"));
		footer.setPropertiesText(resource.getProperty("message.properties.text"));
		return footer;
		
		
	}

}
