package io.chillplus;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.chillplus.model.TvShow;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class TvShowResourceTest {

//    @Test
//    public void testHelloEndpoint() {
//        given()
//          .when().get("/hello")
//          .then()
//             .statusCode(200)
//             .body(is("Hello RESTEasy"));
//    }

    @Test
    public void performTests() {

        // checkTvShowTitleIsNotBlank
        TvShow tvShow = new TvShow();
        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);

        // getAllTvShowsTest
        given()
            .when().get("/api/tv")
            .then()
                .statusCode(200)
                .body("$.size()", is(0));

        // createTvShow
        tvShow = new TvShow();
        tvShow.setTitle("A Space Odyssey 2001");
        given().body(tvShow)
            .contentType(APPLICATION_JSON)
        .when()
            .post("/api/tv")
        .then()
            .statusCode(201);

        //  Verify TV Show was created
        given()
                .when().get("/api/tv")
                .then()
                .statusCode(200)
                .body("$.size()", is(1));

        tvShow.setId(42L);
        given().body(tvShow)
                .contentType(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);

        // createTvShow
        tvShow = new TvShow();
        tvShow.setTitle("Hitchhikers guide to the galaxy");
        given().body(tvShow)
                .contentType(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201);

        //  Verify TV Show was created
        given()
                .when().get("/api/tv")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));

        List shows = given()
                .when().get("/api/tv")
                .then()
                .contentType(APPLICATION_JSON)
                .extract()
                .as(List.class);
        assertTrue(shows.size() > 0);

        Integer id = (Integer)((LinkedHashMap)shows.get(0)).get("id");

//      Step 7 and 8 cannot be implemented as no GET /api/tv/{id} was implemented
//        given()
//                .when().get("/api/tv/" + id)
//                .then()
//                .statusCode(200);


        //  Delete specific resource
        given().when().delete("/api/tv/" + id)
                .then()
                .statusCode(200);

        //  Delete all entries at the resource
        given().when().delete("/api/tv").then().statusCode(200);

        given()
                .when().get("/api/tv")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

}