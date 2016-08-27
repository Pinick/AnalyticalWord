package com.analyticalword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 支持doc,docx,XLSX,xls
 * */
public class MainActivity extends AppCompatActivity {
    //请在内存下放个文件
    public String nameStr = "/sdcard/demo.doc";
    public WebView wv_view;
    WordReader fr=null;
    public File myFile;
    // private CustomDialog dialog;
    private WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv_view = (WebView)findViewById(R.id.wv_view);
        webSettings = wv_view.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        wv_view.setHapticFeedbackEnabled(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setDisplayZoomControls(false);
        myFile = new File(nameStr);
        if (myFile.exists()){
            rx.Observable.just(nameStr).map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    fr=new WordReader(s);
                    return fr.returnPath;
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            //拿到call方法对"test"的数据进行处理的结果
                            if (wv_view!=null){
                                wv_view.loadUrl(s);
                                webSettings.setLoadWithOverviewMode(true);
                               // parseFinishListenner.onParseFinshed();
                                // dialog.dismiss();
                            }
                        }
                    });
    }
}
}
