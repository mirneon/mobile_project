package com.example.janus.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DayItem extends AppCompatActivity{
    DayDBHelper dayDBHelper;
    int Id;
    String today;
    EditText editDate, editTitle, editTime, editMemo;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayitem);

        editDate = (EditText) findViewById(R.id.editdate);
        editTitle = (EditText) findViewById(R.id.edittitle);
        editTime = (EditText) findViewById(R.id.edittime);
        editMemo = (EditText) findViewById(R.id.editmemo);

        Intent intent = getIntent();
        Id = intent.getIntExtra("ParamID", -1);
        Log.d("ParamID", "ParamID" + Id);
        today = intent.getStringExtra("ParamDate");

        dayDBHelper = new DayDBHelper(this, "Today.db", null, 1);

        if (Id == -1) {
            editDate.setText(today);
        } else {
            SQLiteDatabase db = dayDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM today WHERE _id='" + Id
                    + "'", null);

            if (cursor.moveToNext()) {
                editTitle.setText(cursor.getString(1));
                editDate.setText(cursor.getString(2));
                editTime.setText(cursor.getString(3));
                editMemo.setText(cursor.getString(4));
            }
            dayDBHelper.close();
        }
        Button btnsave = (Button) findViewById(R.id.btnsave);
        Button btndel = (Button) findViewById(R.id.btndel);
        Button btncancel = (Button) findViewById(R.id.btncancel);
        if (Id == -1) {
            btndel.setVisibility(View.INVISIBLE);

        }

        db = dayDBHelper.getWritableDatabase();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id != -1) {
                    Log.d("ParamID","ParamID"+Id);
                    db.execSQL("UPDATE today SET title='"
                            + editTitle.getText().toString() + "',date='"
                            + editDate.getText().toString() + "', time='"
                            + editTime.getText().toString() + "', memo='"
                            + editMemo.getText().toString() + "' WHERE _id='" + Id
                            + "';");
                } else {
                    db.execSQL("INSERT INTO today VALUES(null, '"
                            + editTitle.getText().toString() + "', '"
                            + editDate.getText().toString() + "', '"
                            + editTime.getText().toString() + "', '"
                            + editMemo.getText().toString() + "');");
                    Log.d("execSQL","db" + db);
                }
                dayDBHelper.close();
                setResult(RESULT_OK);
                finish();
            }
        });

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id != -1) {
                    db.execSQL("DELETE FROM today WHERE _id='" + Id + "';");
                    dayDBHelper.close();
                }
                setResult(RESULT_OK);
                finish();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });


    }

}
