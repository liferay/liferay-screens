package com.liferay.mobile.screens.demoform.views;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.demoform.gestures.FlingListener;
import com.liferay.mobile.screens.demoform.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class UserDeck extends Deck {
	public UserDeck(Context context) {
		super(context);
	}

	public UserDeck(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UserDeck(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public UserDeck(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void moveCardRight(final Card card) {
		card.goRight(() -> {
			int selectedCard = cards.indexOf(card);

			for (int i = 0; i < cards.size(); i++) {
				Card c = cards.get(i);

				if (i == selectedCard) {
					c.setState(CardState.MAXIMIZED);
				} else if (i > selectedCard) {
					c.setState(CardState.HIDDEN);
				}
			}
		});
	}

	@Override
	protected void onFling(FlingListener.Movement movement, Card card) {
		if (card.getCardSubviewCurrentIndex() == 0) {
			super.onFling(movement, card);
		}
	}
}
