package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;



public class _07_GoRestUsersTest {
    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    int userID= 0;
    @BeforeClass
    public void setUp(){
        baseURI="https://gorest.co.in/public/v2/users";

         reqSpec= new RequestSpecBuilder()
                 .addHeader("Authorization", "Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1")
                 .setContentType(ContentType.JSON)
                 .build();
    }
    @Test
    public void Creatuser(){
        String rnFullName = randomUretici.name().fullName();
        String rndEmail = randomUretici.internet().emailAddress();

        Map<String,String> newUsers = new HashMap<>();
        newUsers.put("name", rnFullName);
        newUsers.put("gender","male");
        newUsers.put("email", rndEmail);
        newUsers.put("status","active");

        userID =
        given()
                .spec(reqSpec)
                .body(newUsers)

                .when()
                .post("")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;

    }
    @Test(dependsOnMethods = "Creatuser")
    public void getUserById(){

        given()
                .spec(reqSpec)
                .when()
                .get("/"+userID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                ;
    }

    @Test(dependsOnMethods = "getUserById")
    public void updateUser(){
        String updateName = "alicandan";
        Map<String,String> updateUser = new HashMap<>();
        updateUser.put("name", updateName);

        given()
                .spec(reqSpec)
                .body(updateUser)

                .when()
                .put("/"+userID)

                .then()
                .statusCode(200)
                .log().body()
                .body("id", equalTo(userID))
                .body("name",equalTo(updateName))
        ;
    }
    @Test(dependsOnMethods = "updateUser")
    public void deleteUser(){

        given()
                .spec(reqSpec)

                .when()
                .delete("/"+userID)

                .then()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative(){

        given()
                .spec(reqSpec)

                .when()
                .delete("/"+userID)

                .then()
                .statusCode(404)
        ;
    }
}
