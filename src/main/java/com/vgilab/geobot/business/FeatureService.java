package com.vgilab.geobot.business;

import com.vgilab.geobot.persistence.entity.Coordinates;
import com.vgilab.geobot.persistence.repositories.CoordinateRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import java.util.ArrayList;
import java.util.List;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * http://docs.geotools.org/stable/tutorials/feature/csv2shp.html
 * 
 * @author smuellner
 */
@Service
public class FeatureService {

    @Autowired
    private CoordinateRepository coordinateRepository;

    public List<SimpleFeature> exportAllFeatures() {
        /*
         * A list to collect features as we create them.
         */
        final List<SimpleFeature> features = new ArrayList<>();
        /*
         * GeometryFactory will be used to create the geometry attribute of each feature,
         * using a Point object for the location.
         */
        final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        final SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(this.getFeatureType());
        final List<Coordinates> allCoordinates = this.coordinateRepository.findAll();
        for (final Coordinates curCoordinate : allCoordinates) {
            if (null != curCoordinate.getLatitudeConv() && null != curCoordinate.getLongitudeConv()) {
                final Double longitude = curCoordinate.getLongitudeConv().doubleValue();
                final Double latitude = curCoordinate.getLatitudeConv().doubleValue();
                final Coordinate coordinate = curCoordinate.getAltitudeConv() != null ? new Coordinate(longitude, latitude, curCoordinate.getAltitudeConv().doubleValue()) : new Coordinate(longitude, latitude);
                final Point point = geometryFactory.createPoint(coordinate);
                featureBuilder.add(point);
                featureBuilder.add(curCoordinate.getUniqueName());
                if (null != curCoordinate.getAltitudeConv()) {
                    featureBuilder.add(curCoordinate.getAltitudeConv().doubleValue());
                }
                features.add(featureBuilder.buildFeature(null));
            }
        }
        return features;
    }

    public SimpleFeatureType getFeatureType() {
        final SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();
        featureTypeBuilder.setName("Point");
        featureTypeBuilder.setCRS(DefaultGeographicCRS.WGS84_3D); // set crs first
        featureTypeBuilder.add("the_geom", Point.class); // then add geometry
        featureTypeBuilder.add("name", String.class);
        featureTypeBuilder.add("height", Double.class);
        return featureTypeBuilder.buildFeatureType();
    }
}
