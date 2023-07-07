
package com.place.admin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Type;
import org.springframework.core.io.Resource;

import com.place.Constants;
import com.place.admin.service.StorageManager;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table( name = "PROPERTY" )
public class Property
{
    // ---------------------------------------------------------------------
    // Properties
    // ---------------------------------------------------------------------
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long   id;
    private String         name;
    private String         type;// house,apartment, plot of land, garage,
                                // parking space, commercial
    private String         status; // available, sold out,rented etc...
    private String         condition; // new, old, renewed,in construction
    private String         reason;// rent, buy,sell,exchange, give away
    private Double         price;
    private String         rooms;
    private String         area; // 150m^2

    private String         cover;
    //@Lob
    @Column( columnDefinition="TEXT")
    private String         overview;
    //@Lob
    @Column(columnDefinition="TEXT")
    private String         description;
    @Column( unique = true )
    private String         reference;
    private String video;
    @Transient
    private String fileExtension;

    @OneToMany( mappedBy = "property",
                cascade = CascadeType.ALL,
                orphanRemoval = true )
    private List< Image >  images = new ArrayList<>();

    @OneToMany( mappedBy = "property",
                cascade = CascadeType.ALL,
                orphanRemoval = true )
    private List< Detail > details = new ArrayList<>();

    @ManyToOne( fetch = FetchType.LAZY )
    private TUser           tuser;

    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "address_id", referencedColumnName = "id" )
    private Address        address;

    @Transient
    private List< String > imagesToRemove = new ArrayList< String >();

    // ---------------------------------------------------------------------
    // Construction
    // ---------------------------------------------------------------------
    public Property()
    {

    }
    // ---------------------------------------------------------------------
    // Private Helper Methods
    // ---------------------------------------------------------------------

    // ---------------------------------------------------------------------
    // Public Methods
    // ---------------------------------------------------------------------
    @Transient
    public String getImagePath()
    {


        final String link =Constants.S3_BASE_URI+"/uploads/properties/"
        					+this.id
                            + "/"
                            + this.cover;
        return link;
        
    }
    
	/*
	 * @Transient public String getImagePath() {
	 * 
	 * 
	 * final String link = "properties/" +this.id + "/" + this.cover;
	 * 
	 * StorageManager storageService = new StorageManager(); StringBuilder filename
	 * = new StringBuilder( link ); Resource resource = storageService.load(
	 * filename.toString() );
	 * 
	 * // pick up default image if( resource == null ) { resource =
	 * storageService.load( Constants.DEFAULT_PROPERTY_COVER_IMAGE ); filename = new
	 * StringBuilder( Constants.UPLOAD_FOLDER ); filename.append(
	 * resource.getFilename() ); } else// pick up stored image { filename = new
	 * StringBuilder( Constants.UPLOAD_FOLDER ); filename.append( link ); } return
	 * filename.toString();
	 * 
	 * }
	 */
    
	/*
	 * @Transient public String getVideoPath() {
	 * 
	 * 
	 * final String link = "properties/"+ this.id+ "/" + this.video; StorageManager
	 * storageService = new StorageManager(); StringBuilder filename = new
	 * StringBuilder( link ); Resource resource = storageService.load(
	 * filename.toString() );
	 * 
	 * // pick up default video if( resource == null ) { resource =
	 * storageService.load( Constants.DEFAULT_PROPERTY_VIDEO ); filename = new
	 * StringBuilder( Constants.UPLOAD_FOLDER ); filename.append(
	 * resource.getFilename() ); } else// pick up stored video { filename = new
	 * StringBuilder( Constants.UPLOAD_FOLDER ); filename.append( link ); }
	 * 
	 * setFileExtension(filename.toString());
	 * 
	 * return filename.toString();
	 * 
	 * 
	 * }
	 */
    
    @Transient
    public String getVideoPath()
    {


    	String  link= Constants.S3_BASE_URI+"/properties/"+
    						  			this.id+ "/"
    						  			+ this.video;
    	
    	if(link.contains("null")) {
    		
    		link = Constants.S3_BASE_URI+"/uploads/"+Constants.DEFAULT_PROPERTY_VIDEO ;
    	}
       
    
        return link;
        
        
    }

    public void addImage( Image image )
    {
        images.add( image );
        image.setProperty( this );
    }

    public void removeImage( Image image )
    {
        images.remove( image );
        image.setProperty( null );
    }

    public void addDetail( Detail detail )
    {
        details.add( detail );
        detail.setProperty( this );
    }

    public void removeDetail( Detail detail )
    {
        details.remove( detail );
        detail.setProperty( null );
    }

    public Long getId()
    {
        return id;
    }
    public void setId( Long id )
    {
        this.id = id;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover( String image )
    {
        this.cover = image;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview( String overview )
    {
        this.overview = overview;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference( String reference )
    {
        this.reference = reference;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition( String condition )
    {
        this.condition = condition;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason( String reason )
    {
        this.reason = reason;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice( Double price )
    {
        this.price = price;
    }

    public String getRooms()
    {
        return rooms;
    }

    public void setRooms( String rooms )
    {
        this.rooms = rooms;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea( String area )
    {
        this.area = area;
    }

    public List< Detail > getDetails()
    {
        return details;
    }

    public void setDetails( List< Detail > details )
    {
        this.details = details;
    }

    public List< Image > getImages()
    {
        return images;
    }

    public void setImages( List< Image > images )
    {
        this.images = images;
    }

    public TUser getTuser()
    {
        return tuser;
    }


    public void setTuser( TUser user )
    {
        this.tuser = user;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress( Address address )
    {
        this.address = address;
    }
    public List< String > getImagesToRemove()
    {
        return imagesToRemove;
    }

    public void setImagesToRemove( List< String > imagesToremove )
    {
        this.imagesToRemove = imagesToremove;
    }
    
    

    public String getVideo() {
		return video;
	}


	public void setVideo(String video) {
		this.video = video;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
    public int hashCode()
    {
        return Objects.hash( id,
                             name,
                             reference );
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass() != obj.getClass() )
            return false;
        Property other = ( Property )obj;
        return Objects.equals( id,
                               other.id )
               && Objects.equals( name,
                                  other.name )
               && Objects.equals( reference,
                                  other.reference );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "Property [id=" );
        builder.append( id );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", type=" );
        builder.append( type );
        builder.append( ", status=" );
        builder.append( status );
        builder.append( ", condition=" );
        builder.append( condition );
        builder.append( ", reason=" );
        builder.append( reason );
        builder.append( ", price=" );
        builder.append( price );
        builder.append( ", rooms=" );
        builder.append( rooms );
        builder.append( ", area=" );
        builder.append( area );
        builder.append( ", cover=" );
        builder.append( cover );
        builder.append( ", overview=" );
        builder.append( overview );
        builder.append( ", description=" );
        builder.append( description );
        builder.append( ", reference=" );
        builder.append( reference );
        builder.append( ", images=" );
        builder.append( images );
        builder.append( ", details=" );
        builder.append( details );
        builder.append( ", tuser=" );
        builder.append( tuser );
        builder.append( ", address=" );
        builder.append( address );
        builder.append( ", imagesToRemove=" );
        builder.append( imagesToRemove );
        builder.append( "]" );
        return builder.toString();
    }

}

