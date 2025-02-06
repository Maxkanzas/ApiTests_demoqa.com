package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class BookSpec {

    public static RequestSpecification addBookRequestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().method()
            .log().body();

    public static RequestSpecification deleteBookRequestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().method()
            .log().body();

    public static ResponseSpecification addBookResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification deleteBookResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .log(BODY)
            .build();
}
