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

package com.liferay.mobile.screens.ddl.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.LocalValidator;
import com.liferay.mobile.screens.ddl.exception.EmptyDocumentRemoteFileException;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.Locale;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

/**
 * @author Javier Gamarra
 */
public class DocumentField extends Field<DocumentFile> {

    public static final Parcelable.ClassLoaderCreator<DocumentField> CREATOR =
        new Parcelable.ClassLoaderCreator<DocumentField>() {

            @Override
            public DocumentField createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new DocumentField(parcel, classLoader);
            }

            public DocumentField createFromParcel(Parcel in) {
                throw new AssertionError();
            }

            public DocumentField[] newArray(int size) {
                return new DocumentField[size];
            }
        };
    private State state = State.IDLE;

    public DocumentField() {
        super();
    }

    public DocumentField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);
    }

    protected DocumentField(Parcel in, ClassLoader classLoader) {
        super(in, classLoader);
    }

    public boolean moveToUploadInProgressState() {
        if (state == State.IDLE || state == State.FAILED || state == State.UPLOADED) {
            state = State.UPLOADING;
            return true;
        }

        return false;
    }

    public boolean moveToUploadCompleteState() {
        if (state == State.UPLOADING) {
            state = State.UPLOADED;
            return true;
        }

        return false;
    }

    public boolean moveToUploadFailureState() {
        if (state == State.UPLOADING) {
            state = State.FAILED;
            return true;
        }

        return false;
    }

    public boolean isUploaded() {
        return (state == State.UPLOADED);
    }

    public boolean isUploading() {
        return (state == State.UPLOADING);
    }

    public boolean isUploadFailed() {
        return (state == State.FAILED);
    }

    public void createLocalFile(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        setCurrentValue(new DocumentLocalFile(path));
    }

    @Override
    protected DocumentFile convertFromString(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }

        DocumentRemoteFile result = null;

        try {
            result = new DocumentRemoteFile(string);
        } catch (EmptyDocumentRemoteFileException e) {
            LiferayLogger.i(e.getMessage());
        } catch (JSONException e) {
            LiferayLogger.e("Can't parse the document JSON", e);
        }

        return result;
    }

    @Override
    protected String convertToData(DocumentFile document) {
        return document == null ? null : document.toData();
    }

    @Override
    protected String convertToFormattedString(DocumentFile document) {
        return document == null ? "" : document.getFileName();
    }

    @Override
    protected boolean doValidate() {
        DocumentFile currentValue = getCurrentValue();
        if (currentValue == null && isRequired() || currentValue != null && !currentValue.isValid()) {
            setValidationState(ValidationState.INVALID_BY_REQUIRED_WITHOUT_VALUE);
            return false;
        }

        if (isUploading() || isUploadFailed()) {
            setValidationState(ValidationState.INVALID_BY_LOCAL_RULE);
            return false;
        }
        return true;
    }

    @Nullable
    @Override
    public LocalValidator getLocalValidator() {
        return documentFieldValidator;
    }

    private LocalValidator documentFieldValidator = new LocalValidator() {
        @NotNull
        @Override
        public String getCustomErrorMessage() {
            Context context = LiferayScreensContext.getContext();
            String isUploadingErrorMsg = context.getString(R.string.uploading_document_not_finished_error);
            String isUploadFailed = context.getString(R.string.uploading_document_error);

            return (isUploading() ? isUploadingErrorMsg : isUploadFailed);
        }
    };

    private enum State {
        IDLE, UPLOADING, UPLOADED, FAILED
    }
}
