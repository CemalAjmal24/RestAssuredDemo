package apitests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class FirstApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/api";
    }
    @Test
    public void testGetUser() {
        given().when().get("/users/2").then().statusCode(200).body("data.id", equalTo(2));
    }
    @Test
    public void testCreateUser() {
        given().contentType("application/json").body("{\"name\": \"Joe\",\"job\": \"consultant\"}").when().post("/users").then().statusCode(201).body("name", equalTo("Joe"));
    }
    @Test
    public void testUpdateUser() {
        given().contentType("application/json").body("{\"name\": \"Joe\",\"job\": \"developer\"}").when().put("/users/2").then().statusCode(200).body("job", equalTo("developer"));
    }
    @Test
    public void testDeleteUser() {
        given().when().delete("/users/2").then().statusCode(204);
    }
}