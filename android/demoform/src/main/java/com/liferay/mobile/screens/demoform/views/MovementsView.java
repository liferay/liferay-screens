package com.liferay.mobile.screens.demoform.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovementsView
	extends BaseListScreenletView<Record, MovementsView.AccountsViewHolder, MovementsView.AccountsAdapter>
	implements DDLListViewModel {

	public MovementsView(Context context) {
		super(context);
	}

	public MovementsView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public MovementsView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected AccountsAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new AccountsAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.movements_row;
	}

	@Override
	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_material;
	}

	public class AccountsAdapter extends BaseListAdapter<Record, AccountsViewHolder> {

		public AccountsAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
			super(layoutId, progressLayoutId, listener);
		}

		@NonNull
		@Override
		public AccountsViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
			return new AccountsViewHolder(view, listener);
		}

		@Override
		protected void fillHolder(Record entry, AccountsViewHolder holder) {

			String titleField = (String) entry.getServerValue(getLabelFields().get(0));
			holder.textView.setText(titleField);

			String balance = entry.getServerValue("Balance") + " $";
			holder.balanceText.setText(balance);
			try {
				String updated1 = (String) entry.getServerValue("Updated");
				Date updated = new SimpleDateFormat("yyyy-MM-dd").parse(updated1);

				holder.subtitleTextView.setText("" + (updated.getDay()) + "/" + updated.getMonth() + 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public class AccountsViewHolder extends BaseListAdapter.ViewHolder {

		protected final TextView subtitleTextView;
		protected final TextView balanceText;

		public AccountsViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			this.subtitleTextView = (TextView) view.findViewById(R.id.liferay_list_subtitle);
			this.balanceText = (TextView) view.findViewById(R.id.balance_text);
		}
	}
}