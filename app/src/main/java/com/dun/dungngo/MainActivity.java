package com.dun.dungngo;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {



    WebView superWebView;
    SwipeRefreshLayout websitePullToRefresh;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,scanner.class);
                startActivity(i);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){

            NotificationChannel channel= new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        FirebaseMessaging.getInstance().subscribeToTopic("ge")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfully";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }

                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });





        websitePullToRefresh = (SwipeRefreshLayout) findViewById(R.id.websitePullToRefresh);





        superWebView = findViewById(R.id.myWebView);





        superWebView.loadUrl("https://getcare.vn/en/");
        superWebView.getSettings().setJavaScriptEnabled(true);
        superWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    superWebView.stopLoading();
                } catch (Exception e) {
                }

                if (superWebView.canGoBack()) {
                    webView.goBack();
                }

                superWebView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });

        websitePullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                superWebView.loadUrl("https://getcare.vn/en/");
                websitePullToRefresh.setRefreshing(false);
            }
        });










        superWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri myUri = Uri.parse(url);
                Intent superIntent = new Intent(Intent.ACTION_VIEW);
                superIntent.setData(myUri);
                startActivity(superIntent);
            }
        });

    }






    private void GoForward() {
        if (superWebView.canGoForward()) {
            superWebView.goForward();
        } else {
            Toast.makeText(this, "Can't go further!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (superWebView.canGoBack()) {
            superWebView.goBack();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Exit App");
            dialog.setMessage("Browser has nothing to go back, so what next?");
            dialog.setPositiveButton("EXIT ME", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.setCancelable(false);
            dialog.setNegativeButton("STAY HERE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        }
    }
}