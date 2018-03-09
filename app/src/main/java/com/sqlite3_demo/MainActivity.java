package com.sqlite3_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sqlite3_demo.db.BlackDao;
import com.sqlite3_demo.entity.BlackInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private BlackDao blackDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button viewById = (Button) findViewById(R.id.btn_test);
        viewById.setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.btn_query_some).setOnClickListener(this);

        blackDao = new BlackDao(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                    testAddSome();
                break;
            case R.id.btn_add:
                    testAdd();
                break;
            case R.id.btn_delete:
                    testDelete();
                break;
            case R.id.btn_query:
                    testQuery();
                break;
            case R.id.btn_update:
                    testUpdate();
                break;
            case R.id.btn_query_some:
                    testQuerySome();
                break;
            default:
                break;
        }
    }

    private void testQuerySome() {
        List<BlackInfo> blackList = blackDao.getBlackList();
        Log.i(TAG, "testQuerySome: -----------------------------------00");
        for (int i = 0; i < blackList.size(); i++) {
            int key = i+1;
            Log.i(TAG, "testQuerySome: ---00 "+key+" "+blackList.get(i).toString());
        }
    }

    private void testUpdate() {
        if (blackDao.update("11111111111", 2)) {
            showToast("成功！");
        } else {
            showToast("失败！");
        }
    }

    private void testQuery() {
        int type = blackDao.getType("11111111111");
        showToast(type+"");
    }

    private void testDelete() {
        if (blackDao.delete("11111111111")) {
            showToast("成功！");
        } else {
            showToast("失败！");
        }
    }

    private void testAdd() {
        if (blackDao.add("11111111111", 3)) {
            showToast("成功！");
        } else {
            showToast("失败！");
        }
    }

    public void testAddSome() {
        for (int i = 0; i < 30; i++) {
            String number = "1888888800" + i;
            int type = i % 3 + 1;
            boolean actual = blackDao.add(number, type);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
