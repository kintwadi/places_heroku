package com.place.pages;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;

import com.place.Constants;
import com.place.Util;
import com.place.admin.model.Address;
import com.place.admin.model.Detail;
import com.place.admin.model.Image;
import com.place.admin.model.Property;
import com.place.admin.model.TUser;
import com.place.admin.service.StorageManager;

public class SinglePropertyCard extends PropertyCard{

	private Long id;
	private String name;
	private String type;// house,apartment, plot of land, garage,
						// parking space, commercial
	private String status; // available, sold out,rented etc...
	private String condition; // new, old, renewed,in construction
	private String reason;// rent, buy,sell,exchange, give away
	private Double price;
	private String rooms;
	private String area; // 150m^2
	private String cover;
	private String overview;
	private String description;
	private String reference;
	private List<Property>relatedProperties;
	private String fileExtension;
	private String video;
	private String page; // link 
	
	
	private Address address;
	private List<Detail> details;
	private List <Image> images;
	private TUser user; // property owner
	private Gui gui; //  labels
	
	public SinglePropertyCard createSingleProperty(HttpServletRequest request,StorageManager storageManager)
	{
		long id = Util.getId(request);
		String lang = Util.getLanguage(request);
		Properties resource = Util.loadResourceFile(lang);
		
		Property property = storageManager.getPropertyById(id);
		
		SinglePropertyCard singlePropertyCard = new SinglePropertyCard();
		singlePropertyCard.setPropertyId(property.getId());
		singlePropertyCard.setName(property.getName());
		singlePropertyCard.setType(property.getType());
		singlePropertyCard.setArea(property.getArea());
		singlePropertyCard.setReason(property.getReason());
		singlePropertyCard.setCondition(property.getCondition());
		singlePropertyCard.setPrice(property.getPrice());
		singlePropertyCard.setRooms(property.getRooms());
		singlePropertyCard.setCover(property.getImagePath());
		singlePropertyCard.setOverview(property.getOverview());
		singlePropertyCard.setDescription(property.getDescription());
		singlePropertyCard.setImages(property.getImages());
		singlePropertyCard.setAddress(property.getAddress());
		singlePropertyCard.setUser(property.getTuser());
		singlePropertyCard.setReference(property.getReference());
		singlePropertyCard.setDetails(storageManager.findDetailByPropertyId(property.getId(),true));
		singlePropertyCard.setRelatedProperties(storageManager.getRelatedProperties(id));
		singlePropertyCard.setVideo(property.getVideoPath());
		singlePropertyCard.setFileExtension(property.getFileExtension());
		singlePropertyCard.setGui(new Gui().singlePropertyGuiText(resource));
		singlePropertyCard.setPage("find_property?id="+property.getId()+"&lang="+lang);
		
		return singlePropertyCard;
	}
	
	public long getId() {
		return id;
	}

	public void setPropertyId(long propertyId) {
		this.id = propertyId;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public String getRooms() {
		return rooms;
	}


	public void setRooms(String rooms) {
		this.rooms = rooms;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getCover() {
		return cover;
	}


	public void setCover(String cover) {
		this.cover = cover;
	}


	public String getOverview() {
		return overview;
	}


	public void setOverview(String overview) {
		this.overview = overview;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public List<Detail> getDetails() {
		return details;
	}


	public void setDetails(List<Detail> detail) {
		this.details = detail;
	}


	public List<Image> getImages() {
		return images;
	}


	public void setImages(List<Image> images) {
		this.images = images;
	}


	public TUser getUser() {
		return user;
	}


	public void setUser(TUser user) {
		this.user = user;
	}


	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}



	public List<Property> getRelatedProperties() {
		return relatedProperties;
	}



	public void setRelatedProperties(List<Property> relatedProperties) {
		this.relatedProperties = relatedProperties;
	}
	
	
    public String getVideo() {
		return video;
	}



	public void setVideo(String video) {
		this.video = video;
	}

	public String getAdImagePath()
    {

        final String link = Constants.S3_BASE_URI+"/uploads/"+Constants.DEFAULT_ADD_IMAGE;

        return link;
        
    }

//	public String getAdImagePath()
//    {
//
//
//    	// fetch random adds from db
//        final String link = "defaultad";
//
//        StorageManager storageService = new StorageManager();
//        StringBuilder filename = new StringBuilder( link );
//        Resource resource = storageService.load( filename.toString() );
//
//        // pick up default image
//        if( resource == null )
//        {
//            resource = storageService.load( Constants.DEFAULT_ADD_IMAGE );
//            filename = new StringBuilder( Constants.UPLOAD_FOLDER );
//            filename.append( resource.getFilename() );
//        }
//        else// pick up stored image
//        {
//            filename = new StringBuilder( Constants.UPLOAD_FOLDER );
//            filename.append( link );
//        }
//        
//        return filename.toString();
//        
//    }
	
	
    public String getPropertyVideoPath()
    {


    	// fetch property video from remote storage
        final String link = Constants.S3_BASE_URI+"/uploads/"+Constants.DEFAULT_PROPERTY_VIDEO ;

        return link;
        
    }
    
//    public String getPropertyVideoPath()
//    {
//
//
//    	// fetch random adds from db
//        final String link = "defaultvideo";
//
//        StorageManager storageService = new StorageManager();
//        StringBuilder filename = new StringBuilder( link );
//        Resource resource = storageService.load( filename.toString() );
//
//        // pick up default video
//        if( resource == null )
//        {
//            resource = storageService.load( Constants.DEFAULT_PROPERTY_VIDEO );
//            filename = new StringBuilder( Constants.UPLOAD_FOLDER );
//            filename.append( resource.getFilename() );
//        }
//        else// pick up stored video
//        {
//            filename = new StringBuilder( Constants.UPLOAD_FOLDER );
//            filename.append( link );
//        }
//        
//        setFileExtension(filename.toString());
//    
//        return filename.toString();
//        
//    }

    
    public void setFileExtension(String video)
    {

    	String extension = "mp4";
    	if(video != null) 
    	{
    		
    		int i = video.lastIndexOf('.');
        	if (i > 0) {
        	    extension = video.substring(i+1);
        	}
    	}
    	
    	this.fileExtension = extension;
    	
        
    }

	public String getFileExtension() {
		return fileExtension;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SinglePropertyCard [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", status=");
		builder.append(status);
		builder.append(", condition=");
		builder.append(condition);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", price=");
		builder.append(price);
		builder.append(", rooms=");
		builder.append(rooms);
		builder.append(", area=");
		builder.append(area);
		builder.append(", cover=");
		builder.append(cover);
		builder.append(", overview=");
		builder.append(overview);
		builder.append(", description=");
		builder.append(description);
		builder.append(", reference=");
		builder.append(reference);
		builder.append(", relatedProperties=");
		builder.append(relatedProperties);
		builder.append(", fileExtension=");
		builder.append(fileExtension);
		builder.append(", video=");
		builder.append(video);
		builder.append(", page=");
		builder.append(page);
		builder.append(", images=");
		builder.append(images);
		builder.append(", user=");
		builder.append(user);
		builder.append(", gui=");
		builder.append(gui);
		builder.append("]");
		return builder.toString();
	}
    
    
	
	
	
	

}
