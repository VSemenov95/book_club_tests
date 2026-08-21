package tests.api;

import data.TestData;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.clubs.CreateClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.TELEGRAM_CHAT_LINK;
import static data.TestData.UPDATE_TELEGRAM_CHAT_LINK;

@Owner("VSSemenov")
@Epic("CRUD-операции с клубом")
@DisplayName("CRUD-операции с клубом")
public class ClubsTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @Tag("API")
    @Story("Создание клуба")
    @DisplayName("Создание клуба")
    public void successfulCreationOfClub() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse;
        createClubResponse = api.club.createClub(clubsData, access);

        step("Проверка данных созданного клуба", () -> {
            assertThat(createClubResponse.id()).isNotNull();
            assertThat(createClubResponse.bookTitle()).isEqualTo(testData.bookTitle);
            assertThat(createClubResponse.bookAuthors()).isEqualTo(testData.bookAuthors);
            assertThat(createClubResponse.publicationYear()).isEqualTo(testData.publicationYear);
            assertThat(createClubResponse.description()).isEqualTo(testData.description);
            assertThat(createClubResponse.telegramChatLink()).isEqualTo(TELEGRAM_CHAT_LINK);
            assertThat(createClubResponse.owner()).isNotNull();
            assertThat(createClubResponse.owner()).isEqualTo(registrationResponse.id());
            assertThat(createClubResponse.members()).isNotNull();
            assertThat(createClubResponse.members()).isNotEmpty();
            assertThat(createClubResponse.reviews()).isEmpty();
            assertThat(createClubResponse.created()).isNotNull();
            assertThat(createClubResponse.modified()).isNull();
        });
    }

    @Test
    @Tag("API")
    @Story("Просмотр клуба")
    @DisplayName("Просмотр клуба")
    public void successfulViewingClub() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(clubsData, access);

        Integer clubId = createClubResponse.id();

        SuccessfulCreateClubResponseModel viewingClubResponse = api.club.viewClub(clubId);

        step("Проверка данных клуба", () -> {
            assertThat(viewingClubResponse.id()).isEqualTo(clubId);
            assertThat(viewingClubResponse.bookTitle()).isEqualTo(testData.bookTitle);
            assertThat(viewingClubResponse.bookAuthors()).isEqualTo(testData.bookAuthors);
            assertThat(viewingClubResponse.publicationYear()).isEqualTo(testData.publicationYear);
            assertThat(viewingClubResponse.description()).isEqualTo(testData.description);
            assertThat(viewingClubResponse.telegramChatLink()).isEqualTo(TELEGRAM_CHAT_LINK);
        });
    }

    @Test
    @Tag("API")
    @Story("Редактирование клуба")
    @DisplayName("Редактирование телеграм-ссылки клуба")
    public void successfulEditingClub() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(clubsData, access);

        Integer clubId = createClubResponse.id();

        CreateClubsBodyModel clubsNewData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, UPDATE_TELEGRAM_CHAT_LINK);

        SuccessfulCreateClubResponseModel editingClubResponse;
        editingClubResponse = api.club.editClub(clubId, clubsNewData, access);

        step("Проверка данных клуба после редактирования Telegram-ссылки", () ->
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(editingClubResponse.id()).isNotNull();
                    softAssertions.assertThat(editingClubResponse.id()).isEqualTo(clubId);
                    softAssertions.assertThat(editingClubResponse.bookTitle()).isEqualTo(testData.bookTitle);
                    softAssertions.assertThat(editingClubResponse.bookAuthors()).isEqualTo(testData.bookAuthors);
                    softAssertions.assertThat(editingClubResponse.publicationYear()).isEqualTo(testData.publicationYear);
                    softAssertions.assertThat(editingClubResponse.description()).isEqualTo(testData.description);
                    softAssertions.assertThat(editingClubResponse.telegramChatLink()).isEqualTo(UPDATE_TELEGRAM_CHAT_LINK);
                    softAssertions.assertThat(editingClubResponse.modified()).isNotNull();
                }));
    }

    @Test
    @Tag("API")
    @Story("Удаление клуба")
    @DisplayName("Удаление клуба")
    public void successfulRemoveClub() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(clubsData, access);

        Integer clubId = createClubResponse.id();

        api.club.deleteClub(clubId, access);

        api.club.existenceClub(clubId, access, 404);
    }
}