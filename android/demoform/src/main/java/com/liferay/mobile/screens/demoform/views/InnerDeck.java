package com.liferay.mobile.screens.demoform.views;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.demoform.utils.CardState;
import com.liferay.mobile.screens.demoform.utils.ViewUtil;

/**
 * @author Víctor Galán Grande
 */
public class InnerDeck extends Deck {

	private boolean heightNotified;

	public InnerDeck(Context context) {
		super(context);
	}

	public InnerDeck(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerDeck(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public InnerDeck(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void heightChanged(int newHeight) {

		if (heightNotified) {
			return;
		}

		heightNotified = true;
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
