package com.jy.recycle.anim;


import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;
 
/** 
* This animation class is animating the expanding and reducing the size of a view 
* The animation toggles between the Expand and Reduce, depending on the current state of the view 
* @author Udinic 
* 
*/ 
public class ExpandAnimation extends Animation { 
//	private View mAnimatedView; 
//	private LayoutParams mViewLayoutParams; 
//	private int mMarginStart, mMarginEnd; 
//	private boolean mIsVisibleAfter = false; 
//	private boolean mWasEndedAlready = false; 
//	 
//	/** 
//	* Initialize the animation 
//	* @param view The layout we want to animate 
//	* @param duration The duration of the animation, in ms 
//	*/ 
//	public ExpandAnimation(View view, int duration) { 
//	 
//		setDuration(duration); 
//		mAnimatedView = view; 
//		mViewLayoutParams = (LayoutParams) viewgetLayoutParams(); 
//		 
//		// if the bottom margin is 0, 
//		// then after the animation will end it'll be negative, and invisible 
//		mIsVisibleAfter = (mViewLayoutParamsbottomMargin == 0); 
//		 
//		mMarginStart = mViewLayoutParamsbottomMargin; 
//		mMarginEnd = (mMarginStart == 0 ? (0- viewgetHeight()) : 0); 
//		 
//		viewsetVisibility(ViewVISIBLE); 
//	} 
// 
//	@Override 
//	protected void applyTransformation(float interpolatedTime, Transformation t) { 
//		superapplyTransformation(interpolatedTime, t); 
//	 
//		if (interpolatedTime < 0f) { 
//		 
//		// Calculating the new bottom margin, and setting it 
//		mViewLayoutParamsbottomMargin = mMarginStart 
//		+ (int) ((mMarginEnd - mMarginStart) * interpolatedTime); 
//		 
//		// Invalidating the layout, making us seeing the changes we made 
//		mAnimatedViewrequestLayout(); 
//		 
//		// Making sure we didn't run the ending before (it happens!) 
//		} else if (!mWasEndedAlready) { 
//		mViewLayoutParamsbottomMargin = mMarginEnd; 
//		mAnimatedViewrequestLayout(); 
//		 
//		if (mIsVisibleAfter) { 
//		mAnimatedViewsetVisibility(ViewGONE); 
//		} 
//		mWasEndedAlready = true; 
//	} 
}