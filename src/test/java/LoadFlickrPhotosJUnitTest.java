
import com.vgilab.geobot.PhotoInfo;
import com.vgilab.geobot.flickr.FlickrApi;
import com.vgilab.geobot.flickr.FlickrPhoto;
import com.vgilab.geobot.flickr.FlickrPhotosGetExif;
import com.vgilab.geobot.flickr.FlickrPhotosGetRecent;
import com.vgilab.geobot.persistence.entity.Coordinates;
import com.vgilab.geobot.util.ExifUtil;
import com.vgilab.geobot.util.PhotoInfoToCoordinateMapper;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static javax.persistence.Persistence.createEntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author ljzhang
 */
// @Ignore
public class LoadFlickrPhotosJUnitTest {

    private final static Logger LOGGER = Logger.getGlobal();

    final EntityManagerFactory emf;
    final EntityManager em;

    public LoadFlickrPhotosJUnitTest() {
        this.emf = createEntityManagerFactory("com.vgilab.geobot");
        this.em = this.emf.createEntityManager();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Ignore
    @Test
    public void flickrPhotosGetRecent2() {
        final FlickrPhotosGetRecent photos = FlickrApi.flickrPhotosGetRecent(1, 100);
        LOGGER.severe(photos.getStat());
        for (FlickrPhoto flickrPhoto : photos.getPhotos().getPhoto()) {
            LOGGER.log(Level.SEVERE, flickrPhoto.getId());
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Ignore
    @Test
    public void flickrPhotosGetRecent() {
        final FlickrPhotosGetRecent photos = FlickrApi.flickrPhotosGetRecent(1, 100);
        LOGGER.severe(photos.getStat());
        for (FlickrPhoto flickrPhoto : photos.getPhotos().getPhoto()) {
            final PhotoInfo position = FlickrApi.flickrPhotosGetPosition(flickrPhoto.getId());
            LOGGER.log(Level.SEVERE, MessageFormat.format("{0} {1}", flickrPhoto.getId(), position != null ? position.toString() : "No GPS data"));
            // TODO store values
        }
    }

    @Ignore
    @Test
    public void flickrPhotosGetExif() {
        final FlickrPhotosGetExif photo = FlickrApi.flickrPhotosGetExif("23282875704");
        LOGGER.severe(photo.getStat());
    }

    /**
     *
     */
    @Ignore
    @Test
    public void flickrPhotosGetRecentAndPersist() {
        final Integer perPage = 1000;
        FlickrPhotosGetRecent photos = FlickrApi.flickrPhotosGetRecent(0, 1);
        final Long pages = photos.getPhotos().getPages();
        try {
            for (int currentPage = 1; currentPage <= pages; currentPage++) {
                photos = FlickrApi.flickrPhotosGetRecent(currentPage, perPage);
                if (photos.getPhotos().getPhoto().isEmpty()) {
                    LOGGER.log(Level.SEVERE, "No values found scanning finished.");
                    return;
                }
                LOGGER.severe(photos.getStat());
                for (FlickrPhoto flickrPhoto : photos.getPhotos().getPhoto()) {
                    final PhotoInfo position = FlickrApi.flickrPhotosGetPosition(flickrPhoto.getId());
                    // LOGGER.log(Level.SEVERE, MessageFormat.format("{0} {1}", flickrPhoto.getId(), position != null ? position.toString() : "No GPS data"));
                    // TODO store values
                    final Query createQuery = this.em.createQuery("SELECT COUNT(c.id) FROM Coordinates c WHERE c.source = 'FLICKR' AND c.uniqueName = :uniqueName");
                    createQuery.setParameter("uniqueName", flickrPhoto.getId());
                    final Long singleResult = (Long) createQuery.getSingleResult();
                    if (0 == singleResult) {
                        try {
                            this.em.getTransaction().begin();
                            final Coordinates coordinate = position != null ? PhotoInfoToCoordinateMapper.getDtoToEntityModellMapper().map(position, Coordinates.class) : new Coordinates();
                            coordinate.setSource("FLICKR");
                            coordinate.setUniqueName(flickrPhoto.getId());
                            if (null != position) {
                                final Calendar timestamp = ExifUtil.getCalendarFromExifDate(position.getGpsTimeStamp(), position.getGpsDateStamp());
                                if (null != timestamp) {
                                    coordinate.setTimestamp(timestamp);
                                }
                                final BigDecimal altitude = ExifUtil.getAltitudeFromExif(position.getAltitude(), position.getAltitudeRef());
                                if (null != altitude) {
                                    coordinate.setAltitudeConv(altitude);
                                }
                                final BigDecimal latitudeDecimalDegree = ExifUtil.getDecimalDegreeFromExif(position.getLatitude(), position.getLatitudeRef());
                                if (null != latitudeDecimalDegree) {
                                    coordinate.setLatitudeConv(latitudeDecimalDegree);
                                }
                                final BigDecimal longitudeDecimalDegree = ExifUtil.getDecimalDegreeFromExif(position.getLongitude(), position.getLongitudeRef());
                                if (null != longitudeDecimalDegree) {
                                    coordinate.setLongitudeConv(longitudeDecimalDegree);
                                }
                            }
                            coordinate.setCreatedOn(Calendar.getInstance());
                            this.em.persist(coordinate);
                            this.em.getTransaction().commit();
                        } catch (IllegalStateException | RollbackException e) {
                            this.em.getTransaction().rollback();
                            // need to check if this is true
                            LOGGER.log(Level.SEVERE, MessageFormat.format("Error saving data: {0}", e.getLocalizedMessage()));
                        }
                    } else {
                        // throw new ExistingValueException();
                    }
                }
            }
        } catch (EntityExistsException e) {
            LOGGER.log(Level.SEVERE, MessageFormat.format("Found an existing entry, stopping capture: {0}", e.getLocalizedMessage()));
        }
        if (this.em.isOpen()) {
            this.em.close();
        }
    }

    @Ignore
    @Test
    public void flickrPhotosRefreshDataAndPersist() {
        try {
            /**
             * CriteriaBuilder cb = em.getCriteriaBuilder();
             * CriteriaQuery<Coordinates> cq =
             * cb.createQuery(Coordinates.class); Root<Coordinates> rootEntry =
             * cq.from(Coordinates.class); CriteriaQuery<Coordinates> all =
             * cq.select(rootEntry); TypedQuery<Coordinates> allQuery =
             * em.createQuery(all);
             */
            final Query allQuery = this.em.createQuery("SELECT c FROM Coordinates c");
            final List<Coordinates> coordinates = allQuery.getResultList();
            for (Coordinates curCoordinate : coordinates) {
                if (StringUtils.isNotEmpty(curCoordinate.getLatitude()) && StringUtils.isNotEmpty(curCoordinate.getLongitude())) {
                    final PhotoInfo position = FlickrApi.flickrPhotosGetPosition(curCoordinate.getUniqueName());
                    if (null != position) {
                        try {
                            this.em.getTransaction().begin();
                            PhotoInfoToCoordinateMapper.getDtoToEntityModellMapper().map(position, curCoordinate);
                            final Calendar timestamp = ExifUtil.getCalendarFromExifDate(position.getGpsTimeStamp(), position.getGpsDateStamp());
                            if (null != timestamp) {
                                curCoordinate.setTimestamp(timestamp);
                            }
                            final BigDecimal altitude = ExifUtil.getAltitudeFromExif(position.getAltitude(), position.getAltitudeRef());
                            if (null != altitude) {
                                curCoordinate.setAltitudeConv(altitude);
                            }
                            final BigDecimal latitudeDecimalDegree = ExifUtil.getDecimalDegreeFromExif(position.getLatitude(), position.getLatitudeRef());
                            if (null != latitudeDecimalDegree) {
                                curCoordinate.setLatitudeConv(latitudeDecimalDegree);
                            }
                            final BigDecimal longitudeDecimalDegree = ExifUtil.getDecimalDegreeFromExif(position.getLongitude(), position.getLongitudeRef());
                            if (null != longitudeDecimalDegree) {
                                curCoordinate.setLongitudeConv(longitudeDecimalDegree);
                            }
                            curCoordinate.setModifiedOn(Calendar.getInstance());
                            this.em.persist(curCoordinate);
                            this.em.getTransaction().commit();
                        } catch (IllegalStateException | RollbackException e) {
                            this.em.getTransaction().rollback();
                            // need to check if this is true
                            LOGGER.log(Level.SEVERE, MessageFormat.format("Error saving data: {0}", e.getLocalizedMessage()));
                        }
                    }
                }
            }
        } catch (EntityExistsException e) {
            LOGGER.log(Level.SEVERE, MessageFormat.format("Found an existing entry, stopping capture: {0}", e.getLocalizedMessage()));
        }
        if (this.em.isOpen()) {
            this.em.close();
        }
    }
}
