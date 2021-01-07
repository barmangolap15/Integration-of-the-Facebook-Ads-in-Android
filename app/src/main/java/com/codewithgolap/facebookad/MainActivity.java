package com.codewithgolap.facebookad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class MainActivity extends AppCompatActivity {

    private AdView adView;
    Button btn_show, btn_show_nativeAd;

    public InterstitialAd interstitialAdFB;
    public static boolean fb1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //banner ad
        banner_ad();

        // interstitial ad
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFBInterstitial();
            }
        });
        AudienceNetworkAds.initialize(this);
        loadFbInterstitialAd();

        //native ad
        btn_show_nativeAd = (Button) findViewById(R.id.btn_show_native_ad);
        btn_show_nativeAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NextActivity.class));
            }
        });

    }


    //banner ad
    private void banner_ad() {

        this.adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(this.adView);

        // Request an ad
        this.adView.loadAd();
    }


    //interstitial Ad
    private void loadFbInterstitialAd(){
        this.interstitialAdFB = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            private Ad ad;
            private AdError adError;

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                MainActivity.fb1 = false;
                MainActivity.this.interstitialAdFB.loadAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                StringBuilder sb = new StringBuilder();
                sb.append("Interstitial ad failed to load: ");
                sb.append(adError.getErrorMessage());
                Log.e("fb", sb.toString());
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        };
        interstitialAdFB.loadAd(
                interstitialAdFB.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .build()
        );

    }
    public void showFBInterstitial(){
        InterstitialAd interstitialAd = this.interstitialAdFB;
        if (interstitialAd != null && interstitialAd.isAdLoaded()){
            this.interstitialAdFB.show();
        }
    }
}