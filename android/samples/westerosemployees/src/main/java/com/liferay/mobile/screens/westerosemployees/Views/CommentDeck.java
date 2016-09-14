package com.liferay.mobile.screens.westerosemployees.Views;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;
import com.liferay.mobile.screens.westerosemployees.utils.ViewUtil;

/**
 * @author Víctor Galán Grande
 */
public class CommentDeck extends Deck {
	public CommentDeck(Context context) {
		super(context);
	}

	public CommentDeck(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentDeck(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentDeck(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void heightChanged(int newHeight) {

		maxHeight = newHeight;

		for (int i = 0, size = cards.size(); i < size; i++) {
			final Card card = cards.get(i);

			int cardSize = ViewUtil.pixelFromDp(getContext(), Card.CARD_SIZE);
			int minimizedPosition = maxHeight - (size - i) * cardSize;

			card.initPosition(minimizedPosition, maxHeight, maxWidth);
			card.setState(CardState.MINIMIZED);
		}
	}
}
