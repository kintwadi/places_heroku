
package com.place.admin.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table( name = "PROPERTY_DETAIL" )
public class Detail
{
    // ---------------------------------------------------------------------
    // Properties
    // ---------------------------------------------------------------------
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long     id;
    private String  section;
    private String  name;
    private boolean availability;
    @ManyToOne( fetch = FetchType.LAZY )
    private Property property;
    @Transient
    private String   reference;
    // ---------------------------------------------------------------------
    // Construction
    // ---------------------------------------------------------------------
    public Detail()
    {

    }

    public Detail( Long id,
                   String section,
                   String name,
                   boolean state )
    {
        this.id = id;
        this.section = section;
        this.name = name;
        this.availability = state;
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

    public String getSection()
    {
        return section;
    }

    public void setSection( String section )
    {
        this.section = section;
    }
    public String getName()
    {
        return name;
    }
    public void setName( String name )
    {
        this.name = name;
    }
    public boolean isAvailability()
    {
        return availability;
    }
    public void setAvailability( boolean state )
    {
        this.availability = state;
    }

    public Property getProperty()
    {
        return property;
    }

    public void setProperty( Property property )
    {
        this.property = property;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference( String reference )
    {
        this.reference = this.property.getReference();
    }

    public void AddReference( String reference )
    {
        this.reference = reference;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "Detail [id=" );
        builder.append( id );
        builder.append( ", section=" );
        builder.append( section );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", state=" );
        builder.append( availability );
        builder.append( ", reference=" );
        builder.append( reference );
        builder.append( "]" );
        return builder.toString();
    }

}

