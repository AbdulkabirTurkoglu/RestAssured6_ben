import Model.Location;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class _04_ApiTestPOJO {
    @Test
    public void extractJsonAll_POJO() {
        Location locationNesnesi =
        given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().body().as(Location.class);


        System.out.println("locationNesnesi = " + locationNesnesi);
        System.out.println("locationNesnesi.getPlaces() = " + locationNesnesi.getPlaces());
        System.out.println("locationNesnesi.getPostcode() = " + locationNesnesi.getPostcode());
    }
    // Soru  : https://gorest.co.in/public/v1/users  endpointte dönen Sadece Data Kısmını POJO
    // dönüşümü ile alarak yazdırınız.
    @Test
    public void getUserV1(){
        Location locationSoru =
        given()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                .log().body()
                .extract().body().as(Location.class);;
        System.out.println("locationSoru = " + locationSoru);

    }
}
