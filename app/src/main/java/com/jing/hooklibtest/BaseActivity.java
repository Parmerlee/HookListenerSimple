package com.jing.hooklibtest;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jing.hooklistener.HookCore;
import com.jing.hooklistener.HookListenerContract;
import com.jing.hooklistener.ListenerManager;

/**
 * Created by wulei
 * Data: 2016/10/18.
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean hasHooked = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus) {
            return;
        }
        if (hasHooked) {
            return;
        }
        ListenerManager.Builer builer = new ListenerManager.Builer();

        builer.buildOnClickListener(new HookListenerContract.OnClickListener() {
            @Override
            public void doInListener(View v) {
                Toast.makeText(BaseActivity.this, "单击时我执行", Toast.LENGTH_SHORT).show();
            }
        }).buildOnLongClickListener(new HookListenerContract.OnLongClickListener() {
            @Override
            public void doInListener(View v) {
                Toast.makeText(BaseActivity.this, "长按时我执行", Toast.LENGTH_SHORT).show();
            }
        }).buildOnFocusChangeListener(new HookListenerContract.OnFocusChangeListener() {
            @Override
            public void doInListener(View v, boolean hasFocus) {
                Toast.makeText(BaseActivity.this, "焦点变化时我执行", Toast.LENGTH_SHORT).show();
            }
        }).buildOnItemClickProxyListener(new HookListenerContract.OnItemClickListenerProxy() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BaseActivity.this, "CCCCCCC", Toast.LENGTH_SHORT).show();
            }
        }).buildOnItemLongClickProxyListener(new HookListenerContract.OnItemLongClickListenerProxy() {
            @Override
            public void onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BaseActivity.this, "DDDDDDD", Toast.LENGTH_SHORT).show();
            }
        }).buildOnItemSelectedProxyListener(new HookListenerContract.OnItemSelectedClickListenerProxy() {
            @Override
            public void onItemSelectedClick(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        HookCore.getInstance().startHook(this, ListenerManager.create(builer));
        hasHooked = true;
    }
}
