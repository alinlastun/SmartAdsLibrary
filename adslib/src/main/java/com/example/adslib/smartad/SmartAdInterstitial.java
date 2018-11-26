package com.example.adslib.smartad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by shock on 2017. 8. 30..
 */

public class SmartAdInterstitial implements com.facebook.ads.InterstitialAdListener {

    private final List<String> flow;
    private OnSmartAdInterstitialListener             mListener;
    private Context mContext;
    private boolean                                   mIsAutoStart;
    private String mGoogleID;
    private String mFacebookID;
    int next;

    @SmartAd.SmartAdOrder
    private int                                       mAdOrder = SmartAd.AD_TYPE_RANDOM;

    private com.google.android.gms.ads.InterstitialAd mGoogleAd;
    private com.facebook.ads.InterstitialAd           mFacebookAd;

    public SmartAdInterstitial(Context context, List<String> flow,
                               String googleID, String facebookID, boolean isAutoStart,
                               final OnSmartAdInterstitialListener callback)
    {
        if (callback != null) {
            mListener = callback;
        } else if (context instanceof OnSmartAdInterstitialListener) {
            mListener = (OnSmartAdInterstitialListener) context;
        }

        this.mContext = context;
        this.flow = flow;
        this.mGoogleID = googleID;
        this.mFacebookID = facebookID;
        this.mIsAutoStart = isAutoStart;

        loadAd();
    }

    private void loadAd() {
        if (SmartAd.IsShowAd(this)) {
            switch (mAdOrder) {
                case SmartAd.AD_TYPE_GOOGLE  : loadGoogle();   break;
                case SmartAd.AD_TYPE_FACEBOOK: loadFacebook(); break;
            }
        } else {
            onDone(SmartAd.AD_TYPE_PASS);
            destroy();
        }
    }


    public void runAdds_Part2Interstitial() {
        Log.d("aqw3ad", "runAdds_Part2Interstitial 1" + flow.size());
        next++;
        if (next < flow.size()) {
            switch (flow.get(next)) {
                case "admob":
                    Log.d("aqw3ad", "runAdds_Part2Interstitial admob" );
                    if ((mGoogleAd != null) && (mGoogleAd.isLoaded())) {
                        Log.d("aqw3ad", "runAdds_Part2Interstitial admob 1" );
                        mGoogleAd.show();
                        Log.d("aqw3ad", "runAdds_Part2Interstitial admob 2" );
                        onDone(SmartAd.AD_TYPE_GOOGLE);
                        Log.d("aqw3ad", "runAdds_Part2Interstitial admob 3" );
                    } else{
                        Log.d("aqw3ad", "runAdds_Part2Interstitial admob 4" );
                        runAdds_Part2Interstitial();
                }
                case "facebook":
                    Log.d("aqw3ad", "runAdds_Part2Interstitial facebook" );
                    if ((mFacebookAd != null) && (mFacebookAd.isAdLoaded())) {
                        Log.d("aqw3ad", "runAdds_Part2Interstitial facebook 1" );
                        mFacebookAd.show();
                        Log.d("aqw3ad", "runAdds_Part2Interstitial facebook 2" );
                        onDone(SmartAd.AD_TYPE_FACEBOOK);
                        Log.d("aqw3ad", "runAdds_Part2Interstitial facebook 3" );
                    } else{
                        Log.d("aqw3ad", "runAdds_Part2Interstitial facebook 4" );
                        runAdds_Part2Interstitial();
                    }
                default:
                    onDone(SmartAd.AD_TYPE_PASS);
                    destroy();
            }
        }else {
            onDone(SmartAd.AD_TYPE_PASS);
            destroy();
        }
    }

    public void showLoadedAd() {
        Log.d("SmartAdInter","showLoadedAd");
        if (SmartAd.IsShowAd(this)) {
            if ((mGoogleAd != null) && (mGoogleAd.isLoaded())) {
                mGoogleAd.show();
                onDone(SmartAd.AD_TYPE_GOOGLE);
            } else if ((mFacebookAd != null) && (mFacebookAd.isAdLoaded())) {
                mFacebookAd.show();
                onDone(SmartAd.AD_TYPE_FACEBOOK);
            }
        } else {
            onDone(SmartAd.AD_TYPE_PASS);
            destroy();
        }
    }

    public void destroy() {
        if (mGoogleAd!=null) {
            mGoogleAd.setAdListener(null);
            mGoogleAd = null;
        }
        if (mFacebookAd!=null) {
            mFacebookAd.setAdListener(null);
            mFacebookAd.destroy();
            mFacebookAd = null;
        }
        mListener = null;
    }

    static public SmartAdInterstitial showAdWidthCallback(Activity context, List<String> flow,
                                                          String googleID, String facebookID, boolean isAutoStart,
                                                          final OnSmartAdInterstitialListener callback)
    {
        SmartAdInterstitial ad = new SmartAdInterstitial(context, flow, googleID, facebookID, isAutoStart, callback);
        return ad;
    }



    static public SmartAdInterstitial showAd(Activity context,  List<String> flow, String googleID,
                                             String facebookID, boolean isAutoStart)
    {
        return SmartAdInterstitial.showAdWidthCallback(context, flow, googleID, facebookID, isAutoStart, null);
    }



    private void onDone(@SmartAd.SmartAdResult int type) {
        if (mListener!=null) {
            mListener.onSmartAdInterstitialDone(type);
        }
    }

    private void onFail(@SmartAd.SmartAdResult int type) {
        if (mListener!=null) {
            mListener.onSmartAdInterstitialFail(type);
            destroy();
        }
    }

    // 구글 *****************************************************************************************

    private void loadGoogle() {
        if (mGoogleID != null) {
            mGoogleAd = new com.google.android.gms.ads.InterstitialAd(mContext);
            mGoogleAd.setAdUnitId(mGoogleID);
            mGoogleAd.setAdListener(mGoogleListener);
            mGoogleAd.loadAd(SmartAd.getGoogleAdRequest());
        } else {
            if ((mAdOrder == SmartAd.AD_TYPE_GOOGLE) && (mFacebookID != null)) loadFacebook();
            else onFail(SmartAd.AD_TYPE_GOOGLE);
        }
    }

    private com.google.android.gms.ads.AdListener mGoogleListener = new com.google.android.gms.ads.AdListener() {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();

            Log.d("SmartAdInterstitial", String.valueOf(mIsAutoStart));
            if (mIsAutoStart) showLoadedAd();
        }

        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            Log.e("SmartAd", "SmartAdInterstitial : type = Google, error code = "+i);
            mGoogleAd = null;

            if ((mAdOrder == SmartAd.AD_TYPE_GOOGLE) && (mFacebookID != null)) loadFacebook();
            else onFail(SmartAd.AD_TYPE_GOOGLE);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            if (mListener!=null) mListener.onSmartAdInterstitialClose(SmartAd.AD_TYPE_GOOGLE);
            destroy();
        }
    };

    // 페이스북 **************************************************************************************

    private void loadFacebook() {
        if (mFacebookID != null) {
            mFacebookAd = new com.facebook.ads.InterstitialAd(mContext, mFacebookID);
            mFacebookAd.setAdListener(this);
            mFacebookAd.loadAd();
        } else {
            if ((mAdOrder == SmartAd.AD_TYPE_FACEBOOK) && (mGoogleID != null)) loadGoogle();
            else onFail(SmartAd.AD_TYPE_FACEBOOK);
        }
    }

    @Override
    public void onAdLoaded(com.facebook.ads.Ad ad) {
        if (mIsAutoStart) showLoadedAd();
    }

    @Override
    public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
        Log.e("SmartAd", "SmartAdInterstitial : type = Facebook, error code = "+adError.getErrorCode()+", error message = "+adError.getErrorMessage());

        ad.destroy();
        mFacebookAd.destroy();
        mFacebookAd = null;

        if ((mAdOrder == SmartAd.AD_TYPE_FACEBOOK) && (mGoogleID != null)) loadGoogle();
        else onFail(SmartAd.AD_TYPE_FACEBOOK);
    }

    @Override
    public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
        ad.destroy();
        if (mListener!=null) mListener.onSmartAdInterstitialClose(SmartAd.AD_TYPE_FACEBOOK);
        destroy();
    }

    @Override
    public void onAdClicked(com.facebook.ads.Ad ad) {}
    @Override
    public void onLoggingImpression(com.facebook.ads.Ad ad) {}
    @Override
    public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {}

    // 반환 인터페이스 *********************************************************************************

    public interface OnSmartAdInterstitialListener {
        void onSmartAdInterstitialDone(int adType);
        void onSmartAdInterstitialFail(int adType);
        void onSmartAdInterstitialClose(int adType);
    }
}
