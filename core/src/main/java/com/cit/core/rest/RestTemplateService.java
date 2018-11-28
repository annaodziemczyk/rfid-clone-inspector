package com.cit.core.rest;

import com.sun.javafx.fxml.builder.URLBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by odziea on 11/18/2018.
 */
public abstract class RestTemplateService implements IRestTemplateService {

    private RestTemplate restTemplate = new RestTemplate();

    protected Object getForObject(URIBuilder uriBuilder, Class clazz) throws URISyntaxException, MalformedURLException {

        if(uriBuilder.getPath().isEmpty()){
            uriBuilder.setPath(this.getRootUri());
        }

        return this.restTemplate.getForObject(uriBuilder.build().toURL().toString(), clazz);
    }

}
