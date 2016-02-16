package com.vgilab.geobot.flickr;

/**
 *
 * @author ljzhang
 */
public class FlickrPhotosGetInfo {
    
    private String stat;

    private FlickrPhoto photo;

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
     * @return the photo
     */
    public FlickrPhoto getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(FlickrPhoto photo) {
        this.photo = photo;
    }
}