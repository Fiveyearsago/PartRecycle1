package com.jy.recycle.ui.view.pickerview.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.jy.recycle.R;

/**
 * ����popwindow��͸���ڱ�����������
 * Created by jzxiang on 16/3/14.
 */
public class ViewController implements Animation.AnimationListener {

    View mViewShader;
    ViewGroup mContentView;
    Animation mAnimBgIn, mAnimBgOut;


    public ViewController(Activity activity) {
        init(activity);
    }


    void init(Activity activity) {
        initView(activity);
        initAnim(activity);
    }

    void initView(Activity activity) {
        mContentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        mViewShader = new View(activity);
        mViewShader.setBackgroundResource(R.color.timepicker_dialog_bg);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mViewShader.setLayoutParams(params);

    }

    public void setShaderClickListener(View.OnClickListener onClickListener) {
        mViewShader.setOnClickListener(onClickListener);
    }

    void initAnim(Activity activity) {
        mAnimBgIn = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
        mAnimBgOut = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out);
        mAnimBgOut.setFillAfter(true);
        mAnimBgOut.setAnimationListener(this);
    }


    public void show() {
        mContentView.addView(mViewShader);
        mViewShader.startAnimation(mAnimBgIn);
    }

    public void dismiss() {
        mViewShader.startAnimation(mAnimBgOut);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                mContentView.removeView(mViewShader);
            }
        });
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
