package com.docomotv.model.api;

/**
 * Created by anweiyang on 17/12/27.
 * 分页对象
 */
public class Sort extends HttpQueryParamBaseModel {

    private String distance;
    private String createAt;
    private String paymentCount;
    private String averageScore;
    private String commentCount;
    private String fullScoreCount;
    private String fullScorePercent;
    private Boolean random;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(String paymentCount) {
        this.paymentCount = paymentCount;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getFullScoreCount() {
        return fullScoreCount;
    }

    public void setFullScoreCount(String fullScoreCount) {
        this.fullScoreCount = fullScoreCount;
    }

    public String getFullScorePercent() {
        return fullScorePercent;
    }

    public void setFullScorePercent(String fullScorePercent) {
        this.fullScorePercent = fullScorePercent;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public Boolean getRandom() {
        return random;
    }
}
