package com.example.junky2.smartadslib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.adslib.smartad.ManagerInterstitial
import com.example.adslib.smartad.SmartAd
import com.example.adslib.smartad.SmartAdInterstitial
import com.example.adslib.smartad.SmartAdInterstitial.OnSmartAdInterstitialListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,OnSmartAdInterstitialListener {

    lateinit var smartAdInterstitial:SmartAdInterstitial
    lateinit var managerInterstitial: ManagerInterstitial
    var flow = arrayListOf("facebook","addmob")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nButton.setOnClickListener {
            managerInterstitial = ManagerInterstitial()
            managerInterstitial.initAds(this,flow,"ca-app-pub-3940256099942544/1033173712","335407623685857_335407680352518",false,this)
            SmartAd.addTestDevice(SmartAd.AD_TYPE_GOOGLE, "6F369650F8F0218706E21DAA8DC6361A")
            SmartAd.addTestDevice(SmartAd.AD_TYPE_FACEBOOK, "6F369650F8F0218706E21DAA8DC6361A")

            //smartAdInterstitial =  SmartAdInterstitial.showAdWidthCallback(this,SmartAd.AD_TYPE_GOOGLE,"ca-app-pub-3940256099942544/1033173712","335407623685857_335407680352518",false,this)

        }
        nButton2.setOnClickListener {
            managerInterstitial.showLoading()
            /* smartAdInterstitial.showLoadedAd()*/ }
    }

    override fun onSmartAdInterstitialDone(adType: Int) {
        Log.d("SmartAdInterstitial", "onSmartAdInterstitialDone")
    }

    override fun onSmartAdInterstitialFail(adType: Int) {
        Log.d("SmartAdInterstitial", "onSmartAdInterstitialFail")
    }

    override fun onSmartAdInterstitialClose(adType: Int) {
        Log.d("SmartAdInterstitial", "onSmartAdInterstitialClose")
    }
}
