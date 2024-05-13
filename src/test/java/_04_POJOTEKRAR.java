import Model.Location;
import Model.Place;
import Model.ToDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _04_POJOTEKRAR {
    @Test
    public void extractJson_pojo() {
        Location locationNesnesi =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().body().as(Location.class);
        System.out.println("locationNesnesi = " + locationNesnesi);
        System.out.println("locationNesnesi.getPostcode() = " + locationNesnesi.getPostcode());
        System.out.println("locationNesnesi.getPlaces() = " + locationNesnesi.getPlaces());
    }

    @Test
    public void pojo_02() {
        Location gelenNesne =
                given()
                        .when()
                        .get("http://api.zippopotam.us/tr/01000")
                        .then()
                        .extract().body().as(Location.class);
        for (Place p : gelenNesne.getPlaces()) {
            if (p.getPlacename().equalsIgnoreCase("Camuzcu Köyü"))
                System.out.println("p = " + p);

        }
    }

    /**
     * Task 1
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */
    @Test
    public void task_1() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"));
    }

    /**
     * Task 2
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * a) expect response completed status to be false(hamcrest)
     * b) extract completed field and testNG assertion(testNG)
     */
    @Test
    public void task_2a() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false));
    }
    @Test
    public void task_2b() {
        boolean complatedData =
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().path("completed");
        Assert.assertFalse(complatedData);
    }


    @Test
    public void Task3(){
        /** Task 3
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * Converting Into POJO body data and write
         */
        ToDo ToDoNames = 
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().as(ToDo.class)
                ;
        System.out.println("ToDoNames = " + ToDoNames);
        System.out.println("ToDoNames.getId() = " + ToDoNames.getId());
        System.out.println("ToDoNames.getTitle() = " + ToDoNames.getTitle());
        System.out.println("ToDoNames.getUserId() = " + ToDoNames.getUserId());

    }
}
