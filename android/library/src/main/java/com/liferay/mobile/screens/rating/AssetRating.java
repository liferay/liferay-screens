package com.liferay.mobile.screens.rating;

/**
 * @author Alejandro Hernández
 */
public class AssetRating {
	public AssetRating(long classPK, String className, int[] ratings, double average,
		double userScore, double totalScore, int totalCount) {
		_classPK = classPK;
		_className = className;
		_ratings = ratings;
		_userScore = userScore;
		_average = average;
		_totalScore = totalScore;
		_totalCount = totalCount;
	}

	public int[] getRatings() {
		return _ratings;
	}

	public void setRatings(int[] ratings) {
		this._ratings = ratings;
	}

	public double getAverage() {
		return _average;
	}

	public void setAverage(double average) {
		this._average = average;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		this._className = className;
	}

	public double getUserScore() {
		return _userScore;
	}

	public void setUserScore(double userScore) {
		this._userScore = userScore;
	}

	public double getTotalScore() {
		return _totalScore;
	}

	public void setTotalScore(double totalScore) {
		this._totalScore = totalScore;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		this._classPK = classPK;
	}

	public int getTotalCount() {
		return _totalCount;
	}

	public void setTotalCount(int totalCount) {
		this._totalCount = totalCount;
	}

	private int[] _ratings;
	private double _average;
	private String _className;
	private double _userScore;
	private double _totalScore;
	private long _classPK;
	private int _totalCount;
}
