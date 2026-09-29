package apitests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class AuthApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/api";
    }
    @Test
    public void testSuccessfulLogin(){
        given().contentType("application/json").body("{\"email\": \"mat.kate@reqres.in\",\"password\": \"cityslicka\"}").when().post("/login").then().statusCode(200).body("token",notNullValue());
    }
    @Test
    public void testLoginMissingPassword(){
        given().contentType("application/json").body("{\"email\": \"mat.kate@reqres.in\"}").when().post("/login").then().statusCode(400).body("error",equalTo("Missing password"));
    }
    @Test
    public void testLoginMissingEmail(){
        given().contentType("application/json").body("{\"password\": \"cityslicka\"}").when().post("/login").then().statusCode(400).body("error",equalTo("Missing email or username"));
    }
    @Test
    public void testExtractTokenAndReuse(){
        String token= given().contentType("application/json").body("{\"email\": \"mat.kate@reqres.in\",\"password\": \"cityslicka\"}").when().post("/login").then().statusCode(200).extract().jsonPath().getString("token");
    }
}
