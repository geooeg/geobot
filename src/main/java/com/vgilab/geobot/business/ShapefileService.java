package com.vgilab.geobot.business;

import com.google.common.io.Files;
import com.vgilab.geobot.persistence.entity.Coordinates;
import com.vgilab.geobot.persistence.repositories.CoordinateRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zhang
 */
@Service
public class ShapefileService {

    @Autowired
    CoordinateRepository coordinateRepository;

    public File exportShapefile() {
        /*
            * A list to collect features as we create them.
         */
        final List<SimpleFeature> features = new ArrayList<>();
        /*
            * GeometryFactory will be used to create the geometry attribute of each feature,
            * using a Point object for the location.
         */
        final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        final SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();

//set the name
        featureTypeBuilder.setName("Point");

//add some properties
        featureTypeBuilder.add("name", String.class);
        featureTypeBuilder.add("height", Double.class);

//add a geometry property
        featureTypeBuilder.setCRS(DefaultGeographicCRS.WGS84); // set crs first
        featureTypeBuilder.add("location", Point.class); // then add geometry

//build the type
        final SimpleFeatureType TYPE = featureTypeBuilder.buildFeatureType();
        final SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        final List<Coordinates> allCoordinates = this.coordinateRepository.findAll();
        for (final Coordinates curCoordinate : allCoordinates) {
            /* Longitude (= x coord) first ! */
            if (null != curCoordinate.getLatitudeConv() && null != curCoordinate.getLongitudeConv()) {
                final Point point = geometryFactory.createPoint(new Coordinate(curCoordinate.getLongitudeConv().doubleValue(), curCoordinate.getLatitudeConv().doubleValue()));

                featureBuilder.add(curCoordinate.getUniqueName());
                // 
                if (null == curCoordinate.getAltitudeConv()) {
                    featureBuilder.add(curCoordinate.getAltitudeConv());
                }
                featureBuilder.add(point);
                // featureBuilder.add(number);
                final SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }
        }
        final File shapeDir = Files.createTempDir();
        final File shapeFile = new File(shapeDir, "geobot.shp");
        // LOG.debug("writing out shapefile {}", shapeFile);
        try {

            final ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            final Map<String, Serializable> params = new HashMap<>();
            params.put("url", shapeFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);

            final ShapefileDataStore shapefileDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            shapefileDataStore.createSchema(TYPE);

            Transaction transaction = new DefaultTransaction("create");

            String typeName = shapefileDataStore.getTypeNames()[0];

            SimpleFeatureSource featureSource = shapefileDataStore.getFeatureSource(typeName);
            // SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                /*
             * SimpleFeatureStore has a method to add features from a
             * SimpleFeatureCollection object, so we use the ListFeatureCollection
             * class to wrap our list of features.
                 */
                SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
            shapeDir.deleteOnExit(); // Note: the order is important
            final File zipFile = new File(shapeDir + File.pathSeparator + "geobot.zip");
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
                for (File file : shapeDir.listFiles()) {
                    file.deleteOnExit();
                    // ZipEntry entry = new ZipEntry(shapeDir.replace(rootDir, "") + file.getName());
                    final ZipEntry entry = new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(entry);
                    
                    final FileInputStream in = new FileInputStream(file);
                    IOUtils.copy(in, zipOutputStream);
                    IOUtils.closeQuietly(in);
                }
                zipOutputStream.flush();
            }
            return zipFile;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShapefileService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ShapefileService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShapefileService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
