package com.vgilab.geobot.flickr;

import com.vgilab.geobot.PhotoInfo;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author ljzhang
 */
public class FlickrApi {

    public static String FLICKR_REST_URL = "https://api.flickr.com/services/rest/";
    public static String FLICKR_API_KEY = "";
    public static String FLICKR_STAT_OK = "ok";

    private final static Logger LOGGER = Logger.getGlobal();

    public static FlickrPhotosGetRecent flickrPhotosGetRecent(Integer page, Integer perPage) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FLICKR_REST_URL)
                .queryParam("api_key", FLICKR_API_KEY)
                .queryParam("nojsoncallback", "1")
                .queryParam("format", "json")
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .queryParam("method", "flickr.photos.getRecent");
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        
        // lets just get the plain response as a string
        final HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);

        LOGGER.severe(response.getBody());

        // Now deserialize/map the json data to our defined POJOs
        final ResponseEntity<FlickrPhotosGetRecent> photosResponse
                = restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<FlickrPhotosGetRecent>() {
                });
        return photosResponse.getBody();
    }
    
    public static FlickrPhotosGetInfo flickrPhotosGetInfo(String photoId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FLICKR_REST_URL)
                .queryParam("api_key", FLICKR_API_KEY)
                .queryParam("nojsoncallback", "1")
                .queryParam("format", "json")
                .queryParam("method", "flickr.photos.getInfo")
                .queryParam("photo_id", photoId);
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        // Now deserialize/map the json data to our defined POJOs
        final ResponseEntity<FlickrPhotosGetInfo> photosResponse
                = restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<FlickrPhotosGetInfo>() {
                });
        return photosResponse.getBody();
    }

    public static String getImageURL(String photoId, String label) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FLICKR_REST_URL)
                .queryParam("api_key", FLICKR_API_KEY)
                .queryParam("nojsoncallback", "1")
                .queryParam("format", "json")
                .queryParam("method", "flickr.photos.getSizes")
                .queryParam("photo_id", photoId);
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        // Now deserialize/map the json data to our defined POJOs
        final ResponseEntity<FlickrPhotosGetSizes> photosResponse
                = restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<FlickrPhotosGetSizes>() {
                });
        FlickrPhotosGetSizes body = photosResponse.getBody();
        for(final FlickrSize curFlickrSize : body.getSizes().getSize()) {
            if(null == label) {
                return curFlickrSize.getSource();
            } else if (StringUtils.equalsIgnoreCase(label, curFlickrSize.getLabel())) {
                return curFlickrSize.getSource();
            }
        }
        return null;
    }

    public static FlickrPhotosGetExif flickrPhotosGetExif(String photoId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FLICKR_REST_URL)
                .queryParam("api_key", FLICKR_API_KEY)
                .queryParam("nojsoncallback", "1")
                .queryParam("format", "json")
                .queryParam("method", "flickr.photos.getExif")
                .queryParam("photo_id", photoId);
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        // Now deserialize/map the json data to our defined POJOs
        final ResponseEntity<FlickrPhotosGetExif> photosResponse
                = restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<FlickrPhotosGetExif>() {
                });
        return photosResponse.getBody();
    }

    public static FlickrPhotosGeoGetLocation flickrPhotosGeoGetLocation(String photoId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FLICKR_REST_URL)
                .queryParam("api_key", FLICKR_API_KEY)
                .queryParam("nojsoncallback", "1")
                .queryParam("format", "json")
                .queryParam("method", "flickr.photos.geo.getLocation")
                .queryParam("photo_id", photoId);
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        // Now deserialize/map the json data to our defined POJOs
        final ResponseEntity<FlickrPhotosGeoGetLocation> photosResponse
                = restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<FlickrPhotosGeoGetLocation>() {
                });
        return photosResponse.getBody();
    }

    public static PhotoInfo flickrPhotosGetPosition(String photoId) {
        final FlickrPhotosGeoGetLocation flickrPhotosGeoGetLocation = FlickrApi.flickrPhotosGeoGetLocation(photoId);
        if (FlickrApi.FLICKR_STAT_OK.equalsIgnoreCase(flickrPhotosGeoGetLocation.getStat())) {
            final PhotoInfo position = new PhotoInfo();
            position.setAccuracy(flickrPhotosGeoGetLocation.getPhoto().getLocation().getAccuracy());
            position.setLatitude(flickrPhotosGeoGetLocation.getPhoto().getLocation().getLatitude());
            position.setLongitude(flickrPhotosGeoGetLocation.getPhoto().getLocation().getLongitude());
            final FlickrPhotosGetExif flickrPhotosGetExif = flickrPhotosGetExif(photoId);
            if (null != flickrPhotosGetExif.getPhoto() && null != flickrPhotosGetExif.getPhoto().getExif()) {
                if (flickrPhotosGetExif.getPhoto().getExif().stream().filter((flickrExif) -> (Objects.equals("GPS", flickrExif.getTagspace()))).count() > 0) {
                    final Iterator<FlickrExif> gpsTagsIterator = flickrPhotosGetExif.getPhoto().getExif().stream().filter((flickrExif) -> (Objects.equals("GPS", flickrExif.getTagspace()))).iterator();
                    while (gpsTagsIterator.hasNext()) {
                        final FlickrExif next = gpsTagsIterator.next();
                        if ("GPSAltitude".equalsIgnoreCase(next.getTag())) {
                            position.setAltitude(next.getRaw().get_content());
                        } else if ("GPSAltitudeRef".equalsIgnoreCase(next.getTag())) {
                            position.setAltitudeRef(next.getRaw().get_content());
                        } else if ("GPSLongitude".equalsIgnoreCase(next.getTag())) {
                            position.setLongitude(next.getRaw().get_content());
                        } else if ("GPSLongitudeRef".equalsIgnoreCase(next.getTag())) {
                            position.setLongitudeRef(next.getRaw().get_content());
                        } else if ("GPSLatitude".equalsIgnoreCase(next.getTag())) {
                            position.setLatitude(next.getRaw().get_content());
                        } else if ("GPSLatitudeRef".equalsIgnoreCase(next.getTag())) {
                            position.setLatitudeRef(next.getRaw().get_content());
                        } else if ("GPSTimeStamp".equalsIgnoreCase(next.getTag())) {
                            position.setGpsTimeStamp(next.getRaw().get_content());
                        } else if ("GPSDateStamp".equalsIgnoreCase(next.getTag())) {
                            position.setGpsDateStamp(next.getRaw().get_content());
                        } else if ("GPSDOP".equalsIgnoreCase(next.getTag())) {
                            position.setGpsDilutionOfPrecision(next.getRaw().get_content());
                        }
                    }
                } 
                if (flickrPhotosGetExif.getPhoto().getExif().stream().filter((flickrExif) -> (Objects.equals("IFD0", flickrExif.getTagspace()))).count() > 0) {
                    final Iterator<FlickrExif> ifd0TagsIterator = flickrPhotosGetExif.getPhoto().getExif().stream().filter((flickrExif) -> (Objects.equals("IFD0", flickrExif.getTagspace()))).iterator();
                    while (ifd0TagsIterator.hasNext()) {
                        final FlickrExif next = ifd0TagsIterator.next();
                        if ("Make".equalsIgnoreCase(next.getTag())) {
                            position.setMaker(next.getRaw().get_content());
                        } else if ("Model".equalsIgnoreCase(next.getTag())) {
                            position.setModel(next.getRaw().get_content());
                        } else if ("Software".equalsIgnoreCase(next.getTag())) {
                            position.setSoftware(next.getRaw().get_content());
                        }
                    }
                }
            }
            return position;
        }
        return null;
    }
}
