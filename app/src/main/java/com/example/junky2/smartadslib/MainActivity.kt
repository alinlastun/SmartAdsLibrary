package com.example.junky2.smartadslib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(){


    var flow = arrayListOf("facebook","admob")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* nButton.setOnClickListener {
            managerAdInterstitial = AdsManagerInterstitial()
            managerAdInterstitial.initAds(this,SmartAd.AD_TYPE_FACEBOOK,"ca-app-pub-3940256099942544/1033173712","335407623685857_335407680352518",false, this)
            SmartAd.addTestDevice(SmartAd.AD_TYPE_GOOGLE, "6F369650F8F0218706E21DAA8DC6361A")
            SmartAd.addTestDevice(SmartAd.AD_TYPE_FACEBOOK, "6F369650F8F0218706E21DAA8DC6361A")

            //smartAdInterstitial =  SmartAdInterstitial.showAdWidthCallback(this,SmartAd.AD_TYPE_FACEBOOK,"ca-app-pub-3940256099942544/1033173712","335407623685857_335407680352518",false,this)

        }
        nButton2.setOnClickListener {
            *//* smartAdInterstitial.showLoadedAd()*//* }
    }
*/
    }
}
