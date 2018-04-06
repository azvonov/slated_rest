import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class PoC {

    RequestSpecification specification;
    SessionFilter sessionFilter;

    @BeforeMethod
    public void setUp() throws Exception {

        specification = new RequestSpecBuilder().build();
        specification.baseUri("https://www.slated.com")
                .authentication().form("gambelz@gmail.com", "123456", new FormAuthConfig("/", "id_username", "id_password"));
        specification.redirects().follow(true).
                and().redirects().max(5).
                and().redirects().rejectRelative(false);
        sessionFilter = new SessionFilter();

    }

    @Test
    public void testRest() throws Exception {
        Response response = given()
                .filter(sessionFilter)
                .spec(specification)
                .contentType(ContentType.JSON).
//                auth().form("gambelz@gmail.com", "123456", new FormAuthConfig("/", "id_username", "id_password")).
        when().
                        get("https://www.slated.com/login/").
                        then()
                .statusCode(200).extract().response();
        System.out.print(response.toString());
    }
}
