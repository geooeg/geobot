package com.vgilab.geobot.view;

import com.mysema.query.types.Predicate;
import com.vgilab.geobot.business.ShapefileService;
import com.vgilab.geobot.flickr.FlickrApi;
import com.vgilab.geobot.persistence.entity.Coordinates;
import com.vgilab.geobot.persistence.predicates.CoordinatePredicate;
import com.vgilab.geobot.persistence.repositories.CoordinateRepository;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 *
 * @author ljzhang
 */
@Component
@ManagedBean(name = "coordinateView")
@SessionScoped
public class CoordinateView implements Serializable {

    @Autowired
    CoordinateRepository coordinateRepository;

    @Autowired
    ShapefileService shapefileService;

    private LazyDataModel<Coordinates> coordinates;

    private Coordinates selected;

    private MapModel markerModel;

    private String selectedImageURL;

    @PostConstruct
    public void init() {
        this.markerModel = new DefaultMapModel();
        this.coordinates = new LazyDataModel<Coordinates>() {
            @Override
            public List<Coordinates> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                final boolean ascending = (null != sortOrder ? SortOrder.ASCENDING.equals(sortOrder) : null);
                final int currentPage = startingAt / maxPerPage;
                final Page<Coordinates> page;
                final PageRequest pageRequest;
                if (StringUtils.isNotBlank(sortField) && null != sortOrder) {
                    pageRequest = new PageRequest(currentPage, maxPerPage, new Sort(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortField));
                } else {
                    pageRequest = new PageRequest(currentPage, maxPerPage);
                }
                if (null != filters && !filters.isEmpty()) {
                    page = CoordinateView.this.coordinateRepository.findAll(CoordinateView.this.getCoordinatePredicate(filters), pageRequest);
                    CoordinateView.this.coordinates.setRowCount(Long.valueOf(CoordinateView.this.coordinateRepository.count(CoordinateView.this.getCoordinatePredicate(filters))).intValue());
                } else {
                    page = CoordinateView.this.coordinateRepository.findAll(CoordinatePredicate.predicate(), pageRequest);
                    CoordinateView.this.coordinates.setRowCount(Long.valueOf(CoordinateView.this.coordinateRepository.count(CoordinatePredicate.predicate())).intValue());
                }
                final List<Coordinates> result = new LinkedList<>();
                for (Coordinates curCoordinate : page) {
                    result.add(curCoordinate);
                }
                return result;
            }

            @Override
            public Coordinates getRowData(String rowKey) {
                return CoordinateView.this.coordinateRepository.findOne(Long.valueOf(rowKey));
            }

            @Override
            public Object getRowKey(Coordinates product) {
                return product.getId();
            }
        };
    }

    private Predicate getCoordinatePredicate(Map<String, Object> filters) {
        final String latitude = filters.containsKey("latitude") ? String.valueOf(filters.get("latitude")) : null;
        final String longitude = filters.containsKey("longitude") ? String.valueOf(filters.get("longitude")) : null;
        final String altitude = filters.containsKey("altitude") ? String.valueOf(filters.get("altitude")) : null;
        final String maker = filters.containsKey("maker") ? String.valueOf(filters.get("maker")) : null;
        final String model = filters.containsKey("model") ? String.valueOf(filters.get("model")) : null;
        return CoordinatePredicate.predicateWithLocationAndDevice(latitude, longitude, altitude, maker, model);
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Coordinates) event.getObject();
        this.selectedImageURL = null;
        this.getMarkerModel().getMarkers().clear();
        if (null != this.selected && null != this.selected.getLatitudeConv() && null != this.selected.getLongitudeConv()) {
            final LatLng coord = new LatLng(this.selected.getLatitudeConv().doubleValue(), this.selected.getLongitudeConv().doubleValue());
            final StringBuilder altitude = new StringBuilder();
            altitude.append(StringUtils.isBlank(this.selected.getAltitude()) ? "No altitude" : this.selected.getAltitude());
            altitude.append("\n");
            altitude.append(StringUtils.isBlank(this.selected.getAltitudeRef()) ? "" : this.selected.getAltitudeRef());
            altitude.append("\n");
            altitude.append("Numeric: ");
            altitude.append(null == this.selected.getAltitudeConv() ? "-" : this.selected.getAltitudeConv());
            this.getMarkerModel().addOverlay(new Marker(coord, altitude.toString(), "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
            this.selectedImageURL = FlickrApi.getImageURL(this.selected.getUniqueName(), "Small 320");
        }
    }

    public void onRowUnselect(SelectEvent event) {
        this.selected = null;
    }

    public Coordinates prepareCreate() {
        this.selected = new Coordinates();
        return getSelected();
    }

    public void update() {
        if (this.selected != null) {
            this.coordinateRepository.save(this.selected);
        }
    }

    /**
     *
     * @return
     */
    public LazyDataModel<Coordinates> getCoordinates() {
        return this.coordinates;
    }

    /**
     * @return the selected
     */
    public Coordinates getSelected() {
        return selected;
    }

    /**
     * @return the center position
     */
    public String getCenter() {
        return (null != this.selected && null != this.selected.getLatitudeConv() && null != this.selected.getLongitudeConv()) ? this.selected.getLatitudeConv().toPlainString() + "," + this.selected.getLongitudeConv().toPlainString() : "0.0,0.0";
    }

    /**
     * @return the markerModel
     */
    public MapModel getMarkerModel() {
        return this.markerModel;
    }

    /**
     * @return the selectedImageURL
     */
    public String getSelectedImageURL() {
        return selectedImageURL;
    }

    public StreamedContent getShapefile() {
        InputStream fis = null;
        try {
            final File shapefile = this.shapefileService.exportShapefile();
            fis = new FileInputStream(shapefile);
            return new DefaultStreamedContent(fis, "application/zip", "exort.zip");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CoordinateView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(null != fis) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CoordinateView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
