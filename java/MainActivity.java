package com.example.janus.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.janus.myapplication.CardList;
import com.example.janus.myapplication.DayList;
import com.example.janus.myapplication.MonthAdapter;
import com.example.janus.myapplication.MonthItem;
import com.example.janus.myapplication.R;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView monthView;
    MonthAdapter monthViewAdapter;
    TextView monthText;
    int curYear;
    int curMonth;
    int day;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthView = (GridView) findViewById(R.id.monthView);
        monthViewAdapter = new MonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);
        textView = (TextView) findViewById(R.id.textView);

        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                day = curItem.getDay();

                int year = monthViewAdapter.getCurYear();
                int month = monthViewAdapter.getCurMonth()+1;

                textView.setText(curYear+"/"+(curMonth+1)+"/"+day);

                Log.d("MainActivity", "Selected : " + year + "/" + month + "/" + day );

            }
        });

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DayList.class);
                intent.putExtra("param1", curYear+"/"+(curMonth+1)+"/"+day);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardList.class);
                intent.putExtra("param1", curYear+"/"+(curMonth+1)+"/"+day);
                startActivity(intent);
            }
        });

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();


        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });


        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

    }

    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}
