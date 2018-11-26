package com.example.adslib.smartad.utill;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Junky2 on 8/24/2018.
 */

public class StartProgressBar {

    private static int progressStatus = 0;
    private static Handler handler = new Handler();
    public static Drawable draw;
    private static ClipDrawable mImageDrawable;

    // a field in your class
    private static int mLevel = 0;
    private static int fromLevel = 0;
    private static int toLevel = 0;

    public static final int LEVEL_DIFF = 100;
    public static final int DELAY = 30;
    Thread thread = new Thread();
    Runnable runnable;
    private static Handler mUpHandler = new Handler();
    private static Runnable animateUpImage = new Runnable() {

        @Override
        public void run() {
            doTheUpAnimation(fromLevel, toLevel);
        }
    };
    private ImageView img;
    private ProgressBar progressBar;
    private View view;
    private long sleepMillisec=50;
    private boolean progressTrue=true;

    private static void doTheUpAnimation(int fromLevel, int toLevel) {
        mLevel += LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel <= toLevel) {
            mUpHandler.postDelayed(animateUpImage, DELAY);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);

        }

    }

    public void startThrad() {
        mImageDrawable = (ClipDrawable) img.getDrawable();
        thread = new Thread(runnable = new Runnable() {

            @Override
            public void run() {
                while (progressTrue) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgressDrawable(draw);
                            progressBar.setProgress(progressStatus);
                            mImageDrawable.setLevel(progressStatus * 100);
                            setTextLoading();
                        }
                    });

                    try {
                        Thread.sleep(sleepMillisec);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.interrupt();
    }


    public void startProgressBar(ImageView img, final ProgressBar progressBar, View view) {
        this.img = img;
        this.view = view;
        this.progressBar = progressBar;
        startThrad();
        thread.start();

    }


    public void setTextLoading() {

        if (progressBar.getProgress() == 100) {
            progressStatus = 0;
            toLevel = 0;
            view.setVisibility(View.GONE);
            progressTrue = false;
        }
    }

}
