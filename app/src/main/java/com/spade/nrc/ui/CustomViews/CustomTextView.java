package com.spade.nrc.ui.CustomViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.spade.nrc.R;


/**
 * Created by Ayman Abouzeid on 11/5/17.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        styleText(context, attrs);
    }

    private void styleText(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextView);
//        int fontStyle = typedArray.getInteger(R.styleable.CustomTextView_fontTypeFaceStyle, 0);
        ColorStateList textColor = typedArray.getColorStateList(R.styleable.CustomTextView_color);
//        int fontNameStringID = 0;
//        switch (fontStyle) {
//            case 0:
//                fontNameStringID = R.string.gotham_regular;
//                break;
//            case 1:
//                fontNameStringID = R.string.gotham_semi_bold;
//                break;
//            case 2:
//                fontNameStringID = R.string.gotham_bold;
//                break;
//        }

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Bold.ttf");
        setTypeface(typeface);
        if (textColor != null) {
            setTextColor(textColor);
        } else {
            setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        typedArray.recycle();
    }
}

