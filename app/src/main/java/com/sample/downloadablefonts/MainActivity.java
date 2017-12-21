package com.sample.downloadablefonts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView dynamicFontTextView, switchFontAlertTextview;
    private Button switchButton;
    private Handler mHandler = null;
    private ProgressBar progressBar;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dynamicFontTextView = findViewById(R.id.textView3);
        switchFontAlertTextview = findViewById(R.id.switch_font_alert_textview);
        switchFontAlertTextview.setVisibility(View.INVISIBLE);

        switchButton = findViewById(R.id.switch_font_button);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        flag = 0;

        requestFontDownload();
    }

    private void requestFontDownload() {

        String query = "name=Open Sans&weight=800&italic=1";   // Form the query String
        String query2 = "name=Lobster";   // Form the query String

        final FontRequest request = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                query,
                R.array.com_google_android_gms_fonts_certs);

        final FontRequest request2 = new FontRequest(
                        "com.google.android.gms.fonts",
                        "com.google.android.gms",
                        query2,
                        R.array.com_google_android_gms_fonts_certs);


        final FontsContractCompat.FontRequestCallback callback = new FontsContractCompat
                .FontRequestCallback() {
            @Override
            public void onTypefaceRetrieved(Typeface typeface) {
                dynamicFontTextView.setTypeface(typeface);  // Font has been downloaded successfully
                switchFontAlertTextview.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onTypefaceRequestFailed(int reason) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.request_failed), Toast.LENGTH_LONG)
                        .show();
            }
        };

        final FontsContractCompat.FontRequestCallback callback2 = new FontsContractCompat
                .FontRequestCallback() {
            @Override
            public void onTypefaceRetrieved(Typeface typeface) {
                dynamicFontTextView.setTypeface(typeface);  // Font has been downloaded successfully
                switchFontAlertTextview.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTypefaceRequestFailed(int reason) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.request_failed), Toast.LENGTH_LONG)
                        .show();
            }
        };

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 0) {
                    flag = 1;
                    FontsContractCompat
                            .requestFont(MainActivity.this, request, callback,
                                    getHandlerThreadHandler());
                    switchFontAlertTextview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    flag = 0;
                    FontsContractCompat
                            .requestFont(MainActivity.this, request2, callback2,
                                    getHandlerThreadHandler());
                    switchFontAlertTextview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

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
