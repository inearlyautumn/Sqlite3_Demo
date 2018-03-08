package com.sqlite3_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sqlite3_demo.db.BlackDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testAdd() {
        BlackDao blackDao = new BlackDao(this);
        for (int i = 0; i < 30; i++) {
            String number = "1888888800" + i;
            int type = i % 3 + 1;
            boolean actual = blackDao.add(number, type);
        }

    }
}
