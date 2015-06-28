package com.mygdx.game.android;

import com.mygdx.IActionResolver.IActionResolver;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by bahadirali on 20.06.2015.
 */
public class ActionResolverAndroid implements IActionResolver{

    public boolean flag = false;
    public Toast shortToast;
    Handler uiThread;
    Context appContext;


    public ActionResolverAndroid(Context appContext) {
        uiThread = new Handler();
        this.appContext = appContext;
        shortToast = Toast.makeText(appContext, "", Toast.LENGTH_SHORT);
    }

    @Override
    public void showShortToast(final CharSequence toastMessage) {

        uiThread.post(new Runnable() {

            @Override
            public void run() {
                //instead of toast.cancel I used toast.setText
                //since i couldn't handle it with .cancel
                shortToast.setText(toastMessage);
                shortToast.show();
                uiThread.removeCallbacks(this);
            }
        });
    }


    @Override
    public void showLongToast(final CharSequence toastMessage) {
        uiThread.post(new Runnable() {
            public void run() {
                Toast.makeText(appContext, toastMessage, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


    @Override
    public void showAlertBox(final String alertBoxTitle,
                             final String alertBoxMessage, final String alertBoxButtonText) {
        uiThread.post(new Runnable() {
            public void run() {
                new AlertDialog.Builder(appContext)
                        .setTitle(alertBoxTitle)
                        .setMessage(alertBoxMessage)
                        .setNeutralButton(alertBoxButtonText,
                                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                    int whichButton) {
                    }
                }).create().show();
            }
        });
    }


    @Override
    public void openUri(String uri) {
        Uri myUri = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
        appContext.startActivity(intent);
    }


    @Override
    public void showMyList() {
        //appContext.startActivity(new Intent(this.appContext, MyListActivity.class));
    }
}
