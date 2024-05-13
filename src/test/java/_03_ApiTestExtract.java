import io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class _03_ApiTestExtract {
    @Test
    public void extractingJsonPath_1() {
        String UlkeAdi =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("country");
        System.out.println("ulkeadi = " + UlkeAdi);
        Assert.assertEquals(UlkeAdi, "United States");
    }

    @Test
    public void extractingJsonPath_2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız

        String stateName=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("places[0].state")
                ;
        System.out.println("stateName = " + stateName);
        Assert.assertEquals(stateName, "California");
    }
    @Test
    public void extractingJsonPath_3(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
        // olduğunu testNG Assertion ile doğrulayınız
        String placeName =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().path("places[0].'place name'");
                        Assert. assertEquals(placeName,"Beverly Hills");



    }

    @Test
    public void test_4() {
        Integer limit =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log().body()
                        .extract().path("meta.pagination.limit");
        Assert.assertTrue(limit==10);
    }
    @Test
    public void test_5() {
        ArrayList<Integer> idler=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log(). body()
                        .extract().path("data.id");
        System.out.println("idler = " + idler);
    }
    @Test
    public void test_6() {
       ArrayList<String> names =
               given()
                       .when()
                       .get("https://gorest.co.in/public/v1/users")
                       .then()
                       .log().body()
                       .extract().path("data.name");
        System.out.println("names = " + names);
    }

    @Test
    public void extractingJsonPath_all() {
        Response donenData =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log(). body()
                        .extract().response();

        List<Integer> idler = donenData.path("data.id");
        List<String> names = donenData.path("data.name");
        int limit = donenData.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(idler.contains(6897437));
        Assert.assertTrue(names.contains("Dharitri Mishra DDS"));
        Assert.assertTrue(limit==10);

    }
}
