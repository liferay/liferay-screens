package com.liferay.mobile.screens.ddl.model;

import java.io.Serializable;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Victor Oliveira
 */
public class Option implements Serializable {

	public String label;
	public String name;
	public String value;
	public JSONObject data;

	public Option() {
		super();
	}

	public Option(Map<String, String> optionMap) {
		this(optionMap.get("label"), optionMap.get("name"), optionMap.get("value"));
	}

	public Option(String label, String name, String value) {
		this(label, name, value, null);
	}

	public Option(String label, String name, String value, JSONObject data) {
		this.label = label;
		this.name = name;
		this.value = value;
		this.data = data;
	}

	@Override
	public int hashCode() {
		int result = label != null ? label.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Option) {
			Option opt = (Option) obj;

			if (name != null) {
				return label.equals(opt.label) && value.equals(opt.value) && name.equals(opt.name);
			} else {
				return label.equals(opt.label) && value.equals(opt.value);
			}
		}

		return super.equals(obj);
	}
}
