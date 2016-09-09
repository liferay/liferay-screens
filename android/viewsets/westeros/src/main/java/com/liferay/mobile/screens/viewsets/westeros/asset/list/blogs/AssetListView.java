package com.liferay.mobile.screens.viewsets.westeros.asset.list.blogs;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
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
		return R.layout.asset_list_item_westeros_blogs_item;
	}
}
