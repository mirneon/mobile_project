package com.example.janus.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CardList extends AppCompatActivity {
    String today;
    CardDBHelper cardDBHelper;
    SimpleCursorAdapter adapter;
    SQLiteDatabase db;
    Cursor cursor;
    String sender;
    String contents;
    String received;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SMS 수신 권한 있음.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "SMS 수신 권한 없음.", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this, "SMS 권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, 1);
            }
        }

        Intent intent = getIntent();
        today = intent.getStringExtra("param1");


        Log.d("receivedDate","receivedDate" + received);


        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(today);

        cardDBHelper = new CardDBHelper(this,"Today2.db",null,1);
        db = cardDBHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM today2 WHERE received = '"+ today + "'", null);


        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, cursor, new String[] {
                "contents" }, new int[] { android.R.id.text1});
        adapter.changeCursor(cursor);

        ListView list = (ListView) findViewById(R.id.listView);

        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cardDBHelper.close();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "SMS 권한을 사용자가 승인함.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "SMS 권한 거부됨.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


}