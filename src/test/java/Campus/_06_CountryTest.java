package Campus;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;

import org.testng.annotations.*;
import io.restassured.specification.RequestSpecification;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.Matchers.equalTo;

public class _06_CountryTest {
    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    String countryName = "";
    String countryCode = "";
    String countryID = "";

    @BeforeClass
    public void LoginCampus() {
        baseURI = "https://test.mersys.io";

        String userCredential = "{\"username\": \"turkeyts\", \"password\": \"TechnoStudy123\",\"rememberMe\": \"true\"}";

        Map<String, String> userCredeMap = new HashMap<>();
        userCredeMap.put("username", "turkeyts");
        userCredeMap.put("password", "TechnoStudy123");
        userCredeMap.put("rememberMe", "true");

        Cookies gelenCookies =
                RestAssured.given()
                        // .body(userCredential)
                        .body(userCredeMap)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/auth/login")
                        .then()
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();
        ;

        //System.out.println("gelenCookies = " + gelenCookies);

        reqSpec = new RequestSpecBuilder()
                .addCookies(gelenCookies)
                .setContentType(ContentType.JSON)
                .build();


    }

    @Test
    public void CreateCountry() {
        //burada gelen tooken ın yine cookies içinde geri gitmesi lazım :spec
        countryName = randomUretici.address().country() + randomUretici.address().countryCode();
        countryCode = randomUretici.address().countryCode();

        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name", countryName);
        newCountry.put("code", countryCode);

        RestAssured.given()
                .spec(reqSpec)  // gelen cookies, yeni istek için login olduğumun kanıtı olarak gönderildi.
                .body(newCountry)
                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
        ;

    }

    @Test
    public void CreateCountryNegative() {
        //burada gelen tooken ın yine cookies içinde geri gitmesi lazım:spec

        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name",countryName);
        newCountry.put("code",countryCode);

        RestAssured.given()
                .spec(reqSpec)  // gelen cookies, yeni istek için login olduğumun kanıtı olarak gönderildi.
                .body(newCountry)
                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test
    public void UpdateCountry() {
        //burada gelen tooken ın yine cookies içinde geri gitmesi lazım:spec
        String updCountryName = "ismet" + randomUretici.address().country() +
                randomUretici.address().latitude();
        Map<String, String> updCountry = new HashMap<>();
        updCountry.put("id", countryID);
        updCountry.put("name", updCountryName);
        updCountry.put("code", countryCode);
        RestAssured.given()
                .spec(reqSpec)
                .body(updCountry)
                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(updCountryName)) //gönderdiğiniz ülke adının , dönen body deki ülke adıyla aynı
        ;
    }
@Test
    public void DeleteTest(){

    RestAssured.given()
            .when()
            .spec(reqSpec)
            .when()
            .delete("/school-service/api/countries/"+countryID)
            .then()
            .statusCode(200);
}

    @Test
    public void DeleteTestNegative(){

        RestAssured.given()
                .when()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/countries/"+countryID)
                .then()
                .statusCode(400);
    }

}
