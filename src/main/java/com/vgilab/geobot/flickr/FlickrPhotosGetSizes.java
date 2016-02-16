package com.vgilab.geobot.flickr;

/**
 *
 * @author ljzhang
 */
public class FlickrPhotosGetSizes {
    
    private String stat;

    private FlickrSizes sizes;

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
     * @return the sizes
     */
    public FlickrSizes getSizes() {
        return sizes;
    }

    /**
     * @param sizes the sizes to set
     */
    public void setSizes(FlickrSizes sizes) {
        this.sizes = sizes;
    }
    
}
