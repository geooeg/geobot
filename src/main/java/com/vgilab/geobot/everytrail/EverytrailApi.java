package com.vgilab.geobot.everytrail;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author smuellner
 */
public class EverytrailApi {

    public static String EVERYTRAIL_KEY = "";
    public static String EVERYTRAIL_SECRET = "";
    public static String EVERYTRAIL_API_URL = "www.everytrail.com/api/";
    public static String EVERYTRAIL_TRIP_SEARCH = "trip/search";
    public static String EVERYTRAIL_TRIP_TRACKS = "trip/tracks";
    
    public static String evertrailUrl(String request) {
        return "http://" + EVERYTRAIL_API_URL + request;
    }

    public static EtTripSearchResponse searchTrails(double latitude, double longitude, int proximity) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EverytrailApi.evertrailUrl(EVERYTRAIL_TRIP_SEARCH))
                .queryParam("lat", String.format(Locale.ENGLISH, "%.2f", latitude))
                .queryParam("lon", String.format(Locale.ENGLISH, "%.2f", longitude))
                .queryParam("proximity", String.format(Locale.ENGLISH, "%d", proximity))
                .queryParam("limit", "500");
        final HttpEntity<?> entity = new HttpEntity<>(getHeaders());
        // Now deserialize/map the json data to our defined POJOs
        final ResponseEntity<EtTripSearchResponse> etTripSearchResponse
                = getRestTemplate().exchange(builder.build().encode().toUri(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<EtTripSearchResponse>() {
                });
        return etTripSearchResponse.getBody();
    }
    
    public static HttpEntity getTrip(int tripId) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EverytrailApi.evertrailUrl(EVERYTRAIL_TRIP_TRACKS))
                 .queryParam("trip_id", String.format("%d", tripId));
        final HttpEntity<?> entity = new HttpEntity<>(getHeaders());
        // lets just get the plain response as a string
        final HttpEntity response = getRestTemplate().exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        return response;
    }
    
    private static HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        final String plainCreds = EVERYTRAIL_KEY + ":" + EVERYTRAIL_SECRET;
        final byte[] plainCredsBytes = plainCreds.getBytes();
        final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        final String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }
    
    private static RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        final List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                final MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(new ObjectMapper());
                final MediaType types[] = new MediaType[]{MediaType.TEXT_HTML, MediaType.APPLICATION_XML};
                headers.setAccept(Arrays.asList(types));
                jsonConverter.setSupportedMediaTypes(Arrays.asList(types));
            }
        }
        return restTemplate;
    }

}
