package com.qdqy.admin.sinaapp.comment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.qdqy.admin.sinaapp.R;
import com.qdqy.admin.sinaapp.newsdata.AsyncGetData;

import java.util.ArrayList;
import java.util.List;


public class LoginEditText extends Activity {

    private EditText edtAccont,edtPassword;
    private ListPopupWindow lpw;
    private List<String> list = null;
    private static final String FILENAME = "Account_info";
    private Button btn = null;
    String uname = null,upwd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        OnTouchEvent onTouchEvent = new OnTouchEvent();
        edtAccont.setOnTouchListener(onTouchEvent);

        btn = (Button)findViewById(R.id.login_con);
        OnClickEvent onClickEvent = new OnClickEvent();
        btn.setOnClickListener(onClickEvent);

        lpw = new ListPopupWindow(LoginEditText.this);
        lpw.setAdapter(new ArrayAdapter<String>(LoginEditText.this,
                android.R.layout.simple_list_item_1, list));
        lpw.setAnchorView(edtAccont);
        lpw.setModal(true);

        OnClickItemEvent onClickItemEvent = new OnClickItemEvent();
        lpw.setOnItemClickListener(onClickItemEvent);
    }

    public void init(){

        edtAccont = (EditText) findViewById(R.id.editaccount);
        edtPassword = (EditText) findViewById(R.id.editpwd);
        list = new ArrayList<String>();

        SharedPreferences share = this.getSharedPreferences(FILENAME,0);
        String username = share.getString("username", "");
        String userpwd = share.getString("userpwd","");
        edtAccont.setText(username);
        edtPassword.setText(userpwd);
        Log.i("list", String.valueOf(list.size()));
        list.add(username);
//        if(list!=null){
//            Iterator iterator = list.iterator();
//            while(iterator.hasNext()){
//                list.add(username);
//            }
//        }
    }


    private class OnClickEvent implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            uname = edtAccont.getText().toString();
            upwd = edtPassword.getText().toString();
            SharedPreferences shared = LoginEditText.this.getSharedPreferences(FILENAME,0);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("username", uname);
            editor.putString("userpwd", upwd);
            editor.apply();
            list.add(uname);
            Log.i("list", String.valueOf(list.size()));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(AsyncGetData.sinaData.login(uname,upwd)){
                        Intent intent = getIntent();
                        String com = intent.getStringExtra("comment");
                        String newsId = intent.getStringExtra("newsId");
                        if(AsyncGetData.sinaData.sendComment(newsId,com)){
                            Log.d("test","发送成功");
                            LoginEditText.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginEditText.this, "发送成功", Toast.LENGTH_LONG).show();

                                }
                            });
                            finish();
                        }else{
                            Log.d("test","发送失败");
                        }
                    } else{
                        LoginEditText.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginEditText.this, "登录失败，请重新登录", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
           });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class OnClickItemEvent implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            String item = list.get(position);
            edtAccont.setText(item);
            edtPassword.setText("");
            lpw.dismiss();
        }
    }

    private class OnTouchEvent implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getX() >= (v.getWidth() - ((EditText) v)
                        .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    lpw.show();
                    return true;
                }
            }
            return false;
        }
    }

}