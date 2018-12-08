package com.cit.clonedetection.mapper;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.common.om.access.device.TokenReader;
import com.cit.common.om.access.request.AccessRequest;
import com.cit.common.om.access.token.RfidBadge;
import com.cit.common.om.location.Building;
import com.cit.common.om.location.GeoLocation;
import com.cit.notifier.dto.AccessEventDto;
import com.cit.notifier.dto.CloneDetectionAlertDto;
import com.cit.notifier.dto.CoordinatesDto;
import com.cit.notifier.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.time.ZonedDateTime;

/**
 * Created by odziea on 12/8/2018.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CloneDetectionAlertMapper {

    @Mappings({
            @Mapping(target = "severity", ignore = true),
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "description", ignore = true),
            @Mapping(source = "accessRequest", target = "currentEvent"),
            @Mapping(source = "previousAccessRequest", target = "previousEvent")
    })
    CloneDetectionAlertDto domainToAlertDto(final CloneDetectionResult cloneDetectionResult);

    @Mappings({
            @Mapping(source = "accessIssuer", target = "panelId"),
            @Mapping(source = "accessToken.tokenId", target = "cardId"),
            @Mapping(source = "accessTime", target = "timestamp"),
            @Mapping(source = "accessIssuer", target = "location"),
            @Mapping(source = "requestGranted", target = "accessAllowed")
    })
    AccessEventDto accessRequestToAccessEventDto(AccessRequest<RfidBadge, RfidReaderPanel> accessRequest);

    default String accessIssuerToPanelId(TokenReader accessIssuer){
        return accessIssuer.getId();
    }

    default LocationDto accessIssuerToLocationDto(TokenReader accessIssuer){
        LocationDto locationDto = new LocationDto();

        GeoLocation geoLocation = accessIssuer.getGeoLocation();
        CoordinatesDto coordinatesDto = new CoordinatesDto();
        coordinatesDto.setLatitude(geoLocation.getX());
        coordinatesDto.setLongitude(geoLocation.getY());

        locationDto.setCoordinates(coordinatesDto);
        locationDto.setAltitude((int)geoLocation.getZ());
         if(accessIssuer instanceof RfidReaderPanel){
             Building building = ((RfidReaderPanel) accessIssuer).getBuilding();
             locationDto.setRelativeLocation(building.getAddress().toString());
         }

        return locationDto;
    }

    default long asLong(ZonedDateTime zonedDateTime){

        return zonedDateTime.toInstant().toEpochMilli();
    }
}
