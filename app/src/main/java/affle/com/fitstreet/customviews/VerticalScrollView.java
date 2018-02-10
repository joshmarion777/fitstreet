package affle.com.fitstreet.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import affle.com.fitstreet.utils.Logger;

/**
 * Created by root on 7/27/16.
 */
public class VerticalScrollView extends ScrollView {

    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Logger.i("VerticalScrollview" + "onInterceptTouchEvent: DOWN super false");
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                return false; // redirect MotionEvents to ourself

            case MotionEvent.ACTION_CANCEL:
                Logger.i("VerticalScrollview" + "onInterceptTouchEvent: CANCEL super false");
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_UP:
                Logger.i("VerticalScrollview" + "onInterceptTouchEvent: UP super false");
                return false;

            default:
                Logger.i("VerticalScrollview" + "onInterceptTouchEvent: " + action);
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        Logger.i("VerticalScrollview" + "onTouchEvent. action: " + ev.getAction());
        return true;
    }
}
