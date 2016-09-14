package com.liferay.mobile.screens.viewsets.westeros.asset.list.lastchanges;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Víctor Galán Grande
 */
public class AssetListView
	extends BaseListScreenletView<AssetEntry, AssetListAdapter.AssetListViewHolder, AssetListAdapter> {
	public AssetListView(Context context) {
		super(context);
	}

	public AssetListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected AssetListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new AssetListAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.asset_list_item_westeros_last_changes_item;
	}

	protected RecyclerView.ItemDecoration getDividerDecoration() {
		Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.pixel_westeros);

		return new DividerItemDecoration(drawable);
	}
}
