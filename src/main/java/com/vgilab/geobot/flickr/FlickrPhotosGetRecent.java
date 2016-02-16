package com.vgilab.geobot.flickr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author ljzhang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotosGetRecent {

    private String stat;

    private FlickrPhotos photos;

    public FlickrPhotosGetRecent() {

    }

    /**
     * @return the stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat the stat to set
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    /**
     * @return the photos
     */
    public FlickrPhotos getPhotos() {
        return photos;
    }

    /**
     * @param photos the photos to set
     */
    public void setPhotos(FlickrPhotos photos) {
        this.photos = photos;
    }
}
