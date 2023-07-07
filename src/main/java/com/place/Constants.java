
package com.place;

public class Constants
{
    // ---------------------------------------------------------------------
    // Properties
    // ---------------------------------------------------------------------
	private Constants() {
		
	}

    public static final String REFERENCE = "reference";
    public static final String INPUT_AVAILABILITY   = "availability_";
    public static final String INPUT_NAME    = "name_";
    public static final String INPUT_SECTION = "section_";
    public static final String INPUT_ID      = "id_";
    public static final String UPLOAD_FOLDER = "uploads/";
    public static final String DEFAULT_USER_IMAGE     = "default_user.jpg";
    public static final String DEFAULT_PROPERTY_COVER_IMAGE = "default_property_cover.png";
    public static final String DEFAULT_PROPERTY_IMAGE = "default_property.png";
    public static final String DEFAULT_LOGO = "logo-raw.png";
    public static final String DEFAULT_EMAIL = "info@email.com";
    public static final String DEFAULT_PHONE = "123456789";
    public static final String DEFAULT_LANGUAGE = "PT";
    public static final String DEFAULT_ADD_IMAGE = "default_ad.jpg";
    public static final String DEFAULT_PROPERTY_VIDEO = "default_video.mp4";
    
    public static final String S3_BASE_URI;
    
    static {
    	String bucketName = System.getenv("AWS_BUCKET_NAME");
    	String region = System.getenv("AWS_REGION");
    	String pattern = "https://%s.s3.%s.amazonaws.com";
    	String uri = String.format(pattern,bucketName,region);
    	S3_BASE_URI = bucketName == null ? "" : uri;
    }
    // ---------------------------------------------------------------------
    // Construction
    // ---------------------------------------------------------------------
	

    // ---------------------------------------------------------------------
    // Private Helper Methods
    // ---------------------------------------------------------------------

    // ---------------------------------------------------------------------
    // Public Methods
    // ---------------------------------------------------------------------
    
 

}

