package GoRest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;


import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _07_Tekrar {
    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    int userID = 0;

    @BeforeClass
    public void setUp() {
        baseURI = "https://gorest.co.in/public/v2/users";
        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer 6be16d2ad71a2ff92b1b7d2d2d202796479e2cca3b8a2e9c835702c124b57793")
                .setContentType(ContentType.JSON).build();
    }

    @Test
    public void creatUsers() {
        String rnName = randomUretici.name().fullName();
        String rnEmail = randomUretici.internet().emailAddress();

        Map<String, String> creatUser = new HashMap<>();
        creatUser.put("name", rnName);
        creatUser.put("gender", "male");
        creatUser.put("email", rnEmail);
        creatUser.put("status", "active");

        userID =
                given()
                        .spec(reqSpec)
                        .body(creatUser)

                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "creatUsers")
    public void getUserByID() {
        given()
                .spec(reqSpec)
                .when()
                .get("/" + userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
        ;
    }

    @Test(dependsOnMethods = "getUserByID")
    public void update() {
        String updateName = "yolvas" + randomUretici.name();

        Map<String, String> neweName = new HashMap<>();
        neweName.put("name", updateName);
        given()
                .spec(reqSpec)
                .body(neweName)
                .when()
                .put("/" + userID)
                .then()
                .log().body()
                .statusCode(200)
        // .body("id",equalTo(userID))
        // .body("name",equalTo(neweName))
        ;
    }

    @Test(dependsOnMethods = "update")
    public void delete() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/" + userID)
                .then()
                .statusCode(204);
    }

    @Test(dependsOnMethods = "delete")
    public void deleteNegative() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/" + userID)
                .then()
                .statusCode(404);

    }
}















