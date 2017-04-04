package com.liferay.mobile.screens.demoform.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.gestures.FlingListener;
import com.liferay.mobile.screens.demoform.gestures.FlingTouchListener;
import com.liferay.mobile.screens.demoform.utils.CardState;
import com.liferay.mobile.screens.demoform.utils.ViewUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public class Deck extends FrameLayout implements CardListener {
	protected final List<Card> cards = new ArrayList<>();
	protected int maxWidth;
	protected int maxHeight;

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
		for (Card card : cards) {
			card.setState(state);
		}
	}

	@Override
	public void moveCardRight(Card card) {
		card.goRight();
	}

	@Override
	public void moveCardLeft(Card card) {
		card.goLeft();
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
		initBackArrows();
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

			int cardSize = ViewUtil.pixelFromDp(getContext(), Card.CARD_SIZE);
			int minimizedPosition = maxHeight - (size - i) * cardSize;

			card.initPosition(minimizedPosition, maxHeight, maxWidth);
			card.setOnTouchListener(new FlingTouchListener(getContext().getApplicationContext(), new FlingListener() {
				@Override
				public void onFling(FlingListener.Movement movement) {
					Deck.this.onFling(movement, card);
				}
			}));

			card.setState(CardState.MINIMIZED).setStartDelay(200 * cards.size() - i - 1).setDuration(300);
			card.setCardListener(this);
		}
	}

	protected void initBackArrows() {
		List<View> backArrows = ViewUtil.getViewsByTagPrefix(this, getContext().getString(R.string.arrow_back_tag));

		for (View backArrow : backArrows) {
			backArrow.setOnClickListener(v -> {
				Card card = (Card) v.getParent().getParent();
				card.goLeft();

				if (card.getCardSubviewCurrentIndex() == 0) {
					int selectedCard = cards.indexOf(card);

					for (int i = 0; i < cards.size(); i++) {
						Card c = cards.get(i);

						if (i == selectedCard) {
							c.setState(CardState.NORMAL);
						} else if (i > selectedCard) {
							c.setState(CardState.MINIMIZED);
						}
					}
				}
			});
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
						minimizeAllCards();
						return;
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

	protected void minimizeAllCards() {
		for (Card card : cards) {
			card.setState(CardState.MINIMIZED);
		}
	}

	protected void onFling(FlingListener.Movement movement, Card card) {
		switch (movement) {
			default:
			case TOUCH:
				onClick(card);
				break;
		}
	}
}
