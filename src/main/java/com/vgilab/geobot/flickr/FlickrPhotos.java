package com.vgilab.geobot.flickr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author ljzhang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotos {
    
    /*
    { "photos": { "page": 1, "pages": 10, "perpage": 100, "total": "1000", 
    "photo": [ ] }, "stat": "ok" }
    */
    
    private Long page;
    
    private Long pages;
    
    private Long perpage;
    
    private Long total;
    
    private List<FlickrPhoto> photo;

    public FlickrPhotos() {
        
    }

    /**
     * @return the page
     */
    public Long getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Long page) {
        this.page = page;
    }

    /**
     * @return the pages
     */
    public Long getPages() {
        return pages;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(Long pages) {
        this.pages = pages;
    }

    /**
     * @return the perpage
     */
    public Long getPerpage() {
        return perpage;
    }

    /**
     * @param perpage the perpage to set
     */
    public void setPerpage(Long perpage) {
        this.perpage = perpage;
    }
    

    /**
     * @return the total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return the photo
     */
    public List<FlickrPhoto> getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(List<FlickrPhoto> photo) {
        this.photo = photo;
    }
    
}
