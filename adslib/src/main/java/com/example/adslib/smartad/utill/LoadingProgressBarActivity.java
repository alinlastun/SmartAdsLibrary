package com.example.adslib.smartad.utill;

import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.adslib.R;

import static com.example.adslib.smartad.utill.StartProgressBar.draw;

public class LoadingProgressBarActivity extends AppCompatActivity {
    private static int mLevel = 0;
    public static final int LEVEL_DIFF = 100;
    public static final int DELAY = 30;
    private static ClipDrawable mImageDrawable;
    private static int toLevel = 0;
    private static int progressStatus = 0;
    private static Handler mUpHandler = new Handler();
    private static Handler handler = new Handler();
    private ImageView imageView;
    private ProgressBar progressBar;
    private long sleepMillisec = 50;
    private boolean progressTrue = true;
    private static Runnable animateUpImage;
    private Thread thread = new Thread();
    private String stringLoading;
    private String stringAction;
    private TextView textLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_progress_bar);
        stringLoading = getIntent().getStringExtra("textLoading");
        stringAction = getIntent().getStringExtra("textAction");
        init();
        ruunable();
        startThrad();
        thread.start();

    }

    private static void doTheUpAnimation(int toLevel) {
        mLevel += LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel <= toLevel) {
            mUpHandler.postDelayed(animateUpImage, DELAY);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);
        }

    }

    public void ruunable() {
        animateUpImage = new Runnable() {
            @Override
            public void run() {
                doTheUpAnimation(toLevel);
            }
        };
    }

    public void startThrad() {
        mImageDrawable = (ClipDrawable) imageView.getDrawable();
        thread = new Thread(new Runnable() {

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
                            if (progressBar.getProgress() == 95) {
                                    Log.d("aqw3ad", "LoadingProgressBarActivity 95"  );
                                    Intent intent = new Intent(LoadingProgressBarActivity.this,ManagerInterstitial.class);
                                    intent.putExtra("fromLoading",true);
                                    startActivity(intent);
                                Log.d("aqw3ad", "LoadingProgressBarActivity 95 .finish"  );
                                toLevel = 0;
                                finish();
                                progressTrue = false;
                            }
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


    public void setTextLoading() {

        if (progressBar.getProgress() == 98) {
            progressStatus = 0;
            toLevel = 0;
            finish();
            progressTrue = false;
        }
    }

    public void init() {
        textLoading = findViewById(R.id.loadingText);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView1);
        textLoading.setText(stringLoading);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressStatus = 0;
    }
}
