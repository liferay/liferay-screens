package com.liferay.mobile.screens.westerosemployees.Views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingListener;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingTouchListener;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;
import com.liferay.mobile.screens.westerosemployees.utils.PixelUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public class Deck extends FrameLayout {
	public Deck(Context context) {
		super(context);
	}

	public Deck(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Deck(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public Deck(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void setCardsState(CardState state) {
		for (Card card : cards){
			card.setState(state);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		initializeSize();
	}

	private void initializeSize() {
		if (maxWidth != 0 && maxHeight != 0) {
			initializeDeckAndCards();
		} else {
			getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						removeObserver();
					} else {
						getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}

					maxWidth = getWidth();
					maxHeight = getHeight();

					initializeDeckAndCards();
				}

				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				private void removeObserver() {
					getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			});
		}
	}

	protected void initializeDeckAndCards() {

		fillDeck(this);
		initCards();
	}

	protected void fillDeck(ViewGroup content) {

		for (int i = 0; i < content.getChildCount(); i++) {
			View view = content.getChildAt(i);
			if (view instanceof Card) {
				cards.add((Card) view);
			}
		}
	}

	protected void initCards() {
		for (int i = 0, size = cards.size(); i < size; i++) {
			final Card card = cards.get(i);

			int cardSize = PixelUtil.pixelFromDp(getContext(), Card.CARD_SIZE);
			int minimizedPosition = maxHeight - (size - i) * cardSize;

			card.initPosition(minimizedPosition);
			card.setOnTouchListener(new FlingTouchListener(getContext().getApplicationContext(), new FlingListener() {
				@Override
				public void onFling(FlingListener.Movement movement) {
					Deck.this.onFling(movement, card);
				}
			}));
		}
	}

	protected void onClick(Card card) {
		changeState(card);
	}

	protected void changeState(Card card) {
		// Get the index of the selected card
		int indexSelected = cards.indexOf(card);

		for (int i = 0, size = cards.size(); i < size; i++) {
			Card view = cards.get(i);

			if (indexSelected > i) {
				view.setState(CardState.BACKGROUND);
			}

			if (i == indexSelected) {
				if (view.getCardState() == CardState.NORMAL) {
					if (i != 0) {
						cards.get(i - 1).setState(CardState.NORMAL);
					}

					view.setState(CardState.MINIMIZED);
				} else {
					view.setState(CardState.NORMAL);
				}
			}

			if (indexSelected < i) {
				view.setState(CardState.MINIMIZED);
			}
		}
	}

	protected void onFling(FlingListener.Movement movement, Card card) {
		switch (movement) {
			case TOUCH:
				onClick(card);
				break;
			case RIGHT:
				card.goRight();
				break;
			case LEFT:
				card.goLeft();
		}
	}

	protected List<Card> cards = new ArrayList<>();

	private int maxWidth;
	private int maxHeight;
}
