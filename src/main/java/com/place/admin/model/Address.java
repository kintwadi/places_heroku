
package com.place.admin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table( name = "ADDRESS" )
public class Address
{
    // ---------------------------------------------------------------------
    // Properties
    // ---------------------------------------------------------------------
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long     id;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String zipcode;
    private Double latitude;
    private Double longitude;

    @OneToOne( mappedBy = "address" )
    private Property property;
    @OneToOne( mappedBy = "address" )
    private TUser    tuser;
    // ---------------------------------------------------------------------
    // Construction
    // ---------------------------------------------------------------------

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
    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public void setHouseNumber( String houseNumber )
    {
        this.houseNumber = houseNumber;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode( String zipcode )
    {
        this.zipcode = zipcode;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude( Double latitude )
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude( Double longitude )
    {
        this.longitude = longitude;
    }

    public Property getProperty()
    {
        return property;
    }

    public void setProperty( Property property )
    {
        this.property = property;
    }

    public TUser getTuser()
    {
        return tuser;
    }

    public void setTuser( TUser tuser )
    {
        this.tuser = tuser;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "Address [id=" );
        builder.append( id );
        builder.append( ", country=" );
        builder.append( country );
        builder.append( ", city=" );
        builder.append( city );
        builder.append( ", street=" );
        builder.append( street );
        builder.append( ", houseNumber=" );
        builder.append( houseNumber );
        builder.append( ", zipcode=" );
        builder.append( zipcode );
        builder.append( ", latitude=" );
        builder.append( latitude );
        builder.append( ", longitude=" );
        builder.append( longitude );
        builder.append( ", tuser=" );
        builder.append( tuser );
        builder.append( "]" );
        return builder.toString();
    }

}

