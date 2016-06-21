package com.liferay.mobile.screens.rating;

import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public class RatingEntry {
  public RatingEntry(Map<String, Object> values) {
    _values = values;
  }

  public Map<String, Object> getValues() {
    return _values;
  }

  public long getScore() {
    return (long) _values.get("score");
  }

  private final Map<String, Object> _values;
}
