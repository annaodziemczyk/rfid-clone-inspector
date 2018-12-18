package com.cit.locator.panellocator.uuidpanellocator;

import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.core.rest.RestTemplateService;
import com.cit.locator.panellocator.uuidpanellocator.config.UuidLocatorUri;
import com.cit.locator.panellocator.uuidpanellocator.dto.UuidLocatorLocationDto;
import com.cit.locator.panellocator.uuidpanellocator.mapper.UuidLocatorLocationMapper;
import com.sun.javafx.fxml.builder.URLBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by odziea on 11/18/2018.
 */
@Service
public class UuidPanelLocatorService extends RestTemplateService implements IUuidPanelLocatorService {

    Logger logger = LoggerFactory.getLogger(UuidPanelLocatorService.class);

    @Autowired
    private UuidLocatorUri uuidLocatorUri;

    @Autowired
    UuidLocatorLocationMapper uuidLocatorLocationMapper;

    Map<String, RfidReaderPanel>panels=new HashMap<>();

    public RfidReaderPanel findPanelById(String panelId){

        RfidReaderPanel rfidReaderPanel = new RfidReaderPanel();

        if(this.panels.containsKey(panelId)){
            return this.panels.get(panelId);
        }else{
            try {
                URIBuilder urlBuilder = new URIBuilder(this.getRootUri() + uuidLocatorUri.getPanels()+ panelId);

                UuidLocatorLocationDto uuidLocatorLocationDto = (UuidLocatorLocationDto)this.getForObject(urlBuilder, UuidLocatorLocationDto.class);
                rfidReaderPanel = uuidLocatorLocationMapper.dtoToDomain(uuidLocatorLocationDto);
                rfidReaderPanel.setId(panelId);
                this.panels.put(panelId, rfidReaderPanel);
            } catch (URISyntaxException e) {
                logger.error(e.getMessage());
            } catch (MalformedURLException e) {
                logger.error(e.getMessage());
            }
        }

        return rfidReaderPanel;
    }

    @Override
    public String getRootUri() {
        return uuidLocatorUri.getRoot();
    }
}
