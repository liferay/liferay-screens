/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormPagerViewAdapter extends PagerAdapter {

    private final List<Field> fields;
    private final Map<Field.EditorType, Integer> layoutIds;

    public DDLFormPagerViewAdapter(List<Field> fields, Map<Field.EditorType, Integer> layoutIds) {

        this.fields = fields;
        this.layoutIds = layoutIds;
    }

    @Override
    public int getCount() {
        return fields.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();

        Field field = fields.get(position);
        Field.EditorType type = field.getEditorType();
        int layoutId = layoutIds.get(type);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, container, false);

        DDLFieldViewModel viewModel = (DDLFieldViewModel) view;
        viewModel.setField(field);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}