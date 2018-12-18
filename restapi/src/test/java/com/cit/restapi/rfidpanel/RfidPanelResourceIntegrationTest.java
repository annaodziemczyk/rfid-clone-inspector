package com.cit.restapi.rfidpanel;


import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by odziea on 12/8/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IfProfileValue(name ="spring.profiles.active", value ="integration-test")
//@ActiveProfiles("integration-test")
public class RfidPanelResourceIntegrationTest {

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(
            WireMockSpring.options().dynamicPort());

    @LocalServerPort
    private int port;

    private final String VALID_EVENT_REASON = "Possible time-distance event.";
    private final String PANEL_ID_LOCATION_A="5e11d5ee-7715-4080-bfe6-25c66d8ce821";
    private final String PANEL_ID_LOCATION_A_DIFFERENT_FLOOR="5ae6dbcd-9166-4d80-99d9-069e69bead15";
    private final String PANEL_ID_LOCATION_A_DIFFERENT_BUILDING="287d07d6-e243-4947-807d-74ec80f57427";
    private final String PANEL_ID_LOCATION_A_DIFFERENT_CITY="066e6fd3-49a4-4e08-8793-eb87bd323408";
    private final String PANEL_ID_LOCATION_B="7907775e-15ac-415f-a99c-e978856c8ec0";
    private final String PANEL_ID_LOCATION_B_SAME_CITY="1a9c03a2-1ab7-4879-8d28-a4ea76e1da5b";
    private final String PANEL_ID_LOCATION_B_DIFFERENT_COUNTRY="a35c7873-b875-4685-91ac-188244509a03";

    private final String PANEL_DIFFERENT_COUNTRY="";


    @Before
    public void before(){

//        wiremock.stubFor(get(urlPathMatching("/api/panels/"+PANEL_ID_LOCATION_B))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withBodyFile("locator-panel-5e11d5ee-7715-4080-bfe6-25c66d8ce821.json")));


    }

    @Test
    public void whenNoPreviousAccessRequest_thenValidEvent(){

        final String cardId = "bb";

        issueAccessRequest(PANEL_ID_LOCATION_B, cardId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("validEvent", is(true))
                .body("reason", Matchers.equalTo( VALID_EVENT_REASON))
                .body("currentEvent", notNullValue())
                .body("previousEvent", nullValue());

//        verify(1, getRequestedFor(urlPathMatching("/api/panels/"+PANEL_ID_LOCATION_B)));
    }

    private Response issueAccessRequest(String panelId, String cardId){
        return     given()
                        .port(port)
                        .header("Content-Type", ContentType.JSON)
                        .accept(ContentType.JSON)
                        .queryParam("panelId", panelId)
                        .queryParam("cardId", cardId)
                        .queryParam("allowed", true)
                        .when().
                                put("/api/panels/request");
    }

    @Test
    public void whenSameTimeSameCardSamePanel_thenValidEvent(){

        final String cardId = "cc";

        issueAccessRequest(PANEL_ID_LOCATION_A, cardId)
                .then()
                .assertThat()
                .statusCode(200)

                .body("currentEvent", notNullValue())
                .body("previousEvent", nullValue());

        issueAccessRequest(PANEL_ID_LOCATION_A, cardId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("validEvent", is(true))
                .body("reason", Matchers.equalTo( VALID_EVENT_REASON))
                .body("currentEvent", notNullValue())
                .body("previousEvent", notNullValue());
    }

    @Test
    public void whenSameTimeSameCardDifferentFloors_thenInvalidEvent(){

        final String cardId = "dd";

        issueAccessRequest(PANEL_ID_LOCATION_A, cardId)
                .then()
                .assertThat()
                .statusCode(200)

                .body("currentEvent", notNullValue())
                .body("previousEvent", nullValue());

        issueAccessRequest(PANEL_ID_LOCATION_A_DIFFERENT_FLOOR, cardId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("validEvent", is(false))
                .body("currentEvent", notNullValue())
                .body("previousEvent", notNullValue());
    }

    @Test
    public void whenSameTimeSameCardDifferentBuildings_thenInvalidEvent(){

        final String cardId = "ee";

        issueAccessRequest(PANEL_ID_LOCATION_A, cardId)
                .then()
                .assertThat()
                .statusCode(200)

                .body("currentEvent", notNullValue())
                .body("previousEvent", nullValue());

        issueAccessRequest(PANEL_ID_LOCATION_A_DIFFERENT_BUILDING, cardId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("validEvent", is(false))
                .body("currentEvent", notNullValue())
                .body("previousEvent", notNullValue());
    }

    @Test
    public void whenSameTimeSameCardDifferentCityLocation_thenInvalidEvent(){

        final String cardId = "ff";

        issueAccessRequest(PANEL_ID_LOCATION_B, cardId)
                .then()
                .assertThat()
                .statusCode(200)

                .body("currentEvent", notNullValue())
                .body("previousEvent", nullValue());

        issueAccessRequest(PANEL_ID_LOCATION_B_SAME_CITY, cardId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("validEvent", is(false))
                .body("currentEvent", notNullValue())
                .body("previousEvent", notNullValue());
    }

    @Test
    public void whenSameTimeSameCardDifferentCities_thenInvalidEvent(){

        final String cardId = "gg";

        issueAccessRequest(PANEL_ID_LOCATION_A, cardId)
                .then()
                .assertThat()
                .statusCode(200)

                .body("currentEvent", notNullValue())
                .body("previousEvent", nullValue());

        issueAccessRequest(PANEL_ID_LOCATION_A_DIFFERENT_CITY, cardId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("validEvent", is(false))
                .body("currentEvent", notNullValue())
                .body("previousEvent", notNullValue());
    }
    @Test
    public void whenSameTimeSameCardDifferentCountry_thenInvalidEvent(){

        final String cardId = "hh";

        issueAccessRequest(PANEL_ID_LOCATION_B, cardId)
                .then()
                .assertThat()
                .statusCode(200)

                .body("currentEvent", notNullValue())
                .body("previousEvent", nullValue());

        issueAccessRequest(PANEL_ID_LOCATION_B_DIFFERENT_COUNTRY, cardId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("validEvent", is(false))
                .body("currentEvent", notNullValue())
                .body("previousEvent", notNullValue());
    }
}
