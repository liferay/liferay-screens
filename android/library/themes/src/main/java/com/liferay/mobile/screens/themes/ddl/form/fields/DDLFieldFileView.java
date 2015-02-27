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

package com.liferay.mobile.screens.themes.ddl.form.fields;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.themes.ddl.form.DDLFormScreenletView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Javier Gamarra
 */
public class DDLFieldFileView extends BaseDDLFieldTextView<DocumentField>
		implements DDLFieldViewModel<DocumentField>, View.OnClickListener {

	public DDLFieldFileView(Context context) {
		super(context, null);
	}

	public DDLFieldFileView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public DDLFieldFileView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onClick(final View view) {
		_choseOriginDialog = createOriginDialog(view);
		_choseOriginDialog.show();
	}

	@Override
	public void refresh() {
		getTextEditText().setText(getField().toFormattedString());
		if (getField().getCurrentValue() != null && getField().getCurrentValue().getState() !=
				null) {
			switch (getField().getCurrentValue().getState()) {
				case UPLOADED:
					getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_circle_success, 0);
					_progressBar.setVisibility(View.GONE);
					break;
				case FAILED:
					getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_circle_failed, 0);
					_progressBar.setVisibility(View.GONE);
					break;
				case UPLOADING:
				case PENDING:
					getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					_progressBar.setVisibility(View.VISIBLE);
					break;
				default:
					getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					_progressBar.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onPostValidation(boolean valid) {
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

	private AlertDialog createOriginDialog(final View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setPositiveButton(getContext().getString(R.string.makeAPhoto), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				launchCameraIntent();
			}
		}).setNegativeButton(getContext().getString(R.string.selectAFile), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				showFileDialog(view);
			}
		});

		LayoutInflater factory = LayoutInflater.from(getContext());
		final View customDialogView = factory.inflate(
				R.layout.ddlfield_select_dialog_default, null);
		TextView title = (TextView) customDialogView.findViewById(R.id.dialog_title);
		title.setText(getContext().getString(R.string.origin_of_file));
		builder.setCustomTitle(customDialogView);
		return builder.create();
	}

	private void launchCameraIntent() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File photoFile = null;
		try {
			photoFile = createImageFile();
			getField().getCurrentValue().setName(photoFile.getAbsolutePath());

			if (photoFile != null) {
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				//TODO Activity? or fragment?
				((Activity) getContext()).startActivityForResult(cameraIntent, _positionInForm);
			}
		} catch (IOException e) {
			//TODO Notify user?
		}
	}

	private void showFileDialog(final View view) {
		_fileDialog = new FileDialog().createDialog(getContext(), new FileDialog.SimpleFileDialogListener() {
			@Override
			public void onFileChosen(String path) {
				_progressBar.setVisibility(View.VISIBLE);
				getTextEditText().setText(path);

				DocumentField field = getField();
				field.getCurrentValue().setName(path);
				field.getCurrentValue().setState(DocumentField.State.PENDING);
				view.setTag(field);
				((DDLFormScreenletView) getParentView()).onClick(view);

			}
		});
		_fileDialog.show();
	}

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, ".jpg", storageDir);
		return image;
	}

	private int _positionInForm;
	private ProgressBar _progressBar;
	private AlertDialog _choseOriginDialog;
	private AlertDialog _fileDialog;
}