
package com.place.admin.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import com.place.Constants;
import com.place.admin.service.StorageManager;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table( name = "TUSER" )
public class TUser
{
    // ---------------------------------------------------------------------
    // Properties
    // ---------------------------------------------------------------------
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long             id;
    private String           photo;
    private String           name;
    private String           email;
    private String           phoneNumber;
    @OneToMany( mappedBy = "tuser",
                cascade = CascadeType.ALL,
                orphanRemoval = true )
    private List< Property > properties = new ArrayList< Property >();

    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "address_id", referencedColumnName = "id" )
    private Address          address;

    // ---------------------------------------------------------------------
    // Construction
    // ---------------------------------------------------------------------
    public TUser()
    {

    }

    public TUser( String email )
    {
        this.email = email;
    }
    // ---------------------------------------------------------------------
    // Private Helper Methods
    // ---------------------------------------------------------------------

    // ---------------------------------------------------------------------
    // Public Methods
    // ---------------------------------------------------------------------

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto( String photo )
    {
        this.photo = photo;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber )
    {
        this.phoneNumber = phoneNumber;
    }

    public List< Property > getProperties()
    {
        return properties;
    }

    public void setProperties( List< Property > properties )
    {
        this.properties = properties;
    }

    public void addProperties( Property property )
    {
        properties.add( property );
        property.setTuser( this );
    }

    public void removeProperty( Property property )
    {
        properties.remove( property );
        property.setTuser( null );
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress( Address address )
    {
        this.address = address;
    }
    
    @Transient
    public String getImagePath()
    {
        final String link = Constants.S3_BASE_URI+"/uploads/users/"
        					+this.id
                            + "/"
                            + this.photo;
        return link;

    }

//    @Transient
//    public String getImagePath()
//    {
//    	final String link = "users/"
//    			+this.id
//    			+ "/"
//    			+ this.photo;
//
//    	StorageManager storageService = new StorageManager();
//    	StringBuilder filename = new StringBuilder( link );
//    	Resource resource = storageService.load( filename.toString() );
//
//    	// pick up default image
//    	if( resource == null )
//    	{
//    		resource = storageService.load( Constants.DEFAULT_USER_IMAGE );
//    		filename = new StringBuilder( Constants.UPLOAD_FOLDER );
//    		filename.append( resource.getFilename() );
//    	}
//    	else if( resource != null )// pick up stored image
//    	{
//    		filename = new StringBuilder( Constants.UPLOAD_FOLDER );
//    		filename.append( link );
//    	}
//
//    	return filename.toString();
//
//    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "TUser [id=" );
        builder.append( id );
        builder.append( ", photo=" );
        builder.append( photo );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", email=" );
        builder.append( email );
        builder.append( ", phoneNumber=" );
        builder.append( phoneNumber );
        builder.append( "]" );
        return builder.toString();
    }

}

