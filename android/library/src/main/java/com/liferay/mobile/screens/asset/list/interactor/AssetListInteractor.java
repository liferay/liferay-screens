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

package com.liferay.mobile.screens.asset.list.interactor;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.AssetEvent;
import com.liferay.mobile.screens.asset.AssetFactory;
import com.liferay.mobile.screens.asset.list.connector.AssetEntryConnector;
import com.liferay.mobile.screens.asset.list.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class AssetListInteractor extends BaseListInteractor<BaseListInteractorListener<AssetEntry>, AssetEvent> {

    public BaseListEvent<AssetEvent> execute(Query query, Object... args) throws Exception {

        if (notRequestingRightNow(query)) {

            long classNameId = (long) args[0];
            HashMap<String, Object> customEntryQuery = (HashMap<String, Object>) args[1];
            String portletItemName = (String) args[2];

            validate(query.getStartRow(), query.getEndRow(), locale, portletItemName, classNameId);

            JSONArray jsonArray = getEntries(query, classNameId, customEntryQuery, portletItemName);
            int rowCount = getCount(classNameId, customEntryQuery, portletItemName, jsonArray);

            List<AssetEvent> entries = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                entries.add(createEntity(JSONUtil.toMap(jsonObject)));
            }
            return new BaseListEvent<>(query, entries, rowCount);
        }
        return null;
    }

    private JSONArray getEntries(Query query, long classNameId, Map<String, Object> customEntryQuery,
        String portletItemName) throws Exception {
        if (portletItemName == null) {

            ScreensAssetEntryConnector connector =
                ServiceProvider.getInstance().getScreensAssetEntryConnector(getSession());
            JSONObject entryQueryAttributes = configureEntryQuery(groupId, classNameId, customEntryQuery);
            entryQueryAttributes.put("start", query.getStartRow());
            entryQueryAttributes.put("end", query.getEndRow());

            JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryAttributes);

            return connector.getAssetEntries(entryQuery, locale.toString());
        } else {
            ScreensAssetEntryConnector connector =
                ServiceProvider.getInstance().getScreensAssetEntryConnector(getSession());
            return connector.getAssetEntries(LiferayServerContext.getCompanyId(), groupId, portletItemName,
                locale.toString(), query.getEndRow());
        }
    }

    private int getCount(long classNameId, Map<String, Object> customEntryQuery, String portletItemName,
        JSONArray jsonArray) throws Exception {
        if (portletItemName == null) {
            JSONObject entryQueryParams = configureEntryQuery(groupId, classNameId, customEntryQuery);
            JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryParams);
            AssetEntryConnector connector = ServiceProvider.getInstance().getAssetEntryConnector(getSession());
            return connector.getEntriesCount(entryQuery);
        } else {
            return jsonArray.length();
        }
    }

    @Override
    protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
        throw new AssertionError("Should not be called!");
    }

    @Override
    protected Integer getPageRowCountRequest(Object... args) throws Exception {
        throw new AssertionError("Should not be called!");
    }

    @Override
    protected AssetEvent createEntity(Map<String, Object> stringObjectMap) {
        return new AssetEvent(AssetFactory.createInstance(stringObjectMap));
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        long classNameId = (long) args[0];
        String portletItemName = (String) args[2];

        return portletItemName == null ? String.valueOf(classNameId) : portletItemName;
    }

    protected JSONObject configureEntryQuery(long groupId, long classNameId, Map<String, Object> customEntryQuery)
        throws JSONException {

        JSONObject entryQueryParams = customEntryQuery == null ? new JSONObject() : new JSONObject(customEntryQuery);

        if (!entryQueryParams.has("classNameIds")) {
            entryQueryParams.put("classNameIds", classNameId);
        }
        if (!entryQueryParams.has("groupIds")) {
            entryQueryParams.put("groupIds", groupId);
        }
        if (!entryQueryParams.has("visible")) {
            entryQueryParams.put("visible", true);
        }

        if (classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_ddl_form_record))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_layout))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_mb_thread))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_organization))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_site))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_user))) {

            handleVisibleFlag(classNameId, entryQueryParams);
        }

        return entryQueryParams;
    }

    protected void validate(int startRow, int endRow, Locale locale, String portletItemName, long classNameId) {

        if (groupId <= 0) {
            throw new IllegalArgumentException("GroupId cannot be 0 or negative");
        } else if (portletItemName == null && classNameId <= 0) {
            throw new IllegalArgumentException("ClassNameId cannot be 0 or negative");
        }

        super.validate(startRow, endRow, locale);
    }

    /**
     * AssetListScreenlet only list Asset with visible attribute set to true. But for example User,
     * have it by default in true. This method update this attribute of entryQuery.
     *
     * @param classNameId identifier of the asset’s class name.
     * @param entryQueryParams initial entryQuery parameters.
     * @return final entryQuery parameters.
     * @throws JSONException when can't parse the result
     */
    private JSONObject handleVisibleFlag(long classNameId, JSONObject entryQueryParams) throws JSONException {
        entryQueryParams.put("visible", false);

        return entryQueryParams;
    }
}
