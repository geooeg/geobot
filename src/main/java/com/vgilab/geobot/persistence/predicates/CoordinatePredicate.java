package com.vgilab.geobot.persistence.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.vgilab.geobot.persistence.entity.QCoordinates;
import org.apache.commons.lang3.StringUtils;

/**
 * Predicate forcreating coordinate based SQL filter criterias.
 * 
 * @author ljzhang
 */
public class CoordinatePredicate {

    /**
     *
     * @param latitude
     * @param longitude
     * @param altitude
     * @param maker
     * @param model
     * @return
     */
    public static Predicate predicateWithLocationAndDevice(String latitude, String longitude, String altitude, String maker, String model) {
        final BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.isNotBlank(latitude)) {
            where.and(latitudeContains(latitude));
        }
        if (StringUtils.isNotBlank(longitude)) {
            where.and(longitudeContains(longitude));
        }
        if (StringUtils.isNotBlank(altitude)) {
            where.and(altitudeContains(altitude));
        }
        if (StringUtils.isNotBlank(maker)) {
            where.and(makerContains(maker));
        }
        if (StringUtils.isNotBlank(model)) {
            where.and(modelContains(model));
        }
        where.and(QCoordinates.coordinates.latitude.isNotEmpty());
        where.and(QCoordinates.coordinates.longitude.isNotEmpty());
        return where;
    }
    
    
    public static Predicate predicate() {
        final BooleanBuilder where = new BooleanBuilder();
        where.and(QCoordinates.coordinates.latitude.isNotEmpty());
        where.and(QCoordinates.coordinates.longitude.isNotEmpty());
        return where;
    }
    
    /**
     *
     * @param latitude
     * @return
     */
    public static Predicate latitudeContains(String latitude) {
        final QCoordinates coordinates = QCoordinates.coordinates;
        return coordinates.latitude.containsIgnoreCase(latitude);
    }
    
    /**
     *
     * @param longitude
     * @return
     */
    public static Predicate longitudeContains(String longitude) {
        final QCoordinates coordinates = QCoordinates.coordinates;
        return coordinates.longitude.containsIgnoreCase(longitude);
    }
    
    /**
     *
     * @param altitude
     * @return
     */
    public static Predicate altitudeContains(String altitude) {
        final QCoordinates coordinates = QCoordinates.coordinates;
        return coordinates.altitude.containsIgnoreCase(altitude);
    }
    
    /**
     *
     * @param maker
     * @return
     */
    public static Predicate makerContains(String maker) {
        final QCoordinates coordinates = QCoordinates.coordinates;
        return coordinates.maker.containsIgnoreCase(maker);
    }
    
    /**
     *
     * @param model
     * @return
     */
    public static Predicate modelContains(String model) {
        final QCoordinates coordinates = QCoordinates.coordinates;
        return coordinates.model.containsIgnoreCase(model);
    }

    /**
     *
     * @param title
     * @return
     */
//    public static JPASubQuery titleContains(String title) {
//        final QCategory category = QCategory.category;
//        final QCategoryLocalisation categoryLocalisation = QCategoryLocalisation.categoryLocalisation;
//        return new JPASubQuery().from(categoryLocalisation).where(categoryLocalisation.category.eq(category),
//                categoryLocalisation.title.containsIgnoreCase(title));
//    }

}
