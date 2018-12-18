package com.cit.locator.airport.om;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by odziea on 12/18/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

    private int airportId;
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private String iataCode;
    private String icaoCode;
    private double latitude;
    private double longitude;
    private int altitude;
    private double utcOffset;
    private String dst;
    private String tzDatabaseTimeZone;
    private String type;
    private String source;

    public Airport() {
    }

    public Airport(int airportId, String airportName, String airportCity, String airportCountry, String iataCode, String icaoCode, double latitude, double longitude, int altitude, double utcOffset, String dst, String tzDatabaseTimeZone, String type, String source) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.utcOffset = utcOffset;
        this.dst = dst;
        this.tzDatabaseTimeZone = tzDatabaseTimeZone;
        this.type = type;
        this.source = source;
    }

    public int getAirportId() {
        return airportId;
    }

    public void setAirportId(int airportId) {
        this.airportId = airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public void setAirportCountry(String airportCountry) {
        this.airportCountry = airportCountry;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public double getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(double utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getTzDatabaseTimeZone() {
        return tzDatabaseTimeZone;
    }

    public void setTzDatabaseTimeZone(String tzDatabaseTimeZone) {
        this.tzDatabaseTimeZone = tzDatabaseTimeZone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
