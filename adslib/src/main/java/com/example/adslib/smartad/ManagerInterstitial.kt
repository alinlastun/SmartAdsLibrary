package com.example.adslib.smartad

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.adslib.R
import com.example.adslib.smartad.utill.LoadingProgressBarActivity

import kotlinx.android.synthetic.main.activity_manager_interstitial.*
import java.lang.Exception
import android.widget.EditText
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.adslib.smartad.utill.StartProgressBar


class ManagerInterstitial  {

    lateinit var smartAdInterstitial : SmartAdInterstitial
    var isStartAuto = false
    lateinit var imageView :ImageView
    private lateinit var mImageDrawable:ClipDrawable
    private lateinit var activity: Activity
    private var thread = Thread()
    private var progressTrue = true
    private var toLevel = 0
    private var progressStatus = 0
    private val mUpHandler = Handler()
    private val handler = Handler()
    private var progressBar: ProgressBar? = null
    private val sleepMillisec: Long = 50


    fun initAds(activity: Activity, flow:List<String>, idGoogle:String, idFacebook:String, isStartAuto:Boolean, listener: SmartAdInterstitial.OnSmartAdInterstitialListener){
        this.isStartAuto = isStartAuto
        this.activity =activity
        smartAdInterstitial = SmartAdInterstitial.showAdWidthCallback(activity,flow,idGoogle,idFacebook,isStartAuto,listener)
        initDialog()

    }

    fun initDialog(){
        val dialogBuilder = AlertDialog.Builder(activity)
        val dialogView = activity.layoutInflater.inflate(R.layout.activity_loading_progress_bar, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        startThrad(dialogView.findViewById(R.id.imageView1))
        alertDialog.show()

    }

    fun showLoading(){
        if(!isStartAuto){
            Log.d("aqw3ad", "initAds " + activity)
            try {

            }catch (e:Exception){
                Log.d("aqw3ad", "showLoading error " + e)
            }

        }

    }


    private fun showAd() {
        if(!isStartAuto){
            Log.d("aqw3ad", "showAd 1")
            smartAdInterstitial.next = -1
            Log.d("aqw3ad", "showAd 2")
            smartAdInterstitial.runAdds_Part2Interstitial()
            Log.d("aqw3ad", "showAd 3")
        }

    }

    private fun startThrad(mImageView: ImageView) {
        Log.d("aqw3ad", "Thread 0 ")
       // mImageDrawable = mImageView.drawable as ClipDrawable
        while (progressTrue) {
            Log.d("aqw3ad", "Thread 2 ")
            progressStatus += 1
            Log.d("aqw3ad", "Thread 2.1 ${progressStatus}")
            handler.post {
                Log.d("aqw3ad", "Thread 3 ${progressStatus}")
                progressBar?.progressDrawable = StartProgressBar.draw
                progressBar?.progress = progressStatus
                // mImageDrawable.level = progressStatus * 100
                Log.d("aqw3ad", "Thread 4 ${progressStatus}")
                setTextLoading()
                if (progressBar?.getProgress() == 95) {
                    Log.d("aqw3ad", "LoadingProgressBarActivity 95")
                    Log.d("aqw3ad", "LoadingProgressBarActivity 95 .finish")
                    toLevel = 0
                    progressTrue = false
                }
            }
        }
        /*thread = Thread(Runnable {
            Log.d("aqw3ad", "Thread 1 ")
            while (progressTrue) {
                Log.d("aqw3ad", "Thread 2 ")
                progressStatus += 1
                handler.post {
                    Log.d("aqw3ad", "Thread 3 ")
                    progressBar?.progressDrawable = StartProgressBar.draw
                    progressBar?.progress = progressStatus
                   // mImageDrawable.level = progressStatus * 100
                    setTextLoading()
                    if (progressBar?.getProgress() == 95) {
                        Log.d("aqw3ad", "LoadingProgressBarActivity 95")
                        Log.d("aqw3ad", "LoadingProgressBarActivity 95 .finish")
                        toLevel = 0
                        progressTrue = false
                    }
                }
             *//*   handler.post(Runnable {
                    progressBar.setProgressDrawable(draw)
                    progressBar.setProgress(progressStatus)
                    mImageDrawable.level = progressStatus * 100
                    setTextLoading()
                    if (progressBar.getProgress() == 95) {
                        Log.d("aqw3ad", "LoadingProgressBarActivity 95")
                        val intent = Intent(this@LoadingProgressBarActivity, ManagerInterstitial::class.java)
                        intent.putExtra("fromLoading", true)
                        startActivity(intent)
                        Log.d("aqw3ad", "LoadingProgressBarActivity 95 .finish")
                        toLevel = 0
                        finish()
                        progressTrue = false
                    }
                })*//*

                try {
                    Thread.sleep(sleepMillisec)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        })*/

        //thread.interrupt()
    }


    fun setTextLoading() {

        if (progressBar?.progress == 98) {
            progressStatus = 0
            toLevel = 0

            progressTrue = false
        }
    }


}
