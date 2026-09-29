package apitests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class PojoApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/api";
    }
    @Test
    public void testCreateUser(){
        User newUser= new User("Joe","consultant");
        given().contentType("application/json").body(newUser).when().post("/users").then().statusCode(201).body("name",equalTo("Joe"));
    }
}
