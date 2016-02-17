package com.liferay.mobile.screens.viewsets.westeros.ddl.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Javier Gamarra
 */
public class DDLDocumentFieldView
	extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView {

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
	public void refresh() {
		getTextEditText().setText(getField().toFormattedString());
		if (getField().isUploaded()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.westeros_done_tinted, 0);
			getProgressBar().setVisibility(View.GONE);
		}
		else if (getField().isUploadFailed()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.westeros_highlight_remove_tinted, 0);
			getProgressBar().setVisibility(View.GONE);
		}
		else if (getField().isUploading()) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			getProgressBar().setVisibility(View.VISIBLE);
		}
		else {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.westeros_perm_media_tinted, 0);
			getProgressBar().setVisibility(View.GONE);
		}
	}

}