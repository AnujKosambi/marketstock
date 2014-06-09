package com.marketstock.helper;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MarqueeLayout extends LinearLayout {
	private Animation animation;

	public MarqueeLayout(Context context) {
		super(context);
		

		animation = new TranslateAnimation(
			Animation.RELATIVE_TO_SELF, +1f,
			Animation.RELATIVE_TO_SELF,	-1f,
			Animation.RELATIVE_TO_SELF, 0f,
			Animation.RELATIVE_TO_SELF, 0f
		);

		animation.setRepeatCount(Animation.INFINITE);
		animation.setRepeatMode(Animation.RESTART);
		animation.setInterpolator(new LinearInterpolator());
		
	}

	public void setDuration(int durationMillis) {
		animation.setDuration(durationMillis);
	}
	
	public void startAnimation() {
		startAnimation(animation);
	}
}
