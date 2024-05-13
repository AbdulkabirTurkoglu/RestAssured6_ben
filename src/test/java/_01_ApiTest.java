import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;

public class _01_ApiTest {

    @Test
    public void Test1() {
        given()

                .when()

                .then()
        ;
    }

    @Test
    public void statusCodeTest() {
        given()
                // gonderilicek bilgiler buraya girilecek
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // gelen body kismini goster
                .statusCode(200);  // test kismi assertion status 200 mu;
        ;
    }

   @Test
   public void contentTypeTest() {
       given()
               // gonderilicek bilgiler buraya girilecek
               .when()
               .get("http://api.zippopotam.us/us/90210")

               .then()
               .log().body() // gelen body kismini goster
               .statusCode(200)  // test kismi assertion status 200 mu;
               .contentType(ContentType.JSON) // donene datanin tipi JSON mi ?
       ;
   }
    @Test
    public void checkCountryInResponseBody() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .body("country", equalTo("United States"))
                ;
    }

    @Test
    public void checkCountryInResponseBody__Soru_2() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .body("places[0].state", equalTo("California"))
        ;
    }

    @Test
    public void test_02(){

        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .log().body()
                .statusCode(200)
                .body("places.'place name'", hasItem("Dörtağaç Köyü")) //**** hasItem = contains  *****
        ;
    }
    @Test
    public void test_03(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(1)) //**** place in eleman uzunlugu 1 mi  *****
        ;
    }

    @Test
    public void test_04(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places[0].state", equalTo("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))

        ;
    }

    @Test
    public void pathParamTest(){
        given()
                .pathParams("ulke","us")
                .pathParams("postaKodu", 90210)
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKodu}")

                .then()
                .log().body()
        ;
    }
    @Test
    public void queryParamTest(){
        given()
                .param("page",1)
                .log().uri()
                .when()
                .get("http://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
        ;
    }

    @Test
    public void queryParamTest2() {
        for (int i = 1; i <= 10; i++) {
            given()
                    .param("page", i)
                    .log().uri()
                    .when()
                    .get("http://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(i))
            ;
        }
    }

}
