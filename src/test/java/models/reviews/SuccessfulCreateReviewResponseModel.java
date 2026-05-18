package models.clubs;

public record SuccessfulCreateReviewResponseModel(Integer id,
                                                  Integer club,
                                                  ClubReviewUserModel user,
                                                  String review,
                                                  Integer assessment,
                                                  Integer readPages,
                                                  String created,
                                                  String modified) {
}