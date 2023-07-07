package com.place.admin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table( name = "PROPERTY_REQUEST" )

public class PropertyRequest {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
	private Long propertyId;
	private String toEmail;
	private String propertyReference;
	private String fromEmail;
	private String fromName;
	private String fromPhoneNumber;
	@Lob
	private String fromMessage;
	
	public PropertyRequest() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getPropertyReference() {
		return propertyReference;
	}
	public void setPropertyReference(String propertyReference) {
		this.propertyReference = propertyReference;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getFromPhoneNumber() {
		return fromPhoneNumber;
	}
	public void setFromPhoneNumber(String fromPhoneNumber) {
		this.fromPhoneNumber = fromPhoneNumber;
	}
	public String getFromMessage() {
		return fromMessage;
	}
	public void setFromMessage(String fromMessage) {
		this.fromMessage = fromMessage;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Request [id=");
		builder.append(id);
		builder.append(", propertyId=");
		builder.append(propertyId);
		builder.append(", toEmail=");
		builder.append(toEmail);
		builder.append(", propertyReference=");
		builder.append(propertyReference);
		builder.append(", fromEmail=");
		builder.append(fromEmail);
		builder.append(", fromName=");
		builder.append(fromName);
		builder.append(", fromPhoneNumber=");
		builder.append(fromPhoneNumber);
		builder.append(", fromMessage=");
		builder.append(fromMessage);
		builder.append("]");
		return builder.toString();
	}
	
}
