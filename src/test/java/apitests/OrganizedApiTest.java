package apitests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class OrganizedApiTest {
    @BeforeAll
    public static void setupOnce(){
        RestAssured.baseURI="https://reqres.in/api";
        System.out.println("BeforeAll:runs once for the whole class");
    }
    @BeforeEach
    public void setupEachTest(){
        System.out.println("BeforeEach:runs before every test");
    }
    @AfterEach
    public void cleanupEachTest(){
        System.out.println("AfterEach:runs after every test");
    }
    @Tag("smoke")
    @Test
    public void testGetUserSmoke(){
        given().when().get("/users/2").then().statusCode(200);
    }
    @Tag("regression")
    @Test
    public void testGetUserDetails(){
        given().when().get("/users/2").then().statusCode(200).body("data.id",equalTo(2));
    }
    @Tag("regression")
    @Test
    public void testUserNotFound(){
        given().when().get("/users/9999").then().statusCode(404);
    }
}
