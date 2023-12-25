package com.holdonly.baseandroidproject.utils;

import android.os.SystemClock;
import android.view.View;

/**
 * 点击延时工具
 * */
public class ClickUtil {
    private static final int DEBOUNCING_TAG = -7;
    public static final long DEBOUNCING_DEFAULT_DURATION = 500;

    //点击延时通用调用方法
    public static void applyDebouncingClick(boolean isGlobal,long duration,View v,View.OnClickListener listener){
        if (v==null || listener ==null) return;
        v.setOnClickListener(new OnDebouncingClickListener(isGlobal,duration) {
            @Override
            public void onDebouncingClick(View v) {
                listener.onClick(v);
            }
        });
    }

    public static void applyDebouncingClick(View v,View.OnClickListener listener){
        if (v==null || listener ==null) return;
        v.setOnClickListener(new OnDebouncingClickListener(true,DEBOUNCING_DEFAULT_DURATION) {
            @Override
            public void onDebouncingClick(View v) {
                listener.onClick(v);
            }
        });
    }

    public static void applyDebouncingClickOnViews(View.OnClickListener listener,long duration,View... views){
        if (views == null || listener ==null) return;
        for (View v : views){
            v.setOnClickListener(new OnDebouncingClickListener(true,duration) {
                @Override
                public void onDebouncingClick(View v) {
                    listener.onClick(v);
                }
            });
        }
    }

    public static abstract class OnDebouncingClickListener implements View.OnClickListener {
        private final long mDuration;
        private final boolean mIsGlobal;
        private static boolean mEnable = true;

        private static final Runnable ENABLE_AGAIN = new Runnable() {
            @Override
            public void run() {
                mEnable = true;
            }
        };

        public OnDebouncingClickListener(){
            this(true,DEBOUNCING_DEFAULT_DURATION);
        }

        public OnDebouncingClickListener(long duration){
            this(true,duration);
        }

        public OnDebouncingClickListener(boolean isGlobal){
            this(isGlobal,DEBOUNCING_DEFAULT_DURATION);
        }

        public OnDebouncingClickListener(boolean isGlobal,long duration){
            mDuration = duration;
            mIsGlobal = isGlobal;
        }
        @Override
        public final void onClick(View v) {
            if (mIsGlobal){
                if (mEnable){
                    mEnable = false;
                    v.postDelayed(ENABLE_AGAIN,mDuration);
                    onDebouncingClick(v);
                }
            }else if(isVaild(v)){
                onDebouncingClick(v);
            }
        }

        private boolean isVaild(View v){
            long curr = SystemClock.currentThreadTimeMillis();
            Object tag = v.getTag(DEBOUNCING_TAG);
            if (!(tag instanceof Long)){
                v.setTag(DEBOUNCING_TAG,curr);
                return true;
            }
            long pre = curr - (long)tag;
            if (pre <0){
                v.setTag(DEBOUNCING_TAG,curr);
                return false;
            }else if(pre<=mDuration){
                return false;
            }
            v.setTag(DEBOUNCING_TAG,curr);
            return true;
        }

        public abstract void onDebouncingClick(View v);
    }
}
