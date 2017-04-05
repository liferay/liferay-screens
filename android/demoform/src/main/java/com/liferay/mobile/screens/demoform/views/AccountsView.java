package com.liferay.mobile.screens.demoform.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;

public class AccountsView
	extends BaseListScreenletView<Record, AccountsView.AccountsAdapter.AccountsViewHolder, AccountsView.AccountsAdapter>
	implements DDLListViewModel {

	public AccountsView(Context context) {
		super(context);
	}

	public AccountsView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AccountsView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected AccountsAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new AccountsAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.accounts_row;
	}

	@Override
	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_material;
	}

	public class AccountsAdapter extends BaseListAdapter<Record, AccountsAdapter.AccountsViewHolder> {

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
			String updated = "Last transaction on " + entry.getServerValue("Updated");
			holder.subtitleTextView.setText(updated);
			String type = (String) entry.getServerValue("type");
			holder.accountsIcon.setImageResource(getIcon(type));
			String balance = entry.getServerValue("Balance") + "$";
			holder.balanceText.setText(balance);
		}

		private int getIcon(String type) {
			if ("[\"value 1\"]".equals(type)) {
				return R.drawable.ic_account_balance_black_24dp;
			} else if ("[\"value 2\"]".equals(type)) {
				return R.drawable.ic_attach_money_black_24dp;
			}
			return R.drawable.ic_credit_card_black_24dp;
		}

		public class AccountsViewHolder extends BaseListAdapter.ViewHolder {

			protected final TextView subtitleTextView;
			protected final ImageView accountsIcon;
			protected final TextView balanceText;

			public AccountsViewHolder(View view, BaseListAdapterListener listener) {
				super(view, listener);

				this.subtitleTextView = (TextView) view.findViewById(R.id.liferay_list_subtitle);
				this.accountsIcon = (ImageView) view.findViewById(R.id.accounts_icon);
				this.balanceText = (TextView) view.findViewById(R.id.balance_text);
			}
		}
	}
}