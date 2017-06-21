package com.jing.hooklibtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.edt)
    EditText edt;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    ListView mListView;
    List<String> mData = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        mListView = (ListView) this.findViewById(R.id.listView);
        initData();
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("AAA", "CCCCCC");
            }
        });
    }

    private void initData() {
        for (int i = 'A'; i < 'Z'; i++) {

            mData.add("i:" + i);
        }
    }

    @OnClick({R.id.button, R.id.button1})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button:
                break;
            case R.id.button1:
                context.startActivity(new Intent(this, Main2Activity.class));
                finish();
                break;
        }
    }

//    @OnLongClick({R.id.edt, R.id.button, R.id.button1})
//    public boolean onLongClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv:
//                break;
//            case R.id.button:
//                break;
//            case R.id.button1:
//                break;
//        }
//        return false;
//    }

}
