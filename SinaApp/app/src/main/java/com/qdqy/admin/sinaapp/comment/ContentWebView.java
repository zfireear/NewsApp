package com.qdqy.admin.sinaapp.comment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.qdqy.admin.sinaapp.R;

public class ContentWebView extends Activity {

    private WebView mWebView;
    private String weburl;
    private EditText edt;
    private Button btn;
    private static String newsId;

    public static void actionStart(Context context,String url,String Id){
        newsId = Id;
        Intent intent = new Intent(context,ContentWebView.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        weburl = getIntent().getStringExtra("url");

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl(weburl);

        edt = (EditText)findViewById(R.id.edt_comment);

        btn = (Button)findViewById(R.id.conment_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContentWebView.this,LoginEditText.class);
                intent.putExtra("comment",edt.getText().toString());
                intent.putExtra("newsId",newsId);
                ContentWebView.this.startActivity(intent);
            }
        });
    }
}
