package api;

public class ApiClient {
    public final AuthApiClient auth = new AuthApiClient();
    public final UserApiClient user = new UserApiClient();
    public final ClubsApiClient club = new ClubsApiClient();
    public final ReviewsApiClient reviews = new ReviewsApiClient();
}
