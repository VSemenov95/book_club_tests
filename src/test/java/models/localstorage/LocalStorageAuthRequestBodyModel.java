package models.localstorage;

public record LocalStorageAuthRequestBodyModel(
        UserDataModel userDataModel,
        String accessToken,
        String refreshToken,
        boolean isAuthenticated) {

}