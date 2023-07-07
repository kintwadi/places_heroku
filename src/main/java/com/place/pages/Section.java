package com.place.pages;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.place.Util;

public class Section {
	
	private String title;
	private String subTitle;
	private String rightSideTitle;
	private String rightSideSubtitle;
	private String rightSideLink;
	private String lang;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public String getRightSideTitle() {
		return rightSideTitle;
	}
	public void setRightSideTitle(String rightSideTitle) {
		this.rightSideTitle = rightSideTitle;
	}
	public String getRightSideSubtitle() {
		return rightSideSubtitle;
	}
	public void setRightSideSubtitle(String rightSideSubtitle) {
		this.rightSideSubtitle = rightSideSubtitle;
	}
	
	
	public String getRightSideLink() {
		return rightSideLink;
	}
	public void setRightSideLink(String page) {
		this.rightSideLink = page;
	}
	
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Section createSingleSection(HttpServletRequest request) {
		
		Section section = new Section();
		String lang = Util.getLanguage(request);
		Properties resource = Util.loadResourceFile(lang);
		section.setTitle(resource.getProperty("message.section.title"));
		section.setSubTitle(resource.getProperty("message.section.subtitle"));
		
		section.setRightSideTitle(resource.getProperty("message.right.side.title"));
		section.setRightSideSubtitle(resource.getProperty("message.right.side.subtitle"));
		section.setRightSideLink("all_properties");
		section.setLang(lang);
		return section;
	}
	
	
	
	

}
