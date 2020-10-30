package com.example.janus.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



public class DayList extends AppCompatActivity{
    DayDBHelper dayDBHelper;
    String today;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    ListView list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daylist);

        Intent intent = getIntent();
        today = intent.getStringExtra("param1");


        TextView text = (TextView) findViewById(R.id.texttoday);
        text.setText(today);
        ListView list = (ListView) findViewById(R.id.list1);

        dayDBHelper = new DayDBHelper(this, "Today.db", null, 1);
        SQLiteDatabase db = dayDBHelper.getWritableDatabase();

        cursor = db.rawQuery(
                "SELECT * FROM today WHERE date = '" + today + "'", null);
        Log.d("cursor","cursor" + cursor);

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, new String[] {
                "title", "time" }, new int[] { android.R.id.text1,
                android.R.id.text2 });

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DayItem.class);
                cursor.moveToPosition(position);
                intent.putExtra("ParamID", cursor.getInt(0));
                startActivityForResult(intent, 0);
            }
        });

        dayDBHelper.close();

        Button btn = (Button) findViewById(R.id.btnadd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DayItem.class);
                intent.putExtra("ParamDate", today);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
            case 1:
                if (resultCode == RESULT_OK) {
                    adapter.notifyDataSetChanged();
                    SQLiteDatabase db = dayDBHelper.getWritableDatabase();
                    cursor = db.rawQuery("SELECT * FROM today WHERE date = '"
                            + today + "'", null);
                    adapter.changeCursor(cursor);
                    dayDBHelper.close();
                }
                break;
        }
    }
}
