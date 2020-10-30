package com.example.janus.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MonthItemView extends RelativeLayout {
    TextView textView;
    private MonthItem item;

    public MonthItemView(Context context) {
        super(context);

        init(context);
    }

    public MonthItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.month_item, this, true);
        setBackgroundColor(Color.WHITE);

        textView = (TextView) findViewById(R.id.textView);
    }


    public MonthItem getItem() {
        return item;
    }

    public void setItem(MonthItem item) {
        this.item = item;

        int day = item.getDay();
        if (day != 0) {
            textView.setText(String.valueOf(day));
        } else {
            textView.setText("");
        }

    }


}

