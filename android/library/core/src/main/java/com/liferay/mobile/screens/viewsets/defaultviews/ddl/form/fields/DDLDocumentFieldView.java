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

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.view.RxView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.util.FileUtil;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import rx.functions.Action1;

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
		if (view.getId() == R.id.liferay_ddl_edit_text) {
			_choseOriginDialog = createOriginDialog();
			_choseOriginDialog.show();
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
		_progressBar = (ProgressBar) findViewById(R.id.liferay_document_progress);
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

		View takeVideoButton = customDialogView.findViewById(R.id.liferay_dialog_take_video);
		RxPermissions.getInstance(getContext())
			.request(RxView.clicks(takeVideoButton),
				Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
			.subscribe(launchCamera(MediaStore.ACTION_VIDEO_CAPTURE));

		View takePhotoButton = customDialogView.findViewById(R.id.liferay_dialog_take_photo_form);
		RxPermissions.getInstance(getContext())
			.request(RxView.clicks(takePhotoButton),
				Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
			.subscribe(launchCamera(MediaStore.ACTION_IMAGE_CAPTURE));

		final View selectFileButton = customDialogView.findViewById(R.id.liferay_dialog_select_file);
		RxPermissions.getInstance(getContext())
			.request(RxView.clicks(selectFileButton), Manifest.permission.WRITE_EXTERNAL_STORAGE)
			.subscribe(chooseFile(selectFileButton));

		builder.setView(customDialogView);
		return builder.create();
	}

	protected ProgressBar getProgressBar() {
		return _progressBar;
	}

	@NonNull
	private Action1<Boolean> chooseFile(final View view) {
		return new Action1<Boolean>() {
			@Override
			public void call(Boolean result) {
				if (result) {
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
								_choseOriginDialog.dismiss();
							}
						});
					_fileDialog.show();
				}
				else {
					LiferayCrouton.error(getContext(), getContext().getString(R.string.sd_permissions), null);
				}
				_choseOriginDialog.dismiss();
			}
		};
	}

	@NonNull
	private Action1<Boolean> launchCamera(final String intent) {
		return new Action1<Boolean>() {
			@Override
			public void call(Boolean result) {
				if (result) {
					Intent cameraIntent = new Intent(intent);
					File file = MediaStore.ACTION_VIDEO_CAPTURE.equals(intent) ?
						FileUtil.createVideoFile() : FileUtil.createImageFile();

					if (file != null) {
						getField().createLocalFile(file.getAbsolutePath());
						cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

						Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
						activity.startActivityForResult(cameraIntent, _positionInForm);
					}
				}
				else {
					LiferayCrouton.error(getContext(), getContext().getString(R.string.camera_permissions), null);
				}
				_choseOriginDialog.dismiss();
			}
		};
	}

	protected ProgressBar _progressBar;
	protected AlertDialog _choseOriginDialog;
	protected AlertDialog _fileDialog;

	private int _positionInForm;

}