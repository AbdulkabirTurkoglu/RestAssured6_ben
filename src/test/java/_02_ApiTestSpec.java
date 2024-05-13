import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class _02_ApiTestSpec {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    @BeforeClass
    public void Setup(){
        baseURI="https://gorest.co.in/public/v1";

        requestSpec = new RequestSpecBuilder()  // given
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI) // log().uri()
                .build();

        responseSpec = new ResponseSpecBuilder()  // then
                .expectStatusCode(200)  // statusCode(200)
                .log(LogDetail.BODY)  // log().body()
                .expectContentType(ContentType.JSON)
                .build();
    }
    @Test
    public void Test_01(){
        given()
                .spec(requestSpec)
                .when()
                .get("users")
                .then()
                .spec(responseSpec);

    }

}
