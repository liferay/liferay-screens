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
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.MediaStoreCallback;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.io.File;
import rx.functions.Action1;

import static com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.RECORD_VIDEO;
import static com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.SELECT_ANY_FROM_GALLERY;
import static com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.SELECT_IMAGE_FROM_GALLERY;
import static com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.SELECT_VIDEO_FROM_GALLERY;
import static com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.TAKE_PICTURE_WITH_CAMERA;

/**
 * @author Javier Gamarra
 */
public class DDLDocumentFieldView extends BaseDDLFieldTextView<DocumentField>
	implements DDLFieldViewModel<DocumentField>, View.OnClickListener {

	public interface UploadListener {
		void startUploadField(DocumentField field);
	}

	protected ProgressBar progressBar;
	protected Dialog chooseOriginDialog;
	protected AlertDialog fileDialog;
	protected UploadListener uploadListener;

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
			chooseOriginDialog = createOriginDialog();
			chooseOriginDialog.show();
		}
	}

	@Override
	public void refresh() {
		EditText editText = getTextEditText();
		DocumentField field = getField();

		editText.setText(field.toFormattedString());

		if (field.isUploaded()) {
			setRightDrawable(editText, R.drawable.default_circle_success);
			progressBar.setVisibility(GONE);
		} else if (field.isUploadFailed()) {
			setRightDrawable(editText, R.drawable.default_circle_failed);
			progressBar.setVisibility(GONE);
		} else if (field.isUploading()) {
			setRightDrawable(editText, 0);
			progressBar.setVisibility(VISIBLE);
		} else {
			setRightDrawable(editText, R.drawable.default_blue);
			progressBar.setVisibility(GONE);
		}
	}

	public void setUploadListener(UploadListener uploadListener) {
		this.uploadListener = uploadListener;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (chooseOriginDialog != null) {
			chooseOriginDialog.dismiss();
			chooseOriginDialog = null;
		}

		// Avoid WindowLeak error on orientation changes
		if (fileDialog != null) {
			fileDialog.dismiss();
			fileDialog = null;
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		progressBar = findViewById(R.id.liferay_document_progress);
		getTextEditText().setOnClickListener(this);
	}

	@Override
	protected void onTextChanged(String text) {

	}

	protected Dialog createOriginDialog() {
		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		LayoutInflater factory = LayoutInflater.from(activity);
		final View customDialogView = factory.inflate(R.layout.ddlfield_document_chose_option_default, null);

		setupDialogListeners(activity, customDialogView);

		builder.setView(customDialogView);
		return builder.create();
	}

	protected void setupDialogListeners(Activity activity, View customDialogView) {
		RxPermissions rxPermissions = new RxPermissions(activity);

		View takeVideoButton = customDialogView.findViewById(R.id.liferay_dialog_take_video_form);
		RxView.clicks(takeVideoButton)
			.compose(rxPermissions.ensure(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
			.subscribe(launchCamera(RECORD_VIDEO));

		View takePhotoButton = customDialogView.findViewById(R.id.liferay_dialog_take_photo_form);
		RxView.clicks(takePhotoButton)
			.compose(rxPermissions.ensure(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
			.subscribe(launchCamera(TAKE_PICTURE_WITH_CAMERA));

		final View selectFileButton = customDialogView.findViewById(R.id.liferay_dialog_select_file_form);
		RxView.clicks(selectFileButton)
			.compose(rxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE))
			.subscribe(chooseFile());

		setupGalleryListeners(customDialogView, rxPermissions);
	}

	private void setupGalleryListeners(View customDialogView, RxPermissions rxPermissions) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			View galleryButton = customDialogView.findViewById(R.id.liferay_dialog_select_gallery);
			RxView.clicks(galleryButton)
				.compose(rxPermissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE))
				.subscribe(launchCamera(SELECT_ANY_FROM_GALLERY));
		} else {

			View selectVideo = customDialogView.findViewById(R.id.liferay_dialog_select_gallery_video);
			RxView.clicks(selectVideo)
				.compose(rxPermissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE))
				.subscribe(launchCamera(SELECT_VIDEO_FROM_GALLERY));

			TextView selectImage = customDialogView.findViewById(R.id.liferay_dialog_select_gallery);
			RxView.clicks(selectImage)
				.compose(rxPermissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE))
				.subscribe(launchCamera(SELECT_IMAGE_FROM_GALLERY));

			selectVideo.setVisibility(VISIBLE);
			selectImage.setText(R.string.select_image_from_gallery);
		}
	}

	protected ProgressBar getProgressBar() {
		return progressBar;
	}

	protected void setRightDrawable(EditText editText, @DrawableRes int drawable) {
		editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
	}

	@NonNull
	private Action1<Boolean> chooseFile() {
		return new Action1<Boolean>() {
			@Override
			public void call(Boolean result) {
				if (result) {
					fileDialog = new SelectFileDialog().createDialog(DDLDocumentFieldView.this.getContext(),
						new SelectFileDialog.SimpleFileDialogListener() {
							@Override
							public void onFileChosen(String path) {
								DDLDocumentFieldView.this.startUpload(Uri.fromFile(new File(path)));
								chooseOriginDialog.dismiss();
							}
						});
					fileDialog.show();
				}
				chooseOriginDialog.dismiss();
			}
		};
	}

	@NonNull
	private Action1<Boolean> launchCamera(final int mediaStore) {
		return new Action1<Boolean>() {
			@Override
			public void call(Boolean result) {
				if (result) {

					MediaStoreRequestShadowActivity.show(getContext(), mediaStore, new MediaStoreCallback() {
						@Override
						public void onUriReceived(Uri uri) {
							DDLDocumentFieldView.this.startUpload(uri);
						}
					});
				}
				chooseOriginDialog.dismiss();
			}
		};
	}

	private void startUpload(Uri uri) {
		getField().createLocalFile(uri.toString());
		getTextEditText().setText(uri.getPath());

		uploadListener.startUploadField(getField());
	}
}