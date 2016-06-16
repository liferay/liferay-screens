package com.liferay.mobile.screens.bankofwesteros.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.bankofwesteros.gestures.FlingListener;
import com.liferay.mobile.screens.bankofwesteros.gestures.FlingTouchListener;
import com.liferay.mobile.screens.bankofwesteros.utils.Card;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * @author Javier Gamarra
 */
public abstract class CardActivity extends Activity implements View.OnClickListener {

	public static final int TOP_POSITION = 18;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTransparentMenuBar();
	}

	@Override
	protected void onStart() {
		super.onStart();

		findAndSetCardViews();

		if (_maxWidth != 0 && _maxHeight != 0) {
			animateScreenAfterLoad();
		}
		else {
			final View content = findViewById(android.R.id.content);
			content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						removeObserver();
					}
					else {
						content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}

					_maxWidth = content.getWidth();
					_maxHeight = content.getHeight();

					calculateHeightAndWidth();
					animateScreenAfterLoad();
				}

				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				private void removeObserver() {
					content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		if (v == _card1 || v == _card2SubViewToCard1 || v == _card2ToCard1) {
			toCard1(null);
		}
		else if (v == _card2) {
			toCard2();
		}
		else if (v == _card1ToFrontView) {
			goLeftCard1();
		}
		else if (v == _card2ToFrontView) {
			goLeftCard2();
		}
		else {
			toBackground();
		}
	}

	@Override
	public void onBackPressed() {
		if (_cardHistory.isEmpty() || _cardHistory.size() == 1) {
			super.onBackPressed();
		}
		else {
			toPreviousCard();
		}
	}

	public void animate(ViewGroup view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			startTransition(view);
		}
	}

	protected void findAndSetCardViews() {
		//TODO move this logic to custom view
		_card1 = (ViewGroup) findViewById(R.id.card1);
		_card1.setOnTouchListener(new FlingTouchListener(this, createCard1Listener()));
		_card2 = (ViewGroup) findViewById(R.id.card2);
		_card2.setOnTouchListener(new FlingTouchListener(this, createCard2Listener()));

		_card1Subview1 = (ViewGroup) findViewById(R.id.card1_subview1);
		_card1Subview2 = (ViewGroup) findViewById(R.id.card1_subview2);
		_card2Subview1 = (ViewGroup) findViewById(R.id.card2_subview1);
		_card2Subview2 = (ViewGroup) findViewById(R.id.card2_subview2);

		_card1ToBackground = findAndAddListener(_card1, "card1_to_background");
		_card1ToFrontView = findAndAddListener(_card1, "card1_to_front_view");
		_card1SubViewToBackground = findAndAddListener(_card1, "card1_subview_to_background");
		_card2ToCard1 = findAndAddListener(_card2, "card2_to_card1");
		_card2ToFrontView = findAndAddListener(_card2, "card2_to_front_view");
		_card2SubViewToCard1 = findAndAddListener(_card2, "card2_subview_to_card1");
	}

	protected void calculateHeightAndWidth() {
		_cardHeight = getResources().getDimensionPixelSize(R.dimen.westeros_card_title_height);
		_card1FoldedPosition = _maxHeight - 2 * _cardHeight;
		_card2FoldedPosition = _maxHeight - _cardHeight;
		_card1RestPosition = 0;

		if (_card1Subview2 != null) {
			_card1Subview2.setX(_maxWidth);
		}
		if (_card2Subview2 != null) {
			_card2Subview2.setX(_maxWidth);
		}
	}

	protected abstract void animateScreenAfterLoad();

	protected void toBackground() {
		hideArrowIcon(_card1SubViewToBackground);
		hideArrowIcon(_card1ToFrontView);
		hideArrowIcon(_card1ToBackground);

		_cardHistory.add(Card.BACKGROUND);

		_card1.animate().y(_card1FoldedPosition);
		setFrameLayoutMargins(_card1, 0, 0, 0, _cardHeight);
	}

	protected void toCard1() {
		toCard1(null);
	}

	protected void toCard1(Animator.AnimatorListener listener) {
		showArrowIcon(_card1ToBackground);
		showArrowIcon(_card1SubViewToBackground);
		showArrowIcon(_card1ToFrontView);

		hideArrowIcon(_card2ToCard1);
		hideArrowIcon(_card2SubViewToCard1);
		hideArrowIcon(_card2ToFrontView);

		_cardHistory.add(Card.CARD1);

		animate(_card1);

		setFrameLayoutMargins(_card1, 0, 0, 0, _cardHeight);
		_card1.animate().y(_card1RestPosition);
		_card2.animate().y(_card2FoldedPosition).setListener(listener);
	}

	protected void toCard2() {
		showArrowIcon(_card2ToCard1);
		showArrowIcon(_card2SubViewToCard1);
		showArrowIcon(_card2ToFrontView);

		_cardHistory.add(Card.CARD2);

		_card2.animate().setListener(null);
		int topPosition = convertDpToPx(TOP_POSITION);
		int margin = topPosition / 2;
		_card2.animate().y(topPosition);

		animate(_card1);
		setFrameLayoutMargins(_card1, margin, 0, margin, _cardHeight);
		_card1.animate().y(0);
	}

	protected void goRightCard1() {
		goRight(_card1Subview1, _card1Subview2);
	}

	protected void goRightCard2() {
		goRight(_card2Subview1, _card2Subview2);
	}

	protected void goLeftCard1() {
		goLeft(_card1Subview1, _card1Subview2);
	}

	protected void goLeftCard2() {
		goLeft(_card2Subview1, _card2Subview2);
	}

	protected void goLeft(View leftView, View rightView) {
		if (leftView != null && rightView != null) {
			leftView.animate().x(0);
			rightView.animate().x(_maxWidth);
		}
	}

	protected void goRight(View leftView, View rightView) {
		if (leftView != null && rightView != null) {
			leftView.animate().x(-_maxWidth);
			rightView.animate().x(0);
		}
	}

	protected int convertDpToPx(int dp) {
		Resources resources = getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
	}

	private void setTransparentMenuBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setStatusBar();
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void setStatusBar() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().setStatusBarColor(getResources().getColor(R.color.background_gray_westeros));
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void startTransition(ViewGroup view) {
		TransitionManager.beginDelayedTransition(view);
	}

	private FlingListener createCard1Listener() {
		return new FlingListener() {
			@Override
			public void onFling(Movement movement) {
				switch (movement) {
					case UP:
					case TOUCH:
						toCard1();
						break;
					case DOWN:
						toBackground();
						break;
					case LEFT:
						goLeftCard1();
						break;
					case RIGHT:
						goRightCard1();
						break;
				}
			}
		};
	}

	private FlingListener createCard2Listener() {
		//TODO delete when activity supports n number of cards
		return new FlingListener() {
			@Override
			public void onFling(Movement movement) {
				switch (movement) {
					case UP:
					case TOUCH:
						toCard2();
						break;
					case DOWN:
						toCard1();
						break;
					case LEFT:
						goLeftCard2();
						break;
					case RIGHT:
						goRightCard2();
						break;
				}
			}
		};
	}


	private void showArrowIcon(ImageView view) {
		showOrHideView(view, VISIBLE);
	}

	private void hideArrowIcon(ImageView view) {
		showOrHideView(view, INVISIBLE);
	}

	private void showOrHideView(ImageView view, int visibility) {
		if (view != null) {
			view.setVisibility(visibility);
		}
	}

	private ImageView findAndAddListener(ViewGroup viewGroup, String tag) {
		ImageView view = (ImageView) viewGroup.findViewWithTag(tag);
		if (view != null) {
			view.setOnClickListener(this);
		}
		return view;
	}

	private void setFrameLayoutMargins(View view, int marginLeft, int marginTop,
									   int marginRight, int marginBottom) {
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
		layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		view.setLayoutParams(layoutParams);
	}

	private void toPreviousCard() {
		_cardHistory.poll();

		//INFO this should be generic and calculated via current card
		Card previousCard = _cardHistory.peek();
		if (previousCard == Card.CARD2) {
			toCard2();
		}
		else if (previousCard == Card.CARD1) {
			toCard1();
		}
		else {
			toBackground();
		}

		//we have to remove from the queue the last back movement
		_cardHistory.poll();
	}

	protected int _maxWidth;
	protected int _maxHeight;
	protected int _card1RestPosition;
	protected int _card1FoldedPosition;
	protected int _card2FoldedPosition;

	protected ViewGroup _card1;
	protected ViewGroup _card2;

	protected Queue<Card> _cardHistory = Collections.asLifoQueue(new ArrayDeque<Card>());

	private int _cardHeight;
	private ViewGroup _card1Subview1;
	private ViewGroup _card1Subview2;
	private ViewGroup _card2Subview1;
	private ViewGroup _card2Subview2;

	private ImageView _card1ToBackground;
	private ImageView _card1ToFrontView;
	private ImageView _card1SubViewToBackground;
	private ImageView _card2ToCard1;
	private ImageView _card2ToFrontView;
	private ImageView _card2SubViewToCard1;

}