package com.vgilab.geobot.flickr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author ljzhang
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhoto {
     // { "id": "23281057674", "owner": "69350049@N02", "secret": "ec7eeec315", "server": "766", "farm": 1, "title": "Professional Strategies", "ispublic": 1, "isfriend": 0, "isfamily": 0 },

    private String id;
    
    private String secret;
    
    private List<FlickrExif> exif;
    
    private FlickrPhotoLocation location;
    
    public FlickrPhoto() {
        
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the exif
     */
    public List<FlickrExif> getExif() {
        return exif;
    }

    /**
     * @param exif the exif to set
     */
    public void setExif(List<FlickrExif> exif) {
        this.exif = exif;
    }

    /**
     * @return the location
     */
    public FlickrPhotoLocation getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(FlickrPhotoLocation location) {
        this.location = location;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
}
