/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
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
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.util.FileUtil;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormView;

import java.io.File;

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
			launchCameraIntent(MediaStore.ACTION_VIDEO_CAPTURE, FileUtil.createVideoFile());
			_choseOriginDialog.dismiss();
		}
		else if (view.getId() == R.id.default_dialog_take_photo) {
			launchCameraIntent(MediaStore.ACTION_IMAGE_CAPTURE, FileUtil.createImageFile());
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
			_progressBar.setVisibility(GONE);
		}
		else if (getField().isUploadFailed()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_circle_failed, 0);
			_progressBar.setVisibility(GONE);
		}
		else if (getField().isUploading()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			_progressBar.setVisibility(VISIBLE);
		}
		else {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_blue, 0);
			_progressBar.setVisibility(GONE);
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

	protected AlertDialog createOriginDialog() {
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

	protected void launchCameraIntent(String intent, File file) {
		Intent cameraIntent = new Intent(intent);

		if (file != null) {
			getField().createLocalFile(file.getAbsolutePath());
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			((Activity) getContext()).startActivityForResult(cameraIntent, _positionInForm);
		}
	}

	protected void showSelectFileDialog(final View view) {
		_fileDialog = new SelectFileDialog().createDialog(getContext(),
			new SelectFileDialog.SimpleFileDialogListener() {

				@Override
				public void onFileChosen(String path) {
					_progressBar.setVisibility(VISIBLE);
					getTextEditText().setText(path);

					DocumentField field = getField();
					field.createLocalFile(path);
					field.moveToUploadInProgressState();
					view.setTag(field);
					((DDLFormView) getParentView()).onClick(view);
				}

			});

		_fileDialog.show();
	}

	protected ProgressBar getProgressBar() {
		return _progressBar;
	}

	private int _positionInForm;
	private ProgressBar _progressBar;
	private AlertDialog _choseOriginDialog;
	private AlertDialog _fileDialog;

}