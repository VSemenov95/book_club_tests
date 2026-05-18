package api;

import io.qameta.allure.Step;
import models.clubs.CreateClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestRemoveSpec;
import static specs.BaseSpec.requestSpec;
import static specs.clubs.ClubSpec.*;


public class ClubsApiClient {
    @Step("Создание клуба")
    public SuccessfulCreateClubResponseModel createClub(CreateClubsBodyModel clubsData, String access) {
        return given(requestSpec)
                .header("Authorization", access)
                .body(clubsData)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Просмотр клуба")
    public SuccessfulCreateClubResponseModel viewClub(Integer clubId) {
        return given(requestSpec)
                .pathParam("id", clubId)
                .when()
                .get("/clubs/{id}/")
                .then()
                .spec(getSuccessfulClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Редактирование телеграм-ссылки клуба")
    public SuccessfulCreateClubResponseModel editClub(Integer clubId, CreateClubsBodyModel clubsNewData, String access) {
        return given(requestSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .body(clubsNewData)
                .when()
                .patch("/clubs/{id}/")
                .then()
                .spec(SuccessfulEditClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Удаление клуба")
    public void deleteClub(Integer clubId, String access) {
        given(requestRemoveSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .when()
                .delete("/clubs/{id}/")
                .then()
                .spec(SuccessfulRemoveClubResponseSpec);
    }

    @Step("Проверка существования клуба")
    public void existenceClub(Integer clubId, String access, Integer expectedStatusCode) {
        given(requestRemoveSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .when()
                .get("/clubs/{id}/")
                .then()
                .statusCode(expectedStatusCode);
    }
}