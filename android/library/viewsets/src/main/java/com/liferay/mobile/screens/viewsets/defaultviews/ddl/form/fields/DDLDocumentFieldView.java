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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultCrouton;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormDefaultView;

import java.io.File;
import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class DDLDocumentFieldView extends BaseDDLFieldTextView<DocumentField>
		implements DDLFieldViewModel<DocumentField>, View.OnClickListener {

	public DDLDocumentFieldView(Context context) {
		super(context);
	}

	public DDLDocumentFieldView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLDocumentFieldView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onClick(final View view) {
		if (view.getId() == R.id.text) {
			_choseOriginDialog = createOriginDialog();
			_choseOriginDialog.show();
		}
		else if (view.getId() == R.id.default_dialog_take_video) {
			launchCameraIntent(MediaStore.ACTION_VIDEO_CAPTURE, createVideoFile());
			_choseOriginDialog.dismiss();
		}
		else if (view.getId() == R.id.default_dialog_take_photo) {
			launchCameraIntent(MediaStore.ACTION_IMAGE_CAPTURE, createImageFile());
			_choseOriginDialog.dismiss();
		}
		else if (view.getId() == R.id.default_dialog_select_file) {
			showSelectFileDialog(view);
			_choseOriginDialog.dismiss();
		}
	}


	@Override
	public void refresh() {
		getTextEditText().setText(getField().toFormattedString());
		if (getField().isUploaded()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_circle_success, 0);
			_progressBar.setVisibility(View.GONE);
		}
		else if (getField().isUploadFailed()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_circle_failed, 0);
			_progressBar.setVisibility(View.GONE);
		}
		else if (getField().isUploading()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			_progressBar.setVisibility(View.VISIBLE);
		}
		else {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_blue, 0);
			_progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void setPositionInParent(int position) {
		_positionInForm = position;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (_choseOriginDialog != null) {
			_choseOriginDialog.dismiss();
			_choseOriginDialog = null;
		}

		// Avoid WindowLeak error on orientation changes
		if (_fileDialog != null) {
			_fileDialog.dismiss();
			_fileDialog = null;
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		_progressBar = (ProgressBar) findViewById(R.id.fileProgress);
		getTextEditText().setOnClickListener(this);
	}

	@Override
	protected void onTextChanged(String text) {

	}

	private AlertDialog createOriginDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

		LayoutInflater factory = LayoutInflater.from(getContext());
		final View customDialogView = factory.inflate(
				R.layout.ddlfield_document_chose_option_default, null);

		customDialogView.findViewById(R.id.default_dialog_select_file).setOnClickListener(this);
		customDialogView.findViewById(R.id.default_dialog_take_photo).setOnClickListener(this);
		customDialogView.findViewById(R.id.default_dialog_take_video).setOnClickListener(this);

		builder.setView(customDialogView);

		return builder.create();
	}

	private void launchCameraIntent(String intent, File file) {
		Intent cameraIntent = new Intent(intent);

		if (file != null) {
			getField().createLocalFile(file.getAbsolutePath());
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			((Activity) getContext()).startActivityForResult(cameraIntent, _positionInForm);
		}
	}

	private void showSelectFileDialog(final View view) {
		_fileDialog = new SelectFileDialog().createDialog(getContext(),
				new SelectFileDialog.SimpleFileDialogListener() {

					@Override
					public void onFileChosen(String path) {
						_progressBar.setVisibility(View.VISIBLE);
						getTextEditText().setText(path);

						DocumentField field = getField();
						field.createLocalFile(path);
						field.moveToUploadInProgressState();
						view.setTag(field);
						((DDLFormDefaultView) getParentView()).onClick(view);
					}

				});

		_fileDialog.show();
	}

	private File createImageFile() {
		return createFile("PHOTO", Environment.DIRECTORY_PICTURES, ".jpg");
	}

	private File createVideoFile() {
		return createFile("VIDEO", Environment.DIRECTORY_MOVIES, ".mp4");
	}

	private File createFile(String name, String directory, String extension) {
		try {
			File storageDir = Environment.getExternalStoragePublicDirectory(
					directory);
			return File.createTempFile(name, extension, storageDir);
		}
		catch (IOException e) {
			LiferayLogger.e("error creating temporal file at uploading", e);
			DefaultCrouton.error(getContext(), getContext().getString(R.string.creating_file_error), e);
		}
		return null;
	}

	private int _positionInForm;
	protected ProgressBar _progressBar;
	private AlertDialog _choseOriginDialog;
	private AlertDialog _fileDialog;

}