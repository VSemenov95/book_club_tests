package tests.ui;

import data.TestData;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import models.clubs.CreateClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;
import models.localstorage.LocalStorageAuthRequestBodyModel;
import models.localstorage.UserDataModel;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BookClubPage;
import tests.TestBase;

import static data.TestData.*;


public class ClubsUiTests extends TestBase {
    TestData testData = new TestData();
    BookClubPage bookClubPage = new BookClubPage();

    @Test
    @DisplayName("[UI] Создание клуба")
    public void successfulCreationOfClubWithUi() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(clubsData, access);

        UserDataModel userData = new UserDataModel(
                registrationResponse.id(),
                registrationResponse.username(),
                registrationResponse.firstName(),
                registrationResponse.lastName(),
                registrationResponse.email(),
                registrationResponse.remoteAddr());

        LocalStorageAuthRequestBodyModel localStorageAuthRequestBody = new LocalStorageAuthRequestBodyModel(
                userData,
                loginResponse.access(),
                loginResponse.refresh(),
                true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStringLocalStorage;
        try {
            jsonStringLocalStorage = mapper.writeValueAsString(localStorageAuthRequestBody);
            System.out.println(jsonStringLocalStorage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(ERROR_CONVERTING_TO_JSON, e);
        }

        bookClubPage.openFaviconPage(jsonStringLocalStorage)
                .openBookClubPage(createClubResponse.id())
                .checkClubDetails()
                .checkResult(bookClubPage.getClubTitle(), createClubResponse.bookTitle())
                .checkResult(bookClubPage.getClubAuthors(), createClubResponse.bookAuthors())
                .checkResult(bookClubPage.getClubPublicationYear(), String.valueOf(createClubResponse.publicationYear()))
                .checkResult(bookClubPage.getClubDescription(), createClubResponse.description());
    }

    @Test
    @DisplayName("[UI] Редактирование описания клуба")
    public void successfulEditingClubUI() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(clubsData, access);

        UserDataModel userData = new UserDataModel(
                registrationResponse.id(),
                registrationResponse.username(),
                registrationResponse.firstName(),
                registrationResponse.lastName(),
                registrationResponse.email(),
                registrationResponse.remoteAddr());

        LocalStorageAuthRequestBodyModel localStorageAuthRequestBody = new LocalStorageAuthRequestBodyModel(
                userData,
                loginResponse.access(),
                loginResponse.refresh(),
                true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStringLocalStorage;
        try {
            jsonStringLocalStorage = mapper.writeValueAsString(localStorageAuthRequestBody);
            System.out.println(jsonStringLocalStorage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(ERROR_CONVERTING_TO_JSON, e);
        }

        bookClubPage.openFaviconPage(jsonStringLocalStorage)
                .openBookClubPage(createClubResponse.id())
                .checkClubDetails()
                .checkResult(bookClubPage.getClubTitle(), testData.bookTitle)
                .checkResult(bookClubPage.getClubAuthors(), testData.bookAuthors)
                .checkResult(bookClubPage.getClubPublicationYear(), String.valueOf(testData.publicationYear))
                .checkResult(bookClubPage.getClubDescription(), testData.description);

        CreateClubsBodyModel clubsNewData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, NEW_BOOK_DESCRIPTION, TELEGRAM_CHAT_LINK);

        api.club.editClub(createClubResponse.id(), clubsNewData, access);

        bookClubPage.refreshPage()
                .checkClubDetails()
                .checkResult(bookClubPage.getClubTitle(), testData.bookTitle)
                .checkResult(bookClubPage.getClubAuthors(), testData.bookAuthors)
                .checkResult(bookClubPage.getClubPublicationYear(), String.valueOf(testData.publicationYear))
                .checkResult(bookClubPage.getClubDescription(), NEW_BOOK_DESCRIPTION);
    }

    @Test
    @DisplayName("[UI] Удаление клуба")
    public void successfulRemoveClubUI() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(clubsData, access);

        UserDataModel userData = new UserDataModel(
                registrationResponse.id(),
                registrationResponse.username(),
                registrationResponse.firstName(),
                registrationResponse.lastName(),
                registrationResponse.email(),
                registrationResponse.remoteAddr());

        LocalStorageAuthRequestBodyModel localStorageAuthRequestBody = new LocalStorageAuthRequestBodyModel(
                userData,
                loginResponse.access(),
                loginResponse.refresh(),
                true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStringLocalStorage;
        try {
            jsonStringLocalStorage = mapper.writeValueAsString(localStorageAuthRequestBody);
            System.out.println(jsonStringLocalStorage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(ERROR_CONVERTING_TO_JSON, e);
        }

        bookClubPage.openFaviconPage(jsonStringLocalStorage)
                .openBookClubPage(createClubResponse.id())
                .checkClubDetails()
                .checkResult(bookClubPage.getClubTitle(), testData.bookTitle)
                .checkResult(bookClubPage.getClubAuthors(), testData.bookAuthors)
                .checkResult(bookClubPage.getClubPublicationYear(), String.valueOf(testData.publicationYear))
                .checkResult(bookClubPage.getClubDescription(), testData.description);

        api.club.deleteClub(createClubResponse.id(), access);

        bookClubPage.refreshPage()
                .checkErrorClubDetail();

    }
}