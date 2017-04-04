package com.liferay.mobile.screens.demoform.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.material.ddl.form.DDLFormView;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.Map;
import rx.subjects.PublishSubject;

public class TrackedDDLFormView extends DDLFormView implements RecyclerViewPager.OnPageChangedListener {

	private PublishSubject publishSubject = PublishSubject.create();
	private Map<Field, Boolean> pendingErrors = null;

	public TrackedDDLFormView(Context context) {
		super(context);
	}

	public TrackedDDLFormView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public TrackedDDLFormView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	@SuppressLint("MissingSuperCall")
	protected void onFinishInflate() {
		setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
		addOnPageChangedListener(this);
	}

	@Override
	public void showFormFields(Record record) {
		setAdapter(new HorizontalViewPagerAdapter(record));
	}

	@Override
	public void showStartOperation(String actionName, Object argument) {
	}

	@Override
	public void showFinishOperation(String actionName, Object argument) {
		switch (actionName) {
			case DDLFormScreenlet.LOAD_RECORD_ACTION:
				LiferayLogger.i("loaded record");
				showRecordValues();
				break;
			case DDLFormScreenlet.LOAD_FORM_ACTION:
			default:
				LiferayLogger.i("loaded form");
				Record record = (Record) argument;
				showFormFields(record);
				break;
		}
	}

	@Override
	public void showFailedOperation(String actionName, Exception e, Object argument) {
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			LiferayLogger.e("error loading DDLForm", e);
			clearFormFields();
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == com.liferay.mobile.screens.R.id.liferay_form_submit) {
			if (getDDLFormScreenlet().validateForm()) {
				getDDLFormScreenlet().submitForm();
			}
		} else if (view.getId() == R.id.next_page_button) {
			smoothScrollToPosition(getCurrentPosition() + 1);
		} else {
			getDDLFormScreenlet().startUpload((DocumentField) view.getTag());
		}
	}

	@Override
	public void showValidationResults(final Map<Field, Boolean> fieldResults, final boolean autoscroll) {

		boolean scrolled = checkPage(fieldResults);

		if (!scrolled) {
			Record record = getDDLFormScreenlet().getRecord();
			for (Field field : record.getFields()) {
				boolean isFieldValid = fieldResults.get(field);
				if (!isFieldValid) {
					smoothScrollToPosition(record.getPage(field));
					this.pendingErrors = fieldResults;
					return;
				}
			}
		}
	}

	private boolean checkPage(Map<Field, Boolean> fieldResults) {

		LinearLayout container = (LinearLayout) findViewById(R.id.ddlfields_container);

		for (int i = 0; i < container.getChildCount(); i++) {
			View fieldView = container.getChildAt(i);
			DDLFieldViewModel fieldViewModel = (DDLFieldViewModel) fieldView;
			boolean isFieldValid = fieldResults.get(fieldViewModel.getField());

			fieldView.clearFocus();

			fieldViewModel.onPostValidation(isFieldValid);

			if (!isFieldValid) {
				fieldView.requestFocus();
				scrollTo(0, fieldView.getTop());
				//smoothScrollTo(0, fieldView.getTop());
				return true;
			}
		}
		return false;
	}

	@Override
	public PublishSubject getEventsObservable() {
		return publishSubject;
	}

	@Override
	public void OnPageChanged(int oldPosition, int newPosition) {
		if (pendingErrors != null) {
			boolean scroll = checkPage(pendingErrors);
			if (scroll) {
				pendingErrors = null;
			}
		}
	}

	private class HorizontalViewPagerAdapter
		extends RecyclerView.Adapter<HorizontalViewPagerAdapter.HorizontalViewHolder> {

		private final Record record;

		HorizontalViewPagerAdapter(Record record) {
			this.record = record;
		}

		@Override
		public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(getContext()).inflate(R.layout.ddl_page_layout, parent, false);
			return new HorizontalViewHolder(view);
		}

		@Override
		public void onBindViewHolder(HorizontalViewHolder holder, int position) {
			holder.paint(record.getPages().get(position));
		}

		@Override
		public int getItemCount() {
			return record.getPages().size();
		}

		class HorizontalViewHolder extends RecyclerView.ViewHolder {

			private final LinearLayout container;
			private final TextView pageTitleTextView;
			private final TextView pageNumberTextView;
			private final Button nextPageButton;

			HorizontalViewHolder(View itemView) {
				super(itemView);

				container = (LinearLayout) itemView.findViewById(R.id.ddlfields_container);
				submitButton = (Button) itemView.findViewById(R.id.liferay_form_submit);
				submitButton.setOnClickListener(TrackedDDLFormView.this);
				nextPageButton = (Button) itemView.findViewById(R.id.next_page_button);
				nextPageButton.setOnClickListener(TrackedDDLFormView.this);
				pageTitleTextView = (TextView) itemView.findViewById(R.id.page_title);
				pageNumberTextView = (TextView) itemView.findViewById(R.id.page_number);
			}

			void paint(final Record.Page page) {

				for (Field field : page.getFields()) {

					int fieldLayoutId = getFieldLayoutId(field.getEditorType());
					View view =
						LayoutInflater.from(getContext()).inflate(fieldLayoutId, TrackedDDLFormView.this, false);
					DDLFieldViewModel viewModel = (DDLFieldViewModel) view;

					viewModel.setField(field);
					viewModel.setParentView(TrackedDDLFormView.this);
					viewModel.getObservable().subscribe(publishSubject);

					container.addView(view);
				}

				pageTitleTextView.setText(page.getTitle());
				String text = page.getNumber() + 1 + "/" + getItemCount();
				pageNumberTextView.setText(text);

				boolean isLastPage = page.getNumber() == record.getPages().size() - 1;
				submitButton.setVisibility(isLastPage ? VISIBLE : INVISIBLE);
				nextPageButton.setVisibility(!isLastPage ? VISIBLE : INVISIBLE);
			}
		}
	}

	public void clearFocusOfFields(DDLFieldViewModel ddlFieldViewModel) {
		LinearLayout container = (LinearLayout) findViewById(R.id.ddlfields_container);
		for (int i = 0; i < container.getChildCount(); i++) {
			DDLFieldViewModel viewModel = (DDLFieldViewModel) container.getChildAt(i);
			viewModel.clearFocus(ddlFieldViewModel);
		}
	}
}
