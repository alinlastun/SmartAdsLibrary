package com.example.junky2.smartadslib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.adslib.smartad.AdsManagerInterstitial;
import com.example.adslib.smartad.SmartAd;
import com.example.adslib.smartad.SmartAdInterstitial;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private AdsManagerInterstitial adsManagerInterstitial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button nButton = findViewById(R.id.nButton);
        nButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nButton:
                adsManagerInterstitial = new AdsManagerInterstitial();
                adsManagerInterstitial.initAds(this, SmartAd.AD_TYPE_FACEBOOK, "ca-app-pub-3940256099942544/1033173712", "335407623685857_335407680352518", false, new SmartAdInterstitial.OnSmartAdInterstitialListener() {
                    @Override
                    public void onSmartAdInterstitialDone(int adType) {

                    }

                    @Override
                    public void onSmartAdInterstitialFail(int adType) {

                    }

                    @Override
                    public void onSmartAdInterstitialClose(int adType) {

                    }
                });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsManagerInterstitial.destroyLibrary();

    }
}
