package apitests;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
public class WireMockAuthTest {
    static WireMockServer wireMockServer;

    @BeforeAll
    public static void setup(){
        wireMockServer= new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost",8089);
        stubFor(get(urlEqualTo("/protected-resource")).atPriority(1).withHeader("Authorization",WireMock.equalTo("Bearer valid-token-123")).willReturn(aResponse().withStatus(200).withHeader("Content-Type","application/json").withBody("{ \"message\": \"Access granted\"}")));
        stubFor(get(urlEqualTo("/protected-resource")).atPriority(2).willReturn(aResponse().withStatus(401).withHeader("Content-Type","application/json").withBody("{ \"error\": \"Unauthorized\"}")));
        baseURI="http://localhost:8089";
    }
    @AfterAll
    public static void tearDown(){
        wireMockServer.stop();
    }
    @Test
    public void testAccessWithValidToken(){
        given().auth().oauth2("valid-token-123").log().all().when().get("/protected-resource").then().log().all().statusCode(200).body("message",equalTo("Access granted"));
    }
    @Test
    public void testAccessWithoutToken(){
        given().when().get("/protected-resource").then().statusCode(401).body("error",equalTo("Unauthorized"));
    }
    @Test
    public void testAccessWithInvalidToken(){
        given().auth().oauth2("wrong-token").when().get("/protected-resource").then().statusCode(401);
    }
}