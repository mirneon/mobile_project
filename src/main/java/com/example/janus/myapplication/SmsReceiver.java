package com.example.janus.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    public static final String TAG = "SmsReceiver";
    public SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    CardDBHelper cardDBHelper;
    SQLiteDatabase db;


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive() 메소드 호출됨.");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if (messages != null && messages.length > 0) {

            String sender = messages[0].getOriginatingAddress();
            Log.i(TAG, "SMS sender : " + sender);


            String contents = messages[0].getMessageBody().toString();
            Log.i(TAG, "SMS contents : " + contents);


            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.i(TAG, "SMS received date : " + receivedDate.toString());

            savetodb(context, sender, contents, receivedDate);


        }

    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        int smsCount = objs.length;
        for (int i = 0; i < smsCount; i++) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }

        return messages;
    }

    private void savetodb(Context context, String sender, String contents, Date receivedDate) {
        String receivedDate2 = format.format(receivedDate);
        String sinhan = "15447200";
        Log.d("sender", "sender" + sender);
        if(sender.equals(sinhan)) {
            cardDBHelper = new CardDBHelper(context, "Today2.db", null, 1);
            db = cardDBHelper.getWritableDatabase();
            db.execSQL("INSERT INTO today2" + "(received,contents) VALUES(" + "'" + receivedDate2 + "'," + "'" + contents + "'" + ");");
            Log.d("execSQL", "db" + db);
            cardDBHelper.close();
        }
    }


}
