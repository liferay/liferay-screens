package com.liferay.mobile.screens.westerosemployees.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.westerosemployees.utils.ViewUtil;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public class DocsCard extends CommentsRatingsCard implements BaseListListener<AssetEntry> {

	private AssetListScreenlet docsListScreenlet;
	private AssetDisplayScreenlet documentDisplayScreenlet;

	public DocsCard(Context context) {
		super(context);
	}

	public DocsCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DocsCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DocsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<AssetEntry> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(AssetEntry element, View view) {

		documentDisplayScreenlet.setClassPK(Long.parseLong((String) element.getValues().get("classPK")));
		documentDisplayScreenlet.setClassName("com.liferay.document.library.kernel.model.DLFileEntry");
		documentDisplayScreenlet.loadAsset();

		initializeRatingsAndComments("com.liferay.document.library.kernel.model.DLFileEntry", element.getClassPK());

		cardListener.moveCardRight(this);
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		docsListScreenlet = (AssetListScreenlet) findViewById(R.id.asset_list_screenlet_docs);
		docsListScreenlet.setListener(this);

		documentDisplayScreenlet = (AssetDisplayScreenlet) findViewById(R.id.asset_display_screenlet_doc);
	}
}
