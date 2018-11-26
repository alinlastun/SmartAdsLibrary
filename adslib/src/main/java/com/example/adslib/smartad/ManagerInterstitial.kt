package com.example.adslib.smartad

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ClipDrawable
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.adslib.R
import com.example.adslib.smartad.utill.StartProgressBar
import java.util.*


class ManagerInterstitial {
    lateinit var smartAdInterstitial: SmartAdInterstitial
    private lateinit var mImageDrawable: ClipDrawable
    private lateinit var activity: Activity
    private var progressTrue = true
    private var toLevel = 0
    private var progressStatus = 0
    private val handler = Handler()
    private lateinit var alertDialog: Dialog
    private var progressBar: ProgressBar? = null
    private val sleepMillisec: Long = 50


    fun initAds( activity: Activity,order:Int,idGoogle: String,idFacebook: String,isStartAuto: Boolean,listener: SmartAdInterstitial.OnSmartAdInterstitialListener) {
        this.activity = activity
        smartAdInterstitial = SmartAdInterstitial.showAdWidthCallback(activity, order, idGoogle, idFacebook, isStartAuto, listener)
        if(!isStartAuto){
            initDialog()
        }
    }

    private fun initDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)
        val dialogView = activity.layoutInflater.inflate(R.layout.activity_loading_progress_bar, null)
        dialogBuilder.setView(dialogView)
        alertDialog = dialogBuilder.create()
        startThrad(dialogView.findViewById(R.id.imageView1))
        alertDialog.show()

    }

    private fun startThrad(mImageView: ImageView) {
        mImageDrawable = mImageView.drawable as ClipDrawable
        toLevel = 0
        progressStatus = 0
        Timer().schedule(object : TimerTask() {
            override fun run() {
                while (progressStatus < 101) {
                    progressStatus += 1
                    handler.post {
                        progressBar?.progressDrawable = StartProgressBar.draw
                        progressBar?.progress = progressStatus
                        mImageDrawable.level = progressStatus * 100
                        if (progressStatus == 95) {
                            smartAdInterstitial.showLoadedAd()
                        }
                        if (progressStatus == 99) {
                            toLevel = 0
                            progressTrue = false
                            alertDialog.dismiss()
                        }
                    }
                    try {
                        Thread.sleep(sleepMillisec)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }, 50)
    }


}
