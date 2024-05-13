import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;


public class _05_tekrar {
    @Test
    public void test05(){
        String postCode =
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .extract().path("'post code'")
                ;
        System.out.println("post code : "+postCode);
    }
    @Test
    public void jsonPath(){
        int postCode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post code'")
                ;
        System.out.println("post code : "+postCode);
    }
    @Test
    public void test_05(){
        Response response =
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .extract().response();
        Location locationAsPath = response.as(Location.class);
        System.out.println("locationAsPath.getPlaces() = " + locationAsPath.getPlaces());
        List<Place> place = response.jsonPath().getList("place", Place.class);
    }
}
