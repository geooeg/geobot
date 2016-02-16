package com.vgilab.geobot.everytrail;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author smuellner
 */
@XmlRootElement(name="trip")
public class EtTrip {
    
    @JacksonXmlProperty(isAttribute = true)
    private String id;

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
    
}
