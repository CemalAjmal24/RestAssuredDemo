package apitests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class ExtractionApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/api";
    }
    @Test
    public void testExtractIdFromCreatedUser(){
        User newUser = new User("Joe","consultant");
        Response response=given().contentType("application/json").body(newUser).when().post("/users").then().statusCode(201).extract().response();
        String generatedId = response.path("id");
        String createdAt = response.path("createdAt");
        System.out.println("New user's generated ID:"+ generatedId);
        System.out.println("Created at:"+createdAt);
        given().when().get("/users/2").then().statusCode(200);
    }
}
