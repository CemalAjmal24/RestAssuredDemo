package apitests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.concurrent.TimeUnit;
public class ResponseTimeApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/api";
        given().when().get("/users/1");
    }
    @Test
    public void testResponseTimeIsAcceptable(){
        given().when().get("/users/2").then().statusCode(200).time(lessThan(3000L));
    }
    @Test
    public void testResponseTimeInSeconds(){
        given().when().get("/users/2").then().statusCode(200).time(lessThan(3L),TimeUnit.SECONDS);
    }
    @Test
    public void testPrintActualResponseTime(){
        long responseTime=given().when().get("/users/2").then().statusCode(200).extract().time();
        System.out.println("Actual response time:" + responseTime + "ms");
    }
}
