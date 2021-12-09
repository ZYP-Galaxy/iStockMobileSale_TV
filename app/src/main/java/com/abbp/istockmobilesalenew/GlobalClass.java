package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class GlobalClass {

    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static void showAlertDialog(Context ctx, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx, R.style.AlertDialogTheme);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", (dialog1, which) -> {
        });
        dialog.create().show();
    }


}


