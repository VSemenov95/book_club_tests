package specs.reviews;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;
import static specs.BaseSpec.baseResponseSpec;

public class ReviewsSpec {
    public static ResponseSpecification createReviewResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath("schemas/reviews/create_review_response_schema.json"))
            .build();

    public static ResponseSpecification editReviewResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/reviews/edit_review_response_schema.json"))
            .build();

    public static ResponseSpecification editReviewAssessmentResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/reviews/edit_review_response_schema.json"))
            .build();

    public static ResponseSpecification deleteReviewResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification editSomeoneElseReviewResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(403)
            .expectBody(matchesJsonSchemaInClasspath("schemas/reviews/not_permission_response_schema.json"))
            .expectBody("detail", notNullValue())
            .build();
}