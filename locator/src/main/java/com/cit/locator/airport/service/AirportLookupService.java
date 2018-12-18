package com.cit.locator.airport.service;

import com.cit.common.om.location.GeoLocation;
import com.cit.core.csv.ICSVReader;
import com.cit.locator.airport.om.Airport;
import com.cit.locator.distance.service.IDistanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by odziea on 12/18/2018.
 */
@Service
public class AirportLookupService implements IAirportLookupService {

    @Autowired
    ICSVReader csvReader;

    @Autowired
    IDistanceService distanceService;

    Logger logger = LoggerFactory.getLogger(AirportLookupService.class);

    private List<Airport> airports = new ArrayList<>();
    private Airport nearestAirport;

    private double lastShortestDistanceToAirport=0;

    @PostConstruct
    private void setupData() {
        try {
            File file = new ClassPathResource("airports.dat").getFile();
            airports = csvReader.loadObjectList(Airport.class, file);
        } catch (IOException e) {
            logger.error("Cannot load airport list: " + e.getMessage());
        }
    }

    @Override
    public Airport findNearestAirport(GeoLocation geoLocation){

        this.nearestAirport=null;
        this.lastShortestDistanceToAirport=0;

        this.airports.stream().forEach(airport -> {
            double distanceToAirport=this.distanceService.calculateDistanceInMeters(geoLocation, airport);

            if(lastShortestDistanceToAirport==0 || distanceToAirport<lastShortestDistanceToAirport){
                lastShortestDistanceToAirport = distanceToAirport;
                nearestAirport=airport;
            }
        });

        return nearestAirport;
    }
}
