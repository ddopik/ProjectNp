package com.spade.nrc.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;
import android.widget.Toast;

import java.util.Locale;

public class Utils {

    public static boolean isMobile(String s) {
        return Patterns.PHONE.matcher(s).matches();

    }

    public static void dial(String mobileNumber, Context mContext) {
        Uri number = Uri.parse("tel:" + mobileNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        mContext.startActivity(callIntent);
    }

    public static void sendSms(String mobileNumber, Context mContext) {
        Uri number = Uri.parse("smsto:" + mobileNumber);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, number);
        smsIntent.putExtra("sms_body", "");
        mContext.startActivity(smsIntent);
    }

    public static void openURL(String link, Context mContext) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            mContext.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "Not valid Url", Toast.LENGTH_LONG).show();
        }
    }

    public static void openMap(double latitude, double longitude, Context mContext) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mContext.startActivity(intent);
    }

    public static void openMail(String mail, Context mContext) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", mail, null));
        mContext.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    public static void share(String text, Context mContext) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);

        mContext.startActivity(intent);

    }

}
