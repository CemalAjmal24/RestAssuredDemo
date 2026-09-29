package apitests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class LoggingApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/api";
    }
    @Test
    public void testGetUserWithConditionalLogging(){
        given().log().ifValidationFails().when().get("/users/2").then().log().ifValidationFails().statusCode(200).body("data.id",equalTo(2));
    }
    @Test
    public void testDeliberatelyFailingRequest(){
        given().log().ifValidationFails().when().get("/users/2").then().log().ifValidationFails().statusCode(200).body("data.id",equalTo(999));
    }
}
