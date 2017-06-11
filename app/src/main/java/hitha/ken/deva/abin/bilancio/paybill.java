package hitha.ken.deva.abin.bilancio;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class paybill extends AppCompatActivity {
    private static WebView w;
    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill);
        p=(ProgressBar)findViewById(R.id.pb);
        p.setVisibility(View.GONE);
        String s=getIntent().getStringExtra("url");
        loadsite(s);
    }
    public void loadsite(String url)
    {

        w = (WebView)findViewById(R.id.web);
        w.setVisibility(1);
        p.setVisibility(View.VISIBLE);
        w.getSettings().getLoadsImagesAutomatically();
        w.getSettings().setJavaScriptEnabled(true);
        w.setWebViewClient(new myweb());
        w.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        w.loadUrl(url);
    }

    public class myweb extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //p.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            p.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if(w.canGoBack())
            w.goBack();
        else
            super.onBackPressed();
    }

}
