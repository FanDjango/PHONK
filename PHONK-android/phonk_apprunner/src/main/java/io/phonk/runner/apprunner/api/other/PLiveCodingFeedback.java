/*
 * Part of Phonk http://www.phonk.io
 * A prototyping platform for Android devices
 *
 * Copyright (C) 2013 - 2017 Victor Diaz Barrales @victordiaz (Protocoder)
 * Copyright (C) 2017 - Victor Diaz Barrales @victordiaz (Phonk)
 *
 * Phonk is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Phonk is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Phonk. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package io.phonk.runner.apprunner.api.other;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.phonk.runner.R;
import io.phonk.runner.apidoc.annotation.PhonkMethod;
import io.phonk.runner.apidoc.annotation.PhonkMethodParam;
import io.phonk.runner.base.utils.AndroidUtils;
import io.phonk.runner.base.utils.MLog;

public class PLiveCodingFeedback {
    private static final String TAG = PLiveCodingFeedback.class.getSimpleName();

    protected final Context mContext;
    final boolean hidingLiveText = true;
    private final Handler h;
    private final Runnable r;
    private final Typeface fontCode;
    public boolean enable = false;
    float liveTextY = 0;
    private LinearLayout liveRLayout;
    private TextView liveText;
    private boolean show = true; //
    private int bgColor; // OK
    private String textColor = "#FFFFFFFF";
    private int textSize;
    private boolean autoHide = false; // OK
    private int paddingLeft = 5;
    private int paddingRight = 5;
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int alignment = TextView.TEXT_ALIGNMENT_VIEW_END;
    private long timeToHide = 4000;

    public PLiveCodingFeedback(Context context) {
        this.mContext = context;
        fontCode = Typeface.createFromAsset(context.getAssets(), "Inconsolata.otf");

        bgColor = mContext.getResources().getColor(R.color.phonk_colorPrimary_shade);
        textSize = AndroidUtils.dpToPixels(mContext, 8);
        paddingLeft = paddingRight = paddingTop = paddingBottom = AndroidUtils.dpToPixels(mContext, 10);

        h = new Handler();
        r = () -> liveRLayout.animate().alpha(0.0f).setListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // liveRLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }


    @PhonkMethod(description = "Show/hide the live coding feedback", example = "")
    @PhonkMethodParam(params = {"boolean"})
    public PLiveCodingFeedback show(boolean b) {
        this.show = b;

        if (b) {
            liveRLayout.setVisibility(View.VISIBLE);
        } else {
            liveRLayout.setVisibility(View.GONE);
        }

        return this;
    }


    @PhonkMethod(description = "Auto hide the text after shown", example = "")
    @PhonkMethodParam(params = {"boolean"})
    public PLiveCodingFeedback autoHide(boolean b) {
        this.autoHide = b;
        return this;
    }


    @PhonkMethod(description = "Elapsed time until text is hidden", example = "")
    @PhonkMethodParam(params = {"milliseconds"})
    public PLiveCodingFeedback timeToHide(int t) {
        this.timeToHide = t;
        return this;
    }


    @PhonkMethod(description = "Background color", example = "")
    @PhonkMethodParam(params = {"colorHex"})
    public PLiveCodingFeedback backgroundColor(String c) {
        new Color();
        this.bgColor = Color.parseColor(c);
        liveRLayout.setBackgroundColor(this.bgColor);

        return this;
    }


    @PhonkMethod(description = "Text color", example = "")
    @PhonkMethodParam(params = {"colorHex"})
    public PLiveCodingFeedback textColor(String color) {
        new Color();
        this.textColor = color;
        liveText.setTextColor(Color.parseColor(this.textColor));

        return this;
    }


    @PhonkMethod(description = "Sets up the text size", example = "")
    @PhonkMethodParam(params = {"size"})
    public PLiveCodingFeedback textSize(int textSize) {
        this.textSize = AndroidUtils.dpToPixels(mContext, textSize);
        MLog.d(TAG, "textsize " + textSize);
        // liveText.setTextSize(this.textSize);
        return this;
    }


    @PhonkMethod(description = "Adds a text padding", example = "")
    @PhonkMethodParam(params = {"left", "bottom"})
    public PLiveCodingFeedback padding(int left, int bottom) {
        paddingLeft = left;
        paddingBottom = bottom;

        return this;
    }


    @PhonkMethod(description = "Aligns the text", example = "")
    @PhonkMethodParam(params = {"align={left,center,right}"})
    public PLiveCodingFeedback align(String alignment) {
        switch (alignment) {
            case "right":
                this.alignment = TextView.TEXT_ALIGNMENT_VIEW_START;
                break;
            case "center":
                this.alignment = Gravity.CENTER;
                break;
            case "left":
                this.alignment = TextView.TEXT_ALIGNMENT_VIEW_END;
                break;
        }

        return this;
    }


    @PhonkMethod(description = "Writes simple text in the feedback", example = "")
    @PhonkMethodParam(params = {"text"})
    public PLiveCodingFeedback write(String text) {
        write(text, this.textColor, this.textSize);

        return this;
    }


    @PhonkMethod(description = "Writes text specifing the color and the size", example = "")
    @PhonkMethodParam(params = {"text", "colorHex", "size"})
    public PLiveCodingFeedback write(String text, String color, int size) {
        // this.text = text;

        if (enable) {
            // showing layout and content and removing previous callbacks
            if (hidingLiveText) {
                liveRLayout.setVisibility(View.VISIBLE);
                liveRLayout.animate().alpha(1.0f);
                h.removeCallbacks(r);
                if (this.autoHide) {
                    h.postDelayed(r, timeToHide);
                }

            }

            // if there is a textview then remove it
            if (liveText != null) {
                liveRLayout.removeView(liveText);
            }

            // add text view
            liveText = new TextView(mContext);
            RelativeLayout.LayoutParams liveTextLayoutParams = new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            liveTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            liveTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            liveText.setLayoutParams(liveTextLayoutParams);
            liveText.setBackgroundColor(0x00000000);
            liveText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            liveText.setTextColor(Color.parseColor(color));
            liveText.setTextSize(size);
            liveText.setShadowLayer(2, 1, 1, 0x55000000);
            liveText.setTypeface(fontCode);
            liveText.setGravity(alignment);


            liveText.setText(text);
            liveRLayout.addView(liveText);
        }
        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View add() {
        // live execution layout
        liveRLayout = new LinearLayout(mContext);
        RelativeLayout.LayoutParams liveLayoutParams = new RelativeLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
        );
        liveLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        liveLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        liveRLayout.setLayoutParams(liveLayoutParams);
        liveRLayout.setGravity(Gravity.BOTTOM);
        liveRLayout.setBackgroundColor(bgColor);
        liveRLayout.setPadding(10, 10, 10, 10);
        liveRLayout.setVisibility(View.GONE);
        // liveRLayout.setLayoutTransition(transition)

        Animator appearingAnimation = ObjectAnimator.ofFloat(null, "translationY", 20, 0);
        appearingAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setTranslationX(0f);
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            LayoutTransition l = new LayoutTransition();
            l.enableTransitionType(LayoutTransition.CHANGING);

            AnimatorSet as = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.live_code);
            // as.se
            l.setAnimator(LayoutTransition.APPEARING, as);
            l.setDuration(LayoutTransition.APPEARING, 300);
            l.setStartDelay(LayoutTransition.APPEARING, 0);

            liveRLayout.setLayoutTransition(l);
        }
        return liveRLayout;

    }

}
