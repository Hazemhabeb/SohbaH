package com.sohba_travel.sohba.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by M on 11/21/2016.
 */

public class SohbaEditText extends EditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public SohbaEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SohbaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public SohbaEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Light.otf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Light.otf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Light.otf");
        super.setTypeface(tf);
    }
}
