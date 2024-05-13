package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;

public class proje {
    RequestSpecification rqspac;
    @BeforeClass
    public void _5_(){
        baseURI ="https://www.themoviedb.org/login";
        ///////////////////*****************************
      // String userCredential = "{\"username\": \"turkeyts\", \"password\": \"TechnoStudy123\",\"rememberMe\": \"true\"}";

      // Map<String, String> userCredMap = new HashMap<>();
      // userCredMap.put("username", "turkeyts");
      // userCredMap.put("password", "TechnoStudy123");
      // userCredMap.put("rememberMe", "true");

      // Cookies gelenCookies =
      //         given()
      //                 //.body(userCredential)
      //                 .body(userCredMap)
      //                 .contentType(ContentType.JSON)
      //                 .when()
      //                 .post("/auth/login")
      //                 .then()
      //                 //.log().all()
      //                 .statusCode(200)
      //                 .extract().response().getDetailedCookies();
        //System.out.println("gelenCookies = " + gelenCookies);
        ///////////////////////*******************************************
        rqspac = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZTYwMzVkMDgzMjk5MmUxYzg2Njk5N2ZjY2FmYmNhZCIsInN1YiI6IjY2M2E5MTA0MTkxZTBmOGMxNjlhOTgyZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JNv8SBrefipTxYIcT_QIr5UuxAh3iIbPPZZ5vmjlns4")
                //.addHeader("Cookie","tmdb.prefs=%7B%22adult%22%3Afalse%2C%22i18n_fallback_language%22%3A%22en-US%22%2C%22locale%22%3A%22tr%22%2C%22country_code%22%3A%22TR%22%2C%22timezone%22%3A%22Africa%2FNairobi%22%7D; tmdb.session=AUUx9zNRBp16_yY71kx_ux0xruR5_0z7kwgk7kyakaUsf0o-5O59NqDfbzqeES276UEacRc2KJHh4eVgdDPTcsno67-wjZnGXcNTkvobw0wx2FIefGyFFzeOK_sMAa7GEq42WxfUqkLaAbuWQYhpgL8oSDAFT9x2Rkmb67_kmP9iSJADDsIyU5eettIq0SpunY0Z6VV4GlDjiQ5oxctKXxRXeUMhwZSRCDVo22H1_XApDPQYq1OGj3t7hQAWJ3S9R96KzD8tGYoebYwuv3LRBiYnGZneIDdAKcBNFjRfbHutIQo6myfz510lzCie_TRntjYogJODNDXcIEBgbYHPZmeQMmYOO456dSUU9NDZUwg5G4-AuGOrjfaxjLK0Wy4CT-9WkvR_cCQExXx9P2bQxQIiA82hJVM2lRF9YtTYzOvK7qNjuUTMXuw4wG7jEbF1A3O30oJAQifOcZtDhPclbpk8CNj2uMfd9k_0zYOuk_FyMt4XONcICgYD59OwV1cVHElK0Eeya1lESl48TOdr52Wl2eKbYmExlkq_UT78tltp1vDlM9wCYLPdKsVHdGwvAC7ikDLulYfaj6yZm73t90665O4xQNZhPIwn7m5cO9QVhtR5V3HAjE_EGfkUzl2CzcHhAUSu5509kGR-UfKUX9IMY__sZKBTIfzo7oDbfcy12xmgURwRNsepsH7YkiE4Wg%3D%3D")
                .setContentType(ContentType.JSON).build();
    }
    @Test
    public void Login(){
        Map<String,String>login = new HashMap<>();
        login.put("name","alicandan");
        login.put("password","4321");
        given()
                .spec(rqspac)
                .body( login ).contentType(ContentType.JSON)
                .when()
                .post("")
                .then()
                .log().body()
                .statusCode(200)
                ;

    }
}
