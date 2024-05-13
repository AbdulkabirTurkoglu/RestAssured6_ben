package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _06_Tekrar {
    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    String countryName = "";
    String countryCode = "";
    String countryID = "";


    @BeforeClass
    public void LginCampus() {
        baseURI = "https://test.mersys.io";
        String userCredential = "{\"username\": \"turkeyts\", \"password\": \"TechnoStudy123\",\"rememberMe\": \"true\"}";

        Map<String, String> userCredMap = new HashMap<>();
        userCredMap.put("username", "turkeyts");
        userCredMap.put("password", "TechnoStudy123");
        userCredMap.put("rememberMe", "true");

        Cookies gelenCookies =
                given()
                        .body(userCredMap).contentType(ContentType.JSON)
                        .when()
                        .post("/auth/login")
                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();
        reqSpec = new RequestSpecBuilder().addCookies(gelenCookies)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void CreateCountry() {
        countryName = randomUretici.address().country() + randomUretici.address().countryCode();
        countryCode = randomUretici.address().countryCode();

        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name", countryName);
        newCountry.put("code", countryCode);

        countryID =
                given()
                        .spec(reqSpec)
                        .body(newCountry)

                        .when()
                        .post("/school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "CreateCountry")
    public void CreateCountryNegative() {
        // Yukarıda gönderilen name ve codu tekrar göndererek kayıt yapılamadığını doğrulayınız.
        //burada gelen tooken ın yine cookies içinde geri gitmesi lazım:spec
        Map<String, String> newReCountry = new HashMap<>();
        newReCountry.put("name", countryName);
        newReCountry.put("code", countryCode);
        given()
                .spec(reqSpec)
                .body(newReCountry)

                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "CreateCountryNegative")
    public void updateCountry() {
        String updateCountryName = "update" + randomUretici.country().countryCode2();

        Map<String, String> updateCountry = new HashMap<>();
        updateCountry.put("id", countryID);
        updateCountry.put("name", updateCountryName);
        updateCountry.put("code", countryCode);

        given()
                .spec(reqSpec)
                .body(updateCountry)
                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(updateCountryName));
        ;
    }
    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry(){
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/countries/"+countryID)
                .then()
                .statusCode(200)
                ;
    }
    @Test
    public void DeleteNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/countries/"+countryID)
                .then()
                .log().body()
                .statusCode(404)
                ;
    }

}
