package tests.ui;

import data.TestData;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
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
import models.reviews.CreateReviewBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.BookClubPage;
import pages.components.RatingToStarsComponent;
import tests.TestBase;

import static data.TestData.*;

@Owner("VSSemenov")
@DisplayName("Действия пользователя с сущностью Отзыв")
@Epic("Доработка функциональности пользовательских операций относительно отзывов")
public class ReviewUiTests extends TestBase {
    TestData testData = new TestData();
    BookClubPage bookClubPage = new BookClubPage();
    RatingToStarsComponent ratingToStarsComponent = new RatingToStarsComponent();


    @Test
    @Tag("UI")
    @Story("Создание отзыва")
    @DisplayName("[UI] Создание отзыва")
    public void successfulCreationOfReviewUi() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(
                        testData.username,
                        testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth
                (new LoginBodyModel(
                        testData.username,
                        testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(
                clubsData,
                access);

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
                .setReviewButton()
                .checkReviewForm()
                .setAssessment(testData.assessment)
                .setReadPages(testData.readPages)
                .setReview(testData.review)
                .setSaveButton()
                .checkReviewCard()
                .checkResult(bookClubPage.getReviewerName(), testData.username)
                .checkResult(bookClubPage.getReviewRating(), ratingToStarsComponent.toStars(testData.assessment))
                .checkResult(bookClubPage.getReviewContent(), testData.review)
                .checkResult(bookClubPage.getReadPagesCard(), testData.readPages + " стр.");
    }

    @Test
    @Tag("UI")
    @Story("Редактирование отзыва")
    @DisplayName("[UI] Редактирование отзыва")
    public void successfulEditReviewUi() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(
                        testData.username,
                        testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth
                (new LoginBodyModel(
                        testData.username,
                        testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(
                clubsData,
                access);

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel(
                createClubResponse.id(),
                testData.review,
                testData.assessment,
                testData.readPages);

        api.reviews.reviewCreation(
                access,
                reviewData);

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
                .setEditReviewButton()
                .checkReviewForm()
                .editReviewAssessment(testData.newAssessment)
                .editReviewReadPages(testData.newReadPages)
                .editReview(testData.newReview)
                .setSaveBtn()
                .checkReviewCard()
                .checkResult(bookClubPage.getReviewerName(), testData.username)
                .checkResult(bookClubPage.getReviewRating(), ratingToStarsComponent.toStars(testData.newAssessment))
                .checkResult(bookClubPage.getReviewContent(), testData.newReview)
                .checkResult(bookClubPage.getReadPagesCard(), testData.newReadPages + " стр.");
    }

    @Test
    @Tag("UI")
    @Story("Удаление отзыва")
    @DisplayName("[UI] Удаление отзыва")
    public void successfulDeletingReviewUi() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(
                        testData.username,
                        testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth
                (new LoginBodyModel(
                        testData.username,
                        testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.club.createClub(
                clubsData,
                access);

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel(
                createClubResponse.id(),
                testData.review,
                testData.assessment,
                testData.readPages);

        api.reviews.reviewCreation(
                access,
                reviewData);

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
                .setDeleteReviewButton()
                .checkNoReviews();
    }
}