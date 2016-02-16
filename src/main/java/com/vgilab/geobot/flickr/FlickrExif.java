package com.vgilab.geobot.flickr;

/**
 *
 * @author ljzhang
 */
public class FlickrExif {
    private String tagspace;
    private String tagspaceid;
    private String tag;
    private String label;
    private FlickrExifContent raw;

    /**
     * @return the tagspaceid
     */
    public String getTagspaceid() {
        return tagspaceid;
    }

    /**
     * @param tagspaceid the tagspaceid to set
     */
    public void setTagspaceid(String tagspaceid) {
        this.tagspaceid = tagspaceid;
    }

    /**
     * @return the tagspace
     */
    public String getTagspace() {
        return tagspace;
    }

    /**
     * @param tagspace the tagspace to set
     */
    public void setTagspace(String tagspace) {
        this.tagspace = tagspace;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the raw
     */
    public FlickrExifContent getRaw() {
        return raw;
    }

    /**
     * @param raw the raw to set
     */
    public void setRaw(FlickrExifContent raw) {
        this.raw = raw;
    }

 
}
