package affle.com.fitstreet.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.utils.Logger;

/**
 * This class is a custom linear layout that is shown & hidden with animations
 *
 * @author Affle AppStudioz
 */
public class AnimatingLinearLayout extends LinearLayout {
    public enum AnimationType {
        Top, Bottom, Right, left
    }

    private Context context;
    private Animation inAnimationFromBottom;
    private Animation outAnimationToBottom;
    private AnimationType animationType = AnimationType.Bottom;

    public AnimatingLinearLayout(Context context) {
        super(context);
        this.context = context;
        initAnimations();

    }

    public AnimatingLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAnimations();
    }

    public AnimatingLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initAnimations();
    }

    private void initAnimations() {
        inAnimationFromBottom = AnimationUtils.loadAnimation(context, R.anim.in_animation);
        outAnimationToBottom = AnimationUtils.loadAnimation(context, R.anim.out_animation);

    }

    public void show() {
        if (isVisible())
            return;
        show(true, null, 0);
    }

    public void show(boolean withAnimation, final ScrollView scrollView, final int height) {
        if (withAnimation) {
            post(new Runnable() {

                @Override
                public void run() {
                    if (animationType == AnimationType.Bottom)
                        startAnimation(inAnimationFromBottom);
                    inAnimationFromBottom.setAnimationListener(new AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            setVisibility(View.VISIBLE);
                            if (scrollView != null) {
                                scrollView.animate().translationYBy(-height).setDuration(400);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Logger.e("inside on animation end");
                            if (scrollView != null) {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        }
                    });
                }
            });
        } else {
            setVisibility(View.VISIBLE);
        }

    }

    public void hide() {
        if (!isVisible())
            return;
        hide(true, null, null, 0);
    }

    public void hide(boolean withAnimation, final AnimatingLinearLayout linearLayout, final ScrollView scrollView, final int height) {
        if (withAnimation) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (animationType == AnimationType.Bottom)
                        startAnimation(outAnimationToBottom);
                    outAnimationToBottom.setAnimationListener(new AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            if (scrollView != null) {
                                scrollView.animate().translationYBy(height).setDuration(400);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            setVisibility(View.GONE);
                            setSelected(false);
                            if (linearLayout != null) {
                                linearLayout.setSelected(true);
                                linearLayout.show(true, scrollView, height);
                            }
                        }
                    });
                }
            });
        } else {
            setVisibility(View.GONE);
        }
    }

    public boolean isVisible() {
        return (this.getVisibility() == View.VISIBLE);
    }
}