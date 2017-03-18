package com.example.android.yffandroid;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chris on 19/3/17.
 */

public class Repo {

    public static String loadJSONFromAsset(Context ctxt, String filename) {
        String json;
        try {
            InputStream is = ctxt.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
