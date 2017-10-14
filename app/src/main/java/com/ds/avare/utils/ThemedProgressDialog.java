package com.ds.avare.utils;

import android.app.ProgressDialog;
import android.content.Context;

import me.stratux.stratuvare.R;

public class ThemedProgressDialog extends ProgressDialog {

    public ThemedProgressDialog(Context context) {
        super(context, R.style.Theme_Dialog);
    }

}
