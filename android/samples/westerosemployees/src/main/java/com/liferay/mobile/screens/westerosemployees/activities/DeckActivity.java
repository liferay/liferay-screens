package com.liferay.mobile.screens.westerosemployees.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.westerosemployees.Views.Card;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingListener;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingTouchListener;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * @author Víctor Galán Grande
 */
public class DeckActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cards = new ArrayList<>();
	}

	@Override
	protected void onStart() {
		super.onStart();

		calculateSize();
	}

	@Override
	public void onBackPressed() {
		if (cardHistory.isEmpty()) {
			super.onBackPressed();
		} else {
			toPreviousCard();
		}
	}

	protected void calculateSize() {
		if (maxWidth != 0 && maxHeight != 0) {
			initializeDeckAndCards();
		} else {
			final View content = findViewById(android.R.id.content);
			content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						removeObserver();
					} else {
						content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}

					maxWidth = content.getWidth();
					maxHeight = content.getHeight();

					initializeDeckAndCards();
				}

				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				private void removeObserver() {
					content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			});
		}
	}

	protected void initializeDeckAndCards() {
		FrameLayout base = (FrameLayout) findViewById(android.R.id.content);

		FrameLayout content = (FrameLayout) base.getChildAt(0);

		fillDeck(content);
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

			int minimizedPosition = maxHeight - (size - i) * Card.CARD_SIZE;

			card.initPosition(minimizedPosition);
			card.setOnTouchListener(new FlingTouchListener(this, new FlingListener() {
				@Override
				public void onFling(Movement movement) {
					DeckActivity.this.onFling(movement, card);
				}
			}));
		}
	}

	protected void onClick(Card card) {
		changeState(card);
		cardHistory.add(card);
	}

	protected void changeState(Card card) {
		// Get the index of the selected card
		int indexSelected = cards.indexOf(card);

		for (int i = 0, size = cards.size(); i < size; i++) {
			Card view = cards.get(i);

			if (indexSelected > i) {
				int depth = indexSelected - i;
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

	protected void toPreviousCard() {
		Card card = cardHistory.poll();

		changeState(card);
	}

	protected List<Card> cards;
	protected Queue<Card> cardHistory = Collections.asLifoQueue(new ArrayDeque<Card>());

	protected int maxWidth;
	protected int maxHeight;
}
