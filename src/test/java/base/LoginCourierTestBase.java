package base;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;

public class LoginCourierTestBase extends TestBase {
    protected Response courierResponse;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        this.courierResponse = this.client.create(this.courier);
        this.courierResponse.then().statusCode(HttpStatus.SC_CREATED);
    }
}
