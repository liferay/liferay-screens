package com.liferay.mobile.screens.westerosemployees.activities;

import android.app.Activity;
import android.os.Bundle;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.westerosemployees.Views.Card;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingListener;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class UserActivity extends DeckActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user);

		SessionContext.createBasicSession("test@liferay.com","test");
	}

	@Override
	protected void onFling(FlingListener.Movement movement, final Card card) {
		if(movement == FlingListener.Movement.RIGHT) {

			card.goRight(new Runnable() {
				@Override
				public void run() {
					int selectedCard = cards.indexOf(card);

					for(int i = 0; i < cards.size(); i++) {
						Card c = cards.get(i);

						if(i == selectedCard) {
							c.setState(CardState.MAXIMIZED);
						}
						else if(i > selectedCard) {
							c.setState(CardState.HIDDEN);
						}
					}
				}
			});
		}

		else if(movement == FlingListener.Movement.LEFT) {
			card.goLeft();

			if (card.getCardSubviewCurrentIndex() == 0) {
				int selectedCard = cards.indexOf(card);

				for(int i = 0; i < cards.size(); i++) {
					Card c = cards.get(i);

					if(i == selectedCard) {
						c.setState(CardState.NORMAL);
					}
					else if(i > selectedCard) {
						c.setState(CardState.MINIMIZED);
					}
				}
			}
		}

		else if (movement == FlingListener.Movement.TOUCH && card.getCardSubviewCurrentIndex() == 0) {
			super.onFling(movement, card);
		}
	}
}
