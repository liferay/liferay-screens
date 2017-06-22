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

package com.liferay.mobile.screens.util;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Sarai Díaz García
 */

public class AssetReader {

	private Context context;

	public AssetReader(Context context) {
		this.context = context;
	}

	public String read(int fileId) {
		try {
			InputStream in = context.getResources().openRawResource(fileId);
			return getFileContent(in);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String read(String filename) {
		try {
			InputStream in = context.getResources().getAssets().open(filename);
			return getFileContent(in);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getFileContent(InputStream inputStream) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();
		String mLine = reader.readLine();

		while (mLine != null) {
			sb.append(mLine);
			mLine = reader.readLine();
		}

		reader.close();

		return sb.toString();
	}
}