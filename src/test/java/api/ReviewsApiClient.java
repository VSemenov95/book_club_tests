package api;

import io.qameta.allure.Step;
import models.clubs.SuccessfulCreateReviewResponseModel;
import models.reviews.CreateReviewBodyModel;
import models.reviews.EditAssessmentReviewBodyModel;
import models.reviews.NotPermissionResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestRemoveSpec;
import static specs.BaseSpec.requestSpec;
import static specs.reviews.ReviewsSpec.*;

public class ReviewsApiClient {
    @Step("Создание отзыва")
    public SuccessfulCreateReviewResponseModel reviewCreation(String access, CreateReviewBodyModel reviewData) {
        return given(requestSpec)
                .header("Authorization", access)
                .body(reviewData)
                .when()
                .post("/clubs/reviews/")
                .then()
                .spec(createReviewResponseSpec)
                .extract()
                .as(SuccessfulCreateReviewResponseModel.class);
    }

    @Step("Редактирование отзыва")
    public SuccessfulCreateReviewResponseModel reviewEdit
            (Integer reviewId, String access, CreateReviewBodyModel editReviewData) {
        return given(requestSpec)
                .pathParam("id", reviewId)
                .header("Authorization", access)
                .body(editReviewData)
                .when()
                .put("/clubs/reviews/{id}/")
                .then()
                .spec(editReviewResponseSpec)
                .extract()
                .as(SuccessfulCreateReviewResponseModel.class);
    }

    @Step("Редактирование оценки отзыва")
    public SuccessfulCreateReviewResponseModel reviewAssessmentEdit
            (Integer reviewId, String access, EditAssessmentReviewBodyModel editAssessmentReviewData) {
        return given(requestSpec)
                .pathParam("id", reviewId)
                .header("Authorization", access)
                .body(editAssessmentReviewData)
                .when()
                .patch("/clubs/reviews/{id}/")
                .then()
                .spec(editReviewAssessmentResponseSpec)
                .extract()
                .as(SuccessfulCreateReviewResponseModel.class);
    }

    @Step("Удаление отзыва")
    public void reviewDeletion(Integer reviewId, String access) {
        given(requestRemoveSpec)
                .pathParam("id", reviewId)
                .header("Authorization", access)
                .when()
                .delete("/clubs/reviews/{id}/")
                .then()
                .spec(deleteReviewResponseSpec);
    }

    @Step("Проверка отзыва на существование")
    public void reviewViewing(Integer reviewId, String access, Integer expectedStatusCode) {
        given(requestRemoveSpec)
                .pathParam("id", reviewId)
                .header("Authorization", access)
                .when()
                .get("/clubs/reviews/{id}/")
                .then()
                .statusCode(expectedStatusCode);
    }

    @Step("Попытка редактирования чужого отзыва")
    public NotPermissionResponseModel notPermissionResponse
            (Integer reviewId, String access, CreateReviewBodyModel editReviewData) {
        return given(requestSpec)
                .pathParam("id", reviewId)
                .header("Authorization", access)
                .body(editReviewData)
                .when()
                .put("/clubs/reviews/{id}/")
                .then()
                .spec(editSomeoneElseReviewResponseSpec)
                .extract()
                .as(NotPermissionResponseModel.class);
    }

    @Step("Попытка удаления чужого отзыва")
    public NotPermissionResponseModel notPermissionDeleteResponse(int reviewId, String access) {
        return given(requestSpec)
                .pathParam("id", reviewId)
                .header("Authorization", access)
                .when()
                .delete("/clubs/reviews/{id}/")
                .then()
                .spec(editSomeoneElseReviewResponseSpec)
                .extract()
                .as(NotPermissionResponseModel.class);
    }

}