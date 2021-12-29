package com.abbp.istockmobilesalenew;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class frmscancode extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmscancode);
        scannerView = findViewById(R.id.scanner);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getBaseContext(), "Permission is Granted", Toast.LENGTH_SHORT).show();

            } else {
                requestPermission();
            }


        }

    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(frmscancode.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    public void onRequestPermissionResult(int requestCode, String permission[], int grantResults[]) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getBaseContext(), "Permission is Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Permission is Denied", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permission", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA_PERMISSION);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }

        }
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(frmscancode.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create();

    }


    @Override
    public void handleResult(Result rawResult) {

        String scanResult = rawResult.getText();
        /*Intent i =new Intent(frmscancode.this,sale_entry.class);
        startActivity(i);
        */
        String scannercode = "NULL";

        Cursor cursor = DatabaseHelper.rawQuery("select usr_code from Alias_Code where al_code='" + scanResult + "'");

        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    scannercode = cursor.getString(cursor.getColumnIndex("usr_code"));
                } while (cursor.moveToNext());

            }
        }

        finish();
        if (!scannercode.equals("NULL")) {
            UsrcodeAdapter.scanner(scannercode);
        } else {
            Toast.makeText(getBaseContext(), "Alias Code isn't in the Table!", Toast.LENGTH_SHORT).show();
        }

        /*AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(frmscancode.this);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(),scanResult,Toast.LENGTH_LONG).show();
                Intent i =new Intent(frmscancode.this,sale_entry.class);
                startActivity(i);

            }
        });

        builder.setMessage(scanResult);
        AlertDialog alert=builder.create();
        alert.show();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    setContentView(R.layout.frmscancode);
                    scannerView = findViewById(R.id.scanner);

                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

