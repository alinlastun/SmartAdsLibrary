package com.example.adslib.smartad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.adslib.R;

import java.util.Timer;
import java.util.TimerTask;


public class AdsManagerInterstitial {

    private SmartAdInterstitial smartAdInterstitial;
    private ClipDrawable mImageDrawable;
    private Activity activity;
    private int progressStatus = 0;
    private Dialog alertDialog;
    private ProgressBar progressBar;
    private int sleepMillisec = 50;

    public void initAds(Activity activity,@SmartAd.SmartAdOrder int howWillBeFirst, String idGoogle, String idFacebook, boolean isStartAuto, SmartAdInterstitial.OnSmartAdInterstitialListener listener) {
        this.activity = activity;
        smartAdInterstitial = SmartAdInterstitial.showAdWidthCallback(activity, howWillBeFirst, idGoogle, idFacebook, isStartAuto, listener);
        if (!isStartAuto) {
            initDialog();
        }
    }

    private void initDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.activity_loading_progress_bar, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        progressBar =  dialogView.findViewById(R.id.progressBar);
        startThrad((ImageView) dialogView.findViewById(R.id.imageView1));
        alertDialog.show();

    }


    private void startThrad(ImageView mImageView) {
        mImageDrawable = (ClipDrawable) mImageView.getDrawable();
        progressStatus = 0;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                while (progressStatus < 101) {
                    progressStatus += 1;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                    progressBar.setProgressDrawable(activity.getResources().getDrawable(R.drawable.clip_source));
                                    mImageDrawable.setLevel(progressStatus * 100);
                                    if (progressStatus == 97) {
                                        smartAdInterstitial.showLoadedAd();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                alertDialog.dismiss();
                                            }
                                        }, 800);
                                    }
                                }
                            }, 800);
                        }
                    });
                    try {
                        Thread.sleep(sleepMillisec);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 50);
    }

    public void destroyLibrary() {
        smartAdInterstitial.destroy();
        alertDialog.dismiss();
    }
}
