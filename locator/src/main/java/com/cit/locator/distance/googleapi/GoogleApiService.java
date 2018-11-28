package com.cit.locator.distance.googleapi;

import com.cit.common.om.location.GeoLocation;
import com.cit.core.rest.RestTemplateService;
import com.cit.locator.distance.googleapi.config.GoogleApiConfig;
import com.cit.locator.distance.googleapi.dto.GoogleResponseDTO;
import com.cit.locator.distance.googleapi.mapper.GoogleApiMapper;
import com.cit.locator.distance.om.Distance;
import com.cit.locator.distance.om.TravelMeans;
import com.sun.javafx.fxml.builder.URLBuilder;
import jdk.nashorn.internal.runtime.URIUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

@Service
public class GoogleApiService extends RestTemplateService implements IGoogleApiService {

    @Autowired
    private GoogleApiConfig googleApiConfig;

    @Autowired
    private GoogleApiMapper googleApiMapper;

    public String getRootUri(){
        return this.googleApiConfig.getBaseUri();
    }

    public URIBuilder getRequestURL(GeoLocation current, GeoLocation previous, TravelMeans mode) throws URISyntaxException {

        mode = TravelMeans.driving;
        URIBuilder uriBuilder = new URIBuilder(this.getRootUri() + this.googleApiConfig.getDistancematrix());

        uriBuilder.setParameter("units", this.googleApiConfig.getUnits());
        String origin = String.format("%s,%s",current.getX(),current.getY());
        uriBuilder.setParameter("origins", origin);
        String destination = String.format("%s,%s",previous.getX(),previous.getY());
        uriBuilder.setParameter("destinations", destination);
        uriBuilder.setParameter("key", this.googleApiConfig.getKey());
        uriBuilder.setParameter("mode", mode.toString());

        return uriBuilder;
    }


    public Distance execute(GeoLocation current, GeoLocation previous, TravelMeans travelMeans ) {


        GoogleResponseDTO result = null;
        Distance distance = new Distance();
        try {
            result = (GoogleResponseDTO)this.getForObject(this.getRequestURL(current, previous, travelMeans), GoogleResponseDTO.class);
            if(result.getStatus().equalsIgnoreCase("OK") && !result.getRows().get(0).getElements().get(0).getStatus().equalsIgnoreCase("ZERO_RESULTS")){
                distance = googleApiMapper.googleResponseDtoToDistance(result);
            }else if(result.getStatus().equals("REQUEST_DENIED")){
                throw new IllegalArgumentException("GoogleDistanceService REQUEST_DENIED ");

            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return distance;

    }

}