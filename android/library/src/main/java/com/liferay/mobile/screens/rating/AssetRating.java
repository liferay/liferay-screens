package com.liferay.mobile.screens.rating;

/**
 * @author Alejandro Hernández
 */
public class AssetRating {

    private int[] ratings;
    private double average;
    private String className;
    private double userScore;
    private double totalScore;
    private long classPK;
    private int totalCount;

    public AssetRating(long classPK, String className, int[] ratings, double average, double userScore,
        double totalScore, int totalCount) {

        this.classPK = classPK;
        this.className = className;
        this.ratings = ratings;
        this.userScore = userScore;
        this.average = average;
        this.totalScore = totalScore;
        this.totalCount = totalCount;
    }

    public int[] getRatings() {
        return ratings;
    }

    public void setRatings(int[] ratings) {
        this.ratings = ratings;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public long getClassPK() {
        return classPK;
    }

    public void setClassPK(long classPK) {
        this.classPK = classPK;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
