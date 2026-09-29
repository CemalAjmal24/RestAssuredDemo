package apitests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class SchemaValidationApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/api";
    }
    @Test
    public void testUserResponseMatchesSchema(){
        given().when().get("/users/2").then().statusCode(200).body(matchesJsonSchemaInClasspath("user-schema.json"));
    }
}
