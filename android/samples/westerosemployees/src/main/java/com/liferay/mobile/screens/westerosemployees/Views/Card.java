package com.liferay.mobile.screens.westerosemployees.Views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;
import com.liferay.mobile.screens.westerosemployees.utils.ViewUtil;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */

public class Card extends FrameLayout {

	public static final int MARGIN = 5;
	public static final int DURATION_MILLIS = 300;
	public static final int CARD_SIZE = 60;

	public static final int NORMAL_Y = MARGIN * 7;
	public static final int BACKGROUND_Y = MARGIN;

	public Card(Context context) {
		super(context);
	}

	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public Card(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public Card(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs);
	}

	protected void init(Context context, AttributeSet attributeSet) {

		TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.Card);

		normalY = array.getDimensionPixelSize(R.styleable.Card_normalMarginTop,
			ViewUtil.pixelFromDp(getContext(), NORMAL_Y));

		array.recycle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		arrows = ViewUtil.getViewsByTagPrefix(this, getContext().getString(R.string.arrow_tag));
	}

	public CardState getCardState() {
		return cardState;
	}

	public int getCardSubviewCurrentIndex() {
		return index;
	}

	public void setCardListener(CardListener cardListener) {
		this.cardListener = cardListener;
	}

	public void initPosition(int minimizedPosition, int maxHeight, int maxWidth) {
		this.minimizedPosition = minimizedPosition;
		this.backgroundY = ViewUtil.pixelFromDp(getContext(), BACKGROUND_Y);
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth;

		setY(maxHeight);

		takeSubviewsOffScreen();
	}

	public ViewPropertyAnimator setState(CardState state) {

		ViewPropertyAnimator animator = null;

		switch (state) {
			case BACKGROUND:
				animator = animate().setStartDelay(0)
					.setDuration(DURATION_MILLIS)
					.scaleX(0.95f)
					.translationY(backgroundY);
				break;
			case NORMAL:
				showArrows(true);
				setNormalModeHeight();
				animator = animate().setStartDelay(0)
					.setDuration(DURATION_MILLIS)
					.scaleX(1f)
					.translationY(normalY);
				break;
			case MINIMIZED:
				showArrows(false);
				animator = animate().setStartDelay(0)
					.setDuration(DURATION_MILLIS)
					.scaleX(1f)
					.translationY(minimizedPosition);
				break;
			case MAXIMIZED:
				setMaximizedModeHeight();
				animator = animate().setStartDelay(0)
					.setDuration(DURATION_MILLIS)
					.translationY(0);
				break;

			case HIDDEN:
				animator = animate().setStartDelay(0)
					.setDuration(DURATION_MILLIS)
					.translationY(maxHeight);
				break;
		}

		cardState = state;

		return animator;
	}

	public void goRight() {
		goRight(null);
	}

	public void goRight(Runnable endAction) {

		View current = getChildAt(index);
		View next = getChildAt(index + 1);

		if (next != null) {
			index++;
			current.animate().translationX(-maxWidth);
			next.animate().translationX(0).withEndAction(endAction);
		}
	}

	public void goLeft() {
		goLeft(null);
	}

	public void goLeft(Runnable endAction) {

		View current = getChildAt(index);
		View next = getChildAt(index - 1);

		if (next != null) {
			index--;
			next.animate().translationX(0).withEndAction(endAction);
			current.animate().translationX(maxWidth);
		}
	}

	protected void setMaximizedModeHeight() {
		TransitionManager.beginDelayedTransition(this);
		ViewGroup.LayoutParams params = getLayoutParams();
		params.height = maxHeight;
		setLayoutParams(params);
	}

	protected void setNormalModeHeight() {
		TransitionManager.beginDelayedTransition(this);
		if (statusBarHeight == 0) {
			Rect rectangle = new Rect();
			Window window = ((Activity) getContext()).getWindow();
			window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
			statusBarHeight = rectangle.top;
		}

		ViewGroup.LayoutParams params = getLayoutParams();
		params.height = maxHeight - (maxHeight - minimizedPosition - ViewUtil.pixelFromDp(getContext(), CARD_SIZE)
			+ statusBarHeight);
		setLayoutParams(params);
	}

	protected void showArrows(boolean show) {
		int visibility = show ? VISIBLE : INVISIBLE;

		for (View arrow : arrows) {
			if (isChildArrow(arrow)) {
				arrow.setVisibility(visibility);
			}
		}
	}

	protected boolean isChildArrow(View arrow) {
		return arrow.getParent().getParent().equals(this);
	}

	private void takeSubviewsOffScreen() {
		for (int i = 1; i < getChildCount(); i++) {
			getChildAt(i).setX(maxWidth);
		}
	}

	protected static int statusBarHeight;

	protected List<View> arrows;
	protected int index;

	protected int maxWidth;
	protected int maxHeight;

	protected int minimizedPosition;
	protected int normalY;
	protected int backgroundY;

	protected CardState cardState;

	protected CardListener cardListener;
}
