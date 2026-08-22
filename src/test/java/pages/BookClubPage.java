package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.components.ContentClubComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BookClubPage {
    private final SelenideElement clubDetails = $(".club-details"),
            clubTitle = $(".club-header"),
            clubAuthors = $(".authors"),
            clubPublicationYear = $(".year"),
            clubDescription = $(".description"),
            clubDetail = $(".club-details"),
            reviewButton = $(".add-review-btn"),
            reviewForm = $(".review-form"),
            assessment = $("#assessment"),
            readPages = $("#readPages"),
            review = $("#review"),
            saveButton = $(".save-btn"),
            reviewCard = $(".review-card"),
            reviewerName = $(".reviewer-name"),
            reviewContent = $(".review-content"),
            readPagesCard = $(".read-pages"),
            reviewRating = $(".stars"),
            editReviewButton = $(".edit-review-btn"),
            saveBtn = $(".save-btn"),
            deleteReviewButton = $(".delete-review-btn"),
            noReviews = $(".no-reviews");

    ContentClubComponent contentClubComponent = new ContentClubComponent();

    @Step("Открыть ресурс и передать авторизацию")
    public BookClubPage openFaviconPage(String value) {
        open("https://book-club.qa.guru/favicon.ico");
        localStorage().setItem("book_club_auth", value);
        return this;
    }

    @Step("Перейти в созданный клуб по id: {value}")
    public BookClubPage openBookClubPage(int value) {
        open("https://book-club.qa.guru/clubs/" + value);
        return this;
    }

    @Step("Проверить, что отобразился созданный клуб")
    public BookClubPage checkClubDetails() {
        clubDetails.shouldBe(visible);
        return this;
    }

    public BookClubPage checkResult(SelenideElement key, String value) {
        contentClubComponent.checkResultValues(key, value);
        return this;
    }
    @Step("Проверить название клуба")
    public SelenideElement getClubTitle() {
        return clubTitle;
    }

    @Step("Проверить имя автора книги")
    public SelenideElement getClubAuthors() {
        return clubAuthors;
    }

    @Step("Проверить год публикации книги")
    public SelenideElement getClubPublicationYear() {
        return clubPublicationYear;
    }

    @Step("Проверить описание созданного клуба")
    public SelenideElement getClubDescription() {
        return clubDescription;
    }

    @Step("Обновить страницу браузера")
    public BookClubPage refreshPage() {
        refresh();
        return this;
    }

    @Step("Проверить, что клуб удален")
    public BookClubPage checkErrorClubDetail() {
        clubDetail.shouldBe(text("Не удалось загрузить информацию о клубе"));
        return this;
    }

    @Step("Нажать на кнопку \"Написать отзыв\"")
    public BookClubPage setReviewButton() {
        reviewButton.click();
        return this;
    }

    @Step("Проверить, что открылась форма для создания отзыва")
    public BookClubPage checkReviewForm() {
        reviewForm.shouldBe(visible);
        return this;
    }

    @Step("Ввести оценку от 1 до 5 в поле \"Оценка\"")
    public BookClubPage setAssessment(int value) {
        assessment.setValue(String.valueOf(value));
        return this;
    }

    @Step("Указать количество прочитанных страниц в поле \"Прочитано страниц\"")
    public BookClubPage setReadPages(int value) {
        readPages.setValue(String.valueOf(value));
        return this;
    }

    @Step("Ввести текст отзыва о книге в поле \"Ваш отзыв\"")
    public BookClubPage setReview(String value) {
        review.setValue(value);
        return this;
    }

    @Step("Нажать на кнопку \"Опубликовать\"")
    public BookClubPage setSaveButton() {
        saveButton.click();
        return this;
    }

    @Step("Проверить, что появилась карточка нового отзыва")
    public BookClubPage checkReviewCard() {
        reviewCard.shouldBe(visible);
        return this;
    }

    @Step("Проверить имя автора клуба")
    public SelenideElement getReviewerName() {
        return reviewerName;
    }

    @Step("Проверить контент клуба")
    public SelenideElement getReviewContent() {
        return reviewContent;
    }

    @Step("Проверить указанное количество прочитанных страниц")
    public SelenideElement getReadPagesCard() {
        return readPagesCard;
    }

    @Step("Проверить оценку книги")
    public SelenideElement getReviewRating() {
        return reviewRating;
    }

    @Step("Нажать на кнопку \"Редактировать\"")
    public BookClubPage setEditReviewButton() {
        editReviewButton.click();
        return this;
    }

    @Step("Отредактировать данные в поле \"Оценка\"")
    public BookClubPage editReviewAssessment(int value) {
        assessment.clear();
        assessment.setValue(String.valueOf(value));
        return this;
    }

    @Step("Отредактировать данные в поле \"Прочитано страниц\"")
    public BookClubPage editReviewReadPages(int value) {
        readPages.clear();
        readPages.setValue(String.valueOf(value));
        return this;
    }

    @Step("Отредактировать отзыв в поле \"Ваш отзыв\"")
    public BookClubPage editReview(String value) {
        review.clear();
        review.setValue(value);
        return this;
    }

    @Step("Нажать на кнопку \"Сохранить\"")
    public BookClubPage setSaveBtn() {
        saveBtn.click();
        return this;
    }

    @Step("Нажать на кнопку \"Удалить\"")
    public BookClubPage setDeleteReviewButton() {
        deleteReviewButton.click();
        confirm();
        return this;
    }

    @Step("Проверить, что отзыв удален")
    public BookClubPage checkNoReviews() {
        noReviews.shouldBe(text("Пока нет отзывов. Будьте первым, кто поделится впечатлениями!"));
        return this;
    }

}