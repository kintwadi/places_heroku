
package com.place.admin.model;

import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.place.Constants;
import com.place.admin.service.StorageManager;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table( name = "PROPERTY_IMAGE" )
public class Image
{
    // ---------------------------------------------------------------------
    // Properties
    // ---------------------------------------------------------------------
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long   id;
    private String name;
    @Transient
    private String imagePath;
    @ManyToOne( fetch = FetchType.LAZY )
    private Property property;
    @Transient
    private MultipartFile file;
    // ---------------------------------------------------------------------
    // Construction
    // ---------------------------------------------------------------------
    public Image()
    {

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

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }


    public Property getProperty()
    {
        return property;
    }

    public void setProperty( Property property )
    {
        this.property = property;
    }
    
    @Transient
    public String getImagePath() {

        final String link = Constants.S3_BASE_URI+ "/uploads/properties/"
        					+this.property.getId()
                            + "/images/"
                            + this.name;
        return link;

    }
    
	/*
	 * @Transient public String getImagePath() {
	 * 
	 * final String link = "properties/" +this.property.getId() + "/images/" +
	 * this.name;
	 * 
	 * 
	 * StorageManager storageService = new StorageManager(); StringBuilder filename
	 * = new StringBuilder( link ); Resource resource = storageService.load(
	 * filename.toString() );
	 * 
	 * // pick up default image if( resource == null ) { resource =
	 * storageService.load( Constants.DEFAULT_PROPERTY_IMAGE ); filename = new
	 * StringBuilder( Constants.UPLOAD_FOLDER ); filename.append(
	 * resource.getFilename() ); } else// pick up stored image { filename = new
	 * StringBuilder( Constants.UPLOAD_FOLDER ); filename.append( link ); } return
	 * filename.toString();
	 * 
	 * }
	 */
    public MultipartFile getFile()
    {
        return file;
    }

    public void setFile( MultipartFile file )
    {
        this.file = file;
    }
    @Override
    public int hashCode()
    {
        return Objects.hash( id,
                             name,
                             property );
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
        Image other = ( Image )obj;
        return Objects.equals( id,
                               other.id )
               && Objects.equals( name,
                                  other.name )
               && Objects.equals( property,
                                  other.property );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "Image [id=" );
        builder.append( id );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", imagePath=" );
        builder.append( imagePath );
        builder.append( "]" );
        return builder.toString();
    }


}

