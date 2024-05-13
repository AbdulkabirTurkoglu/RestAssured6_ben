import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class _05_PathAndJsonPath {
    @Test
    public void extractingPath() {
        String postCode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().path("'post code'");

        System.out.println("postCode = " + postCode);
        int postCodeInt = Integer.parseInt(postCode);
        System.out.println("postCodeInt = " + postCodeInt);
    }

        @Test
        public void extractingJsonPath(){
            int postCode=
                    given()
                            .when()
                            .get("http://api.zippopotam.us/us/90210")
                            .then()
                            .log().body()
                            .extract().jsonPath().getInt("'post code'");

            System.out.println("postCode = " + postCode);
        }

    @Test
    public void getZipCode(){
        Response response =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().response();
        Location locatioAsPath= response.as(Location.class);
        System.out.println("locatioAsPath.getPlaces() = " + locatioAsPath.getPlaces());

        List<Place> places = response.jsonPath().getList("place", Place.class);

    }

    @Test
    public void ZipCode(){
        // https://gorest.co.in/public/v1/users  endpointte dönen Sadece Data Kısmını POJO
        // dönüşümü ile alarak yazdırınız.
        Response response =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log().body()
                        .extract().response();
        Location locatioAsPath= response.as(Location.class);
        System.out.println("locatioAsPath.getPlaces() = " + locatioAsPath.getPlaces());

        List<Place> places = response.jsonPath().getList("place", Place.class);

    }


}
