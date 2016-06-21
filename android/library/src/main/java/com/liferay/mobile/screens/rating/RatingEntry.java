package com.liferay.mobile.screens.rating;

import com.liferay.mobile.screens.util.JSONUtil;
import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public class RatingEntry {
  private double _score;

  public RatingEntry(Map<String, Object> values) {
    _values = values;
  }

  public Map<String, Object> getValues() {
    return _values;
  }

  public double getScore() {
    return _score;
  }

  private final Map<String, Object> _values;

  public void setScore(double score) {
    this._score = score;
  }

  public long getUserId() {
    return JSONUtil.castToLong(_values.get("userId"));
  }
}
