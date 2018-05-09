/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddm.form.model;

import com.liferay.mobile.screens.ddl.model.DDMStructure;

/**
 * @author Paulo Cruz
 */
public class FormInstance {

	private long formInstanceId;
	private DDMStructure ddmStructure;
	private boolean requiredAuthentication;
	private boolean requiredCaptcha;
	private String redirectURL;

	public FormInstance(long formInstanceId, DDMStructure ddmStructure) {
		this.formInstanceId = formInstanceId;
		this.ddmStructure = ddmStructure;
	}

	public FormInstance(long formInstanceId, DDMStructure ddmStructure, boolean requiredAuthentication,
		boolean requiredCaptcha, String redirectURL) {

		this.formInstanceId = formInstanceId;
		this.ddmStructure = ddmStructure;
		this.requiredAuthentication = requiredAuthentication;
		this.requiredCaptcha = requiredCaptcha;
		this.redirectURL = redirectURL;
	}

	public boolean isRequiredAuthentication() {
		return requiredAuthentication;
	}

	public void setRequiredAuthentication(boolean requiredAuthentication) {
		this.requiredAuthentication = requiredAuthentication;
	}

	public boolean isRequiredCaptcha() {
		return requiredCaptcha;
	}

	public void setRequiredCaptcha(boolean requiredCaptcha) {
		this.requiredCaptcha = requiredCaptcha;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public long getFormInstanceId() {
		return formInstanceId;
	}

	public void setFormInstanceId(long formInstanceId) {
		this.formInstanceId = formInstanceId;
	}

	public DDMStructure getDdmStructure() {
		return ddmStructure;
	}

	public void setDdmStructure(DDMStructure ddmStructure) {
		this.ddmStructure = ddmStructure;
	}
}
