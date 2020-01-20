package com.liferay.mobile.screens.bankofwesteros.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
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
    protected final Queue<Card> cardHistory = Collections.asLifoQueue(new ArrayDeque<Card>());
    protected int maxWidth;
    protected int maxHeight;
    protected int card1RestPosition;
    protected int card1FoldedPosition;
    protected int card2FoldedPosition;
    protected ViewGroup card1;
    protected ViewGroup card2;
    private int cardHeight;
    private ViewGroup card1Subview1;
    private ViewGroup card1Subview2;
    private ViewGroup card2Subview1;
    private ViewGroup card2Subview2;
    private ImageView card1ToBackground;
    private ImageView card1ToFrontView;
    private ImageView card1SubViewToBackground;
    private ImageView card2ToCard1;
    private ImageView card2ToFrontView;
    private ImageView card2SubViewToCard1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentMenuBar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        findAndSetCardViews();

        if (maxWidth != 0 && maxHeight != 0) {
            animateScreenAfterLoad();
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
        if (v.equals(card1) || v.equals(card2SubViewToCard1) || v.equals(card2ToCard1)) {
            toCard1(null);
        } else if (v.equals(card2)) {
            toCard2();
        } else if (v.equals(card1ToFrontView)) {
            goLeftCard1();
        } else if (v.equals(card2ToFrontView)) {
            goLeftCard2();
        } else {
            toBackground();
        }
    }

    @Override
    public void onBackPressed() {
        if (cardHistory.isEmpty() || cardHistory.size() == 1) {
            super.onBackPressed();
        } else {
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
        card1 = findViewById(R.id.card1);
        card1.setOnTouchListener(new FlingTouchListener(this, createCard1Listener()));
        card2 = findViewById(R.id.card2);
        card2.setOnTouchListener(new FlingTouchListener(this, createCard2Listener()));

        card1Subview1 = findViewById(R.id.card1_subview1);
        card1Subview2 = findViewById(R.id.card1_subview2);
        card2Subview1 = findViewById(R.id.card2_subview1);
        card2Subview2 = findViewById(R.id.card2_subview2);

        card1ToBackground = findAndAddListener(card1, "card1_to_background");
        card1ToFrontView = findAndAddListener(card1, "card1_to_front_view");
        card1SubViewToBackground = findAndAddListener(card1, "card1_subview_to_background");
        card2ToCard1 = findAndAddListener(card2, "card2_to_card1");
        card2ToFrontView = findAndAddListener(card2, "card2_to_front_view");
        card2SubViewToCard1 = findAndAddListener(card2, "card2_subview_to_card1");
    }

    protected void calculateHeightAndWidth() {
        cardHeight = getResources().getDimensionPixelSize(R.dimen.westeros_card_title_height);
        card1FoldedPosition = maxHeight - 2 * cardHeight;
        card2FoldedPosition = maxHeight - cardHeight;
        card1RestPosition = 0;

        if (card1Subview2 != null) {
            card1Subview2.setX(maxWidth);
        }
        if (card2Subview2 != null) {
            card2Subview2.setX(maxWidth);
        }
    }

    protected abstract void animateScreenAfterLoad();

    protected void toBackground() {
        hideArrowIcon(card1SubViewToBackground);
        hideArrowIcon(card1ToFrontView);
        hideArrowIcon(card1ToBackground);

        cardHistory.add(Card.BACKGROUND);

        card1.animate().y(card1FoldedPosition);
        setFrameLayoutMargins(card1, 0, 0, 0, cardHeight);
    }

    protected void toCard1() {
        toCard1(null);
    }

    protected void toCard1(Animator.AnimatorListener listener) {
        showArrowIcon(card1ToBackground);
        showArrowIcon(card1SubViewToBackground);
        showArrowIcon(card1ToFrontView);

        hideArrowIcon(card2ToCard1);
        hideArrowIcon(card2SubViewToCard1);
        hideArrowIcon(card2ToFrontView);

        cardHistory.add(Card.CARD1);

        animate(card1);

        setFrameLayoutMargins(card1, 0, 0, 0, cardHeight);
        card1.animate().y(card1RestPosition);
        card2.animate().y(card2FoldedPosition).setListener(listener);
    }

    protected void toCard2() {
        showArrowIcon(card2ToCard1);
        showArrowIcon(card2SubViewToCard1);
        showArrowIcon(card2ToFrontView);

        cardHistory.add(Card.CARD2);

        card2.animate().setListener(null);
        int topPosition = convertDpToPx(TOP_POSITION);
        int margin = topPosition / 2;
        card2.animate().y(topPosition);

        animate(card1);
        setFrameLayoutMargins(card1, margin, 0, margin, cardHeight);
        card1.animate().y(0);
    }

    protected void goRightCard1() {
        goRight(card1Subview1, card1Subview2);
    }

    protected void goRightCard2() {
        goRight(card2Subview1, card2Subview2);
    }

    protected void goLeftCard1() {
        goLeft(card1Subview1, card1Subview2);
    }

    protected void goLeftCard2() {
        goLeft(card2Subview1, card2Subview2);
    }

    protected void goLeft(View leftView, View rightView) {
        if (leftView != null && rightView != null) {
            leftView.animate().x(0);
            rightView.animate().x(maxWidth);
        }
    }

    protected void goRight(View leftView, View rightView) {
        if (leftView != null && rightView != null) {
            leftView.animate().x(-maxWidth);
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
        getWindow().setStatusBarColor(
            ResourcesCompat.getColor(getResources(), R.color.background_gray_westeros, getTheme()));
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
                    default:
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
                    default:
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
        ImageView view = viewGroup.findViewWithTag(tag);
        if (view != null) {
            view.setOnClickListener(this);
        }
        return view;
    }

    private void setFrameLayoutMargins(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        view.setLayoutParams(layoutParams);
    }

    private void toPreviousCard() {
        cardHistory.poll();

        //INFO this should be generic and calculated via current card
        Card previousCard = cardHistory.peek();
        if (previousCard == Card.CARD2) {
            toCard2();
        } else if (previousCard == Card.CARD1) {
            toCard1();
        } else {
            toBackground();
        }

        //we have to remove from the queue the last back movement
        cardHistory.poll();
    }
}