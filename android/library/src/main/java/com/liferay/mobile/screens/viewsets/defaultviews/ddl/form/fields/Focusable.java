package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.view.View;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.form.EventType;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormView;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class Focusable {

	private final DDLFieldViewModel ddlFieldViewModel;
	private PublishSubject<EventType> focusChange = PublishSubject.create();
	private boolean focused;
	private long timer;

	public Focusable(DDLFieldViewModel ddlFieldViewModel) {
		this.ddlFieldViewModel = ddlFieldViewModel;
	}

	public void focusField() {
		timer = System.currentTimeMillis();
		if (!focused) {
			focused = true;

			DDLFormView ddlFormView = (DDLFormView) ddlFieldViewModel.getParentView();
			ddlFormView.clearFocusOfFields(ddlFieldViewModel);
			focusChange.onNext(EventType.FIELD_ENTER);
		}
	}

	public void clearFocus(DDLFieldViewModel ddlFieldViewModelSelected) {
		if (this.ddlFieldViewModel != ddlFieldViewModelSelected && focused) {
			focused = false;
			focusChange.onNext(EventType.FIELD_LEAVE);
			//((View) ddlFieldViewModel).clearFocus();
		}
	}

	public void clearFocus() {
		clearFocus(null);
	}

	public Observable<EventProperty> getObservable() {
		return Observable.interval(100, TimeUnit.MILLISECONDS).filter(new Func1<Long, Boolean>() {
			@Override
			public Boolean call(Long aLong) {
				return focused && getTime() > Field.RATE_FIELD;
			}
		}).map(new Func1() {
			@Override
			public Object call(Object o) {
				return EventType.FIELD_EXHAUSTED;
			}
		}).mergeWith(focusChange).distinctUntilChanged().map(new Func1() {
			@Override
			public EventProperty call(Object o) {
				return new EventProperty((EventType) o, ddlFieldViewModel.getField().getName(), getTime());
			}
		});
	}

	public long getTime() {
		return System.currentTimeMillis() - timer;
	}
}
