package com.sample.downloadablefonts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView dynamicFontTextView;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dynamicFontTextView = findViewById(R.id.textView3);

        requestFontDownload();
    }

    private void requestFontDownload() {

        String query = "name=Open Sans&weight=800&italic=1";   // Form the query String

        FontRequest request = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                query,
                R.array.com_google_android_gms_fonts_certs);


        FontsContractCompat.FontRequestCallback callback = new FontsContractCompat
                .FontRequestCallback() {
            @Override
            public void onTypefaceRetrieved(Typeface typeface) {
                dynamicFontTextView.setTypeface(typeface);  // Font has been downloaded successfully
            }

            @Override
            public void onTypefaceRequestFailed(int reason) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.request_failed), Toast.LENGTH_LONG)
                        .show();
            }
        };
        FontsContractCompat
                .requestFont(MainActivity.this, request, callback,
                        getHandlerThreadHandler());
    }

    private Handler getHandlerThreadHandler() {
        if (mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }
        return mHandler;
    }
}
