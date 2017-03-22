package com.yackfolkfestival.android.yffandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by chris on 23/2/17.
 */

public class SocialButton extends ImageButton {
    String url;

    public SocialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setUrl(String urlString) {
        this.url = urlString;
    }

    public String getUrl() {
        return this.url;
    }
}
